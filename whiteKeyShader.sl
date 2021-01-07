#define PALE_BLUE        color (0.25, 0.25, 0.35)
#define MEDIUM_BLUE      color (0.20, 0.20, 0.30)
#define DARK_BLUE        color (0.15, 0.15, 0.26)
#define DARKER_BLUE      color (0.1, 0.1, 0.20)

#define WHITE            color (1.0, 1.0, 1.0)
#define VERY_PALE_YELLOW color (1.0, 1.0, 0.89)
#define PALE_YELLOW      color (1.0, 1.0, 0.78)
#define PALE_GOLD        color (1.0, 0.906, 0.55)
#define GOLD             color (0.94, 0.82, 0.37)
#define LIGHT_GRAY       color (0.87, 0.87, 0.87)
#define GRAY             color (0.3, 0.3, 0.3)
#define NNOISE           8
color
marble_color(float m, float i)
{
    color toRet;
    float choice;
    if (i == 0)
        choice = m;
    else
      choice = clamp(2*m + 0.75,0,1);

    color controls[17] = {WHITE, WHITE, PALE_YELLOW,
    WHITE, PALE_YELLOW, PALE_YELLOW, WHITE, WHITE, PALE_GOLD, PALE_GOLD
    , GOLD, WHITE, PALE_YELLOW, PALE_YELLOW, GOLD, GOLD, PALE_GOLD
    };

    color controlsEdge[17] = {WHITE, WHITE, GRAY,
    GRAY, LIGHT_GRAY, LIGHT_GRAY, WHITE, WHITE, GRAY, LIGHT_GRAY
    , LIGHT_GRAY, WHITE, GRAY, LIGHT_GRAY, LIGHT_GRAY, WHITE, WHITE
    };

    if (v < 0.1 || v >= 0.9){
        toRet = spline("catmull-rom", choice, controls);
    }

    else{
    toRet = spline("catmull-rom",
			choice, controls
		);
	}

    return toRet;
}


surface
whiteKeyShader( output color Cout = color (0,0,1),
	    float roughness = 2.1,
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