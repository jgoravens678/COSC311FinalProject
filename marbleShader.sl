#define PALE_BLUE        color (0.25, 0.25, 0.35)
#define MEDIUM_BLUE      color (0.20, 0.20, 0.30)
#define DARK_BLUE        color (0.15, 0.15, 0.26)
#define DARKER_BLUE      color (0.1, 0.1, 0.20)
#define NNOISE           8
color
marble_color(float m, float i)
{
    float choice;
    if (i == 0)
        choice = m;
    else
      choice = clamp(2*m + 0.75,0,1);

    color controls[13] = 	{PALE_BLUE, PALE_BLUE,
			MEDIUM_BLUE, MEDIUM_BLUE, MEDIUM_BLUE,
			PALE_BLUE, PALE_BLUE,
			DARK_BLUE, DARK_BLUE,
			DARKER_BLUE, DARKER_BLUE,
			PALE_BLUE, DARKER_BLUE};

    color toRet = spline("catmull-rom",
			choice, controls
		);
     return toRet;
}


surface
marbleShader( output color Cout = color (0,0,1),
	    float roughness = 0.0,
	    float scale = 3)
{
  color Ct;
  normal NN;
  point PP;
  float f;
  float value;
  float lofreq = 1;
  float hifreq = 2048;
  float firstMult = 2.17;
  float secondMult = 4.0;
  float choice = 0;

  PP = transform("shader", P)*scale;

  value = 0;

  for (f = lofreq; f < hifreq; f = f * firstMult) {
    value += (noise(PP * f))* 1.0/f;
  }

  if (choice == 0)
    value = clamp(value-0.5,0,1);
  else
    value = clamp((secondMult*value)-(secondMult-1),0,1);

    Cout = marble_color(value,choice);


}