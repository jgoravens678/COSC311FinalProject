#define LIGHT_GRAY       color(0.55, 0.55, 0.55)
#define GRAY             color(0.65, 0.65, 0.65)
#define DARK_GRAY        color(0.35, 0.35, 0.35)
#define DARKEST_GRAY     color(0.15, 0.15, 0.15)
#define BLUE_GRAY        color(0.65, 0.65, 0.85)
#define DARK_BLUE_GRAY   color(0.3, 0.3, 0.55)
#define LIGHT_GREEN      color(0.46, 1.0, 0.13)
#define GREEN            color(0.37, 0.625, 0.13)
#define DARK_GREEN       color(0.234, 0.391, 0.059)
#define GOLD             color (0.94, 0.82, 0.37)
#define DARK_GOLD        color (0.906, 0.652, 0.016)
#define GOLD_BROWN       color (0.68, 0.39, 0.0)
#define RED_BROWN        color (0.70, 0.31, 0.0)
#define BROWN            color (0.476, 0.195, 0.0)
#define MAHOGANY         color (0.753, 0.251, 0.0)
#define DARK_BROWN       color(0.3125, 0.148, 0.0)

color makeStoneColor(float splineNo){
    splineNo = clamp(splineNo, 0, 1);

    /*
    color controls[17] = {LIGHT_GRAY, LIGHT_GRAY,
    GRAY, GRAY, GRAY,
    DARK_GRAY, DARK_GRAY, DARK_GRAY,
    GOLD, GOLD,
    GRAY, GRAY, GRAY,
    DARK_GOLD, DARK_GOLD,
    LIGHT_GRAY, LIGHT_GRAY};

    color controls[30] = {
        BROWN, RED_BROWN, BROWN,
        DARK_BROWN, MAHOGANY, DARK_BROWN,
        RED_BROWN, BROWN, MAHOGANY,
        BROWN, DARK_BROWN, RED_BROWN,
        RED_BROWN, MAHOGANY, BROWN,
        BROWN, RED_BROWN, BROWN,
        DARK_BROWN, MAHOGANY, DARK_BROWN,
        RED_BROWN, BROWN, MAHOGANY,
        BROWN, DARK_BROWN, RED_BROWN,
        RED_BROWN, MAHOGANY, BROWN
    };
    */

    color controls[30] = {
        LIGHT_GRAY, GRAY, LIGHT_GRAY,
        DARK_GRAY, DARKEST_GRAY, DARK_GRAY,
        GRAY, LIGHT_GRAY, DARKEST_GRAY,
        LIGHT_GRAY, DARK_GRAY, LIGHT_GRAY,
        GRAY, DARKEST_GRAY, LIGHT_GRAY,
        LIGHT_GRAY, GRAY, LIGHT_GRAY,
        DARK_GRAY, DARKEST_GRAY, DARK_GRAY,
        GRAY, LIGHT_GRAY, DARKEST_GRAY,
        LIGHT_GRAY, DARK_GRAY, LIGHT_GRAY,
        GRAY, DARKEST_GRAY, LIGHT_GRAY
    };

    color toRet = spline("catmull_rom", splineNo, controls);

    return toRet;

}

color makeMossColor(float splineNo){
    splineNo = clamp(splineNo, 0,1);

    color controls[12] = {LIGHT_GREEN, LIGHT_GREEN,
    GREEN, GREEN, GREEN,
    DARK_GREEN, DARK_GREEN, DARK_GREEN,
    LIGHT_GREEN, LIGHT_GREEN,
    DARK_GREEN, DARK_GREEN};

    color toRet = spline("bezier", splineNo, controls);

    return toRet;
}

surface cobbleShader( float numStones = 20,
float stoneProportion = 0.75,
float displaceAmount = 0.3,
output color Cout = color (1.0, 1.0, 1.0),
output vector changeV = vector (0.0, 0.0, 0.0),
output normal NN = N,
output float displacement = 0.0,
output float roughness = 1.0,
output float fuzz = 0.0,
){
    float stoneLongUnit = 1.0 / numStones;
    float stoneStripWidth = (1.0 / numStones) / 2.0;
    float stoneLength = (1.0 / numStones) * stoneProportion;
    float stoneWidth = stoneLength / 2.0;

    /*
    float numIters = 10;
        float noiseVal = 0;

        point PP;
        PP = transform("shader", P);

        float powerScale;
        for(int i = 0; i <= numIters; i++){
            powerScale = pow(2, numIters);
            noiseVal += noise(PP * powerScale) / powerScale;
        }

        noiseVal = clamp(0, 1, noiseVal);
    */

    //normal NN;
      point PP;
      float f;
      float value;
      float lofreq = 1;
      float hifreq = 1024;
      float firstMult = 2.17;
      float secondMult = 4.0;
      float choice = 0;
      float scale = 4;
      normal N1 = normal(0,0,0);

      PP = transform("shader", P)*scale;

      value = 0;

      for (f = lofreq; f < hifreq; f = f * firstMult) {
        value += (noise(PP * f))* 1.0/f;
        N1 += noise(PP * f);
      }

      if (choice == 0)
        value = clamp(value-0.5,0,1);
      else
        value = clamp((secondMult*value)-(secondMult-1),0,1);

    float vOnEvenStrip = mod( (v - mod(v, stoneStripWidth) )/ 2.0, stoneStripWidth);
    //float displaceAmount = 2.0;
    int isBrick = 0;
    if(mod(v, stoneStripWidth) <= stoneProportion * stoneStripWidth){
    if(abs(vOnEvenStrip) < 0.05 * stoneStripWidth){
        if(mod(u, stoneLongUnit) <= stoneProportion * stoneLongUnit){
            Cout = makeStoneColor(value);
            changeV = displaceAmount * (1 + (noise(N1) / 30.0)) * normalize(N);
            displacement = displaceAmount;
            //NN = normalize(N + normalize(N1));
            isBrick = 1;
        }
    }
    else{
        if(mod(u, stoneLongUnit) <= ((1 - stoneProportion) * 1.5) * stoneLongUnit || mod(u, stoneLongUnit) >= ((1 - stoneProportion) * 2.5) * stoneLongUnit ){
                Cout = makeStoneColor(value);
                changeV = displaceAmount * (1 +( noise(N1) / 30.0)) * normalize(N);
                displacement = displaceAmount;
                //NN = normalize(N + normalize(N1));
                isBrick = 1;
            }
    }
    }
    if(isBrick == 0){
        Cout = makeMossColor(value);
        fuzz = 0.7;
        roughness = 0.2;
        changeV = (1.5* displaceAmount) * value * normalize(N);
    }
}