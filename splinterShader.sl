#define LIGHT_WOOD_COLOR  color (0.9375, 0.746, 0.531)

surface splinterShader(output color Cout = LIGHT_WOOD_COLOR,
output float presence = 1.0,
output normal NN = N){
    float numSplinters = 200;
    float makeSplinterProb = cellnoise((1 - v) * numSplinters);
    float scaleProbByHeight = 0.4 * smoothstep(-0.5, 0.5, clamp(1-v, 0.25, 0.75));

    float prob = makeSplinterProb + scaleProbByHeight;
    if (prob < 0.3){
        presence = 0.0;
    }
    if(u < prob){
        presence = 0.0;
    }

    float modNumSplinters = mod(v , 1 / numSplinters);
    float displacementFromCenter = modNumSplinters / (1 / numSplinters) - (1 / numSplinters) / 2.0;
    float angle = asin(displacementFromCenter / ((1 / numSplinters) / 2.0));
    normal newN = normal (cos(angle) * N[0], sin(angle) * N[1], cos(angle) * N[2]);
    NN = newN;
}