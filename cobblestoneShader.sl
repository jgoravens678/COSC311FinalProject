#define LIGHT_GRAY       color(0.9, 0.9, 0.9)
#define GRAY             color(0.65, 0.65, 0.65)
#define DARK_GRAY        color(0.35, 0.35, 0.35)
#define BLUE_GRAY        color(0.65, 0.65, 0.85)
#define DARK_BLUE_GRAY   color(0.3, 0.3, 0.55)
#define LIGHT_GREEN      color(0.46, 1.0, 0.13)
#define GREEN            color(0.37, 0.625, 0.13)
#define DARK_GREEN       color(0.234, 0.391, 0.059)

float distFromCenter(float u1, float v1){
    return sqrt(pow(u1 - u, 2) + pow(v1 - v, 2));

}

vector directionFromCenter(float u1, float v1){
    vector uDir = (u - u1) * dPdu;
    vector vDir = (v - v1) * dPdv;
    vector toRet = uDir + vDir;
    return toRet;
}

int isInCircle(float u1, float v1, float radius){
    if ( distFromCenter(u1,v1) <= radius){
        return 1;
    }
    return 0;
}

color makeStoneColor(float splineNo){
    splineNo = clamp(splineNo, 0, 1);

    color controls[20] = {LIGHT_GRAY, LIGHT_GRAY,
    GRAY, GRAY, GRAY,
    BLUE_GRAY, BLUE_GRAY,
    DARK_GRAY, DARK_GRAY, DARK_GRAY,
    DARK_BLUE_GRAY, DARK_BLUE_GRAY, DARK_BLUE_GRAY,
    BLUE_GRAY, BLUE_GRAY,
    GRAY, GRAY, GRAY,
    LIGHT_GRAY, LIGHT_GRAY};

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

surface cobblestoneShader(float numStones = 60,
output color Cout = 0.0,
output vector changeV = (0.0,0.0,0.0),
output normal Nout = N
){
    point PP;
    color stone;
    color moss;
    float radius = pow(numStones, -1) / 2.0;
    float angle = (float) M_PI / 3.0;
    float uBandwidth = 2 * radius;
    float vBandwidth = 2 * radius * sin(angle);

    float uMod = mod(u, uBandwidth);
    float vMod = mod(v, vBandwidth);

    //Determine first whether we're in an even- or odd-indexed v-band.
    int vBandEven;
    if(mod( (v - vMod) / 2.0, vBandwidth ) == 0.0){
        vBandEven = 1;
    }
    else{
        vBandEven = 0;
    }

    //Accordingly, set the circle center candidates within which (u,v) might be contained.
    float centers[6];
    float uBase = u - uMod;
    float vBase = v - vMod;
    for(int i = 0; i < 3; i++){
        centers[2*i] = uBase + (uBandwidth / 2.0) * (i);
    }
    if(vBandEven == 1){
        centers[1] = vBase + vBandwidth;
        centers[3] = vBase;
        centers[5] = vBase + vBandwidth;
    }
    else{
        centers[1] = vBase;
        centers[3] = vBase + vBandwidth;
        centers[5] = vBase;
    }

    int inCircle = 0;
    float uCenter = -1.0;
    float vCenter = -1.0;

    for(int i = 0; i < 3; i++){
        if(isInCircle(centers[2*i], centers[2*i + 1], radius) == 1){
            inCircle = 1;
            uCenter = centers[2*i];
            vCenter = centers[2*i+1];
        }
    }

    //float modDist = distFromCenter(uMod, vMod);
    float numIters = 10;
    float noiseVal = 0;

    PP = transform("common", "shader", P);

    for(int i = 0; i <= numIters; i++){
        noiseVal += noise(PP * pow(2, numIters));
    }

    noiseVal = (float) noiseVal / (numIters + 1.0);

    float displacementFactor;
    float maxHeight = radius * 0.05;
    normal Ndisplace;
    normal NcenterDirection;
    normal Nrand;
    float uDiff;
    float vDiff;
    if(inCircle == 1){
        Cout = makeStoneColor(noiseVal);
        float modDist = distFromCenter(uCenter, vCenter);
        float ang = acos(modDist / maxHeight);
        displacementFactor = maxHeight * sin(ang);
        Ndisplace = displacementFactor * normalize(N);
        NcenterDirection = directionFromCenter(uCenter, vCenter);
        Nout = (normal) normalize(Ndisplace + NcenterDirection);
    }
    else{
        Cout = makeMossColor(noiseVal);
        displacementFactor = maxHeight * noiseVal;
        //Ndisplace = displacementFactor
        Nrand = (normal) snoise(noiseVal * 100);
        Ndisplace = displacementFactor * normalize(Nrand);
        Nout = normalize(Nrand);
    }

    changeV = Ndisplace;
    //displace(displacementFactor);
    //point pShader = transform("shader",P);

    //Nout = (normal) (P + changeV);

}