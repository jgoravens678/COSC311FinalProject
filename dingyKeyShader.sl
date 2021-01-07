#define WHITE            color (1.0, 1.0, 1.0)
#define VERY_PALE_YELLOW color (1.0, 1.0, 0.89)
#define PALE_YELLOW      color (1.0, 1.0, 0.78)
#define PALE_GOLD        color (1.0, 0.906, 0.55)
#define GOLD             color (0.94, 0.82, 0.37)
#define LIGHT_GRAY       color (0.87, 0.87, 0.87)
#define GRAY             color (0.3, 0.3, 0.3)

surface dingyKeyShader( output color Cout = WHITE
){
float midNoise;
float outNoise;
float splineLoc;

if(0.25 <= u <= 0.75 && 0.25 <= v <= 0.75){
    Cout = WHITE;
}
else if(0.1 <= v <= 0.9){
    midNoise = snoise(u,v);
    if(midNoise > 0.5){
        Cout = WHITE;
    }
    else{
        splineLoc = abs(midNoise);
        color midControls[8] = {PALE_GOLD, PALE_GOLD, WHITE, WHITE, WHITE, PALE_GOLD, GOLD};
        Cout = spline("catmull-rom", splineLoc, midControls);
    }
}
else{
    outNoise = snoise(u,v);
    splineLoc = abs(outNoise);
    color outerControls[8] = {PALE_GOLD, PALE_GOLD, GOLD, GOLD, GRAY, GRAY, LIGHT_GRAY, LIGHT_GRAY};
    Cout = spline("catmull-rom", outNoise, outerControls);
}
}