#define GRAIN_COLOR       color (0.1367, 0.0976, 0.0898)
#define LIGHT_WOOD_COLOR  color (0.9375, 0.746, 0.531)
//#define REDDER_WOOD_COLOR color(0.30, 0.1563, 0.097)
//#define COOL_WOOD_COLOR   color(0.3125, 0.2305, 0.2031)

float angularNoiseDisplace(vector V, float f){
    return snoise(V * 10, f * 5);
}

float angularNoiseWidth(vector V, float f){
    return snoise(V*13, f*6);
}

int isInCircle(float u1, float v1, float radius){
    if (sqrt((u1 - u) * (u1 - u) + (v1-v)*(v1 - v)) <= radius){
        return 1;
    }
    return 0;
}



int isOnRing(float uC, float vC, float radius, float numRings){
    float ringProp = 0.2;
    float ringwidth = (radius / numRings) * ringProp;

    //OSL will not let us declare an array with an unknown int as its length, seemingly, so we will set this array to be far larger than actually needed, and then iterate over only nonempty values.
    float centers[10];

    centers[0] = (1 / numRings) * 0.5 * radius;
    for(int i = 1; i < numRings; ++i){
        centers[i] = (1 / numRings) * (i + 0.5) * radius;
    }

    float u_comp = u - uC;
    float v_comp = v - vC;
    vector direction = vector (u_comp, v_comp, 0);
    vector unitDirection = normalize(direction);

    float noiseDisplace = 2.0 * angularNoiseDisplace(unitDirection, uC);
    float noiseWidth = 2.0 * angularNoiseWidth(unitDirection, uC);

    int toRet = 0;
    float widthScale = (radius / numRings) * 0.15;
    float displaceScale = (radius / numRings) * 0.25;
    float amtDisplace = displaceScale * noiseDisplace;
    float newRingwidth = ringwidth + ( widthScale * noiseWidth);
    float rangeMin;
    float rangeMax;
    float Distance;
    vector zero = vector (0.0, 0.0, 0.0);
    for(int i = 0; i < numRings; ++i){
        rangeMin = (centers[i] + amtDisplace) - (newRingwidth / 2.0);
        rangeMax = (centers[i] + amtDisplace) + (newRingwidth / 2.0);
        Distance = distance(direction, zero);
        if (rangeMin <= Distance && Distance <= rangeMax){
            toRet = 1;
        }
    }

    return toRet;
}

float grainNoiseWidth(float stripeNumber){
    return cellnoise(stripeNumber);
}

float grainNoiseDisplace(){
    return snoise(v * 100, 50*u);
}

//possibly revisit to refine the upper and lower bounding circles in this method...technically not as precise as possible, as we do not account for radial/angular impact

point findStripeNumber(float centers[], float radii[], int numBands){
    int minStripe = 1;
    int maxStripe = numBands;
    float numGrains = (float) numBands;
    //First, find all circles for which the u-parameter overlaps with that of the current position.
    float uMin;
    float uMax;
    float vRadiusAsBand;
    int topCircleIndex = -1;
    int bottomCircleIndex = -1;
    for(int circle = 0; circle < arraylength(radii); circle++){
        uMin = (centers[2 * circle] - 1.5 * radii[circle]);
        uMax = (centers[2 * circle] + 1.5 * radii[circle]);
        if(uMin <= u && u <= uMax){
            vRadiusAsBand = centers[2*circle + 1] * numGrains;
            if (v <= centers[2 * circle + 1]){
                maxStripe = (int) floor(vRadiusAsBand);
                if(bottomCircleIndex == -1){
                    bottomCircleIndex = circle;
                }
                else if (centers[2 * bottomCircleIndex + 1] > centers[2 * circle + 1] ){
                    bottomCircleIndex = circle;
                }
            }
            else{
                minStripe = (int) ceil(vRadiusAsBand);
                if(topCircleIndex == -1){
                    topCircleIndex = circle;
                }
                else if (centers[2 * topCircleIndex + 1] < centers[2 * circle + 1] ){
                    topCircleIndex = circle;
                }
            }
        }
    }

    float vUpper = 1.0;
    float vLower = 0.0;
    float angle;

    if (topCircleIndex != -1){
        angle = acos( abs(u - centers[2 * topCircleIndex]) / radii[topCircleIndex] );
        vLower = centers[2 * topCircleIndex + 1] + radii[topCircleIndex] * sin(angle);
    }
    if(bottomCircleIndex != -1){
        angle = acos( abs(u - centers[2 * bottomCircleIndex]) / radii[bottomCircleIndex] );
        vUpper = centers[2 * bottomCircleIndex + 1] - radii[bottomCircleIndex] * sin(angle);
    }

    float numStripes = (float) (maxStripe - minStripe + 1);
    float vRange = vUpper - vLower;
    float vRelativeStripe = ((v - vLower) / vRange) * numStripes;

    float stripeNumber = floor(vRelativeStripe) + ((float) minStripe);

    float vStripeLower = vLower + ((vRange / numStripes) * floor(vRelativeStripe));
    float vStripeUpper = vStripeLower + (vRange / numStripes);

     //toRet = {stripeNumber, vStripeLower, vStripeUpper};
    point toRet = point (stripeNumber, vStripeLower, vStripeUpper);
    return toRet;

}

surface woodShader(
    float uBigRingCenter = 0.2,
    float vBigRingCenter = 0.3,
    float uMidRingCenter = 0.6,
    float vMidRingCenter = 0.75,
    float uSmallRingCenter = 0.9,
    float vSmallRingCenter = 0.2,
    float uSideRingCenter = 0.05,
    float vSideRingCenter = 0.9,
    float uCentralRingCenter = 0.5,
    float vCentralRingCenter = 0.4,
    float bigRadius = 0.15,
    float midRadius = 0.1,
    float smallRadius = 0.3,
    float sideRadius = 0.07,
    float centralRadius = 0.1,
    float numBigRings = 5,
    float numMidRings = 4,
    float numSmallRings = 7,
    float numSideRings = 3,
    float numCentralRings = 8,
    float numRings = 5,
    float bandwidthProp = 0.25,
    int numBands = 30,
    output color Cout = color(0.9375, 0.746, 0.531)
)

{
    /* Set bandwidth by dividing the surface into numBands stripes, then multiplying by some fractional component.
    */

    color REDDER_WOOD_COLOR = color (0.30, 0.1563, 0.097);
    color COOL_WOOD_COLOR = color (0.3125, 0.2305, 0.2031);

    REDDER_WOOD_COLOR = REDDER_WOOD_COLOR - 0.10;
    COOL_WOOD_COLOR = COOL_WOOD_COLOR - 0.10;

    float ringCenters[10] = {uBigRingCenter, vBigRingCenter, uMidRingCenter, vMidRingCenter, uSmallRingCenter, vSmallRingCenter, uSideRingCenter, vSideRingCenter, uCentralRingCenter, vCentralRingCenter};
    float radii[5] = {bigRadius, midRadius, smallRadius, sideRadius, centralRadius};
    float ringNums[5] = {numBigRings, numMidRings, numSmallRings, numSideRings, numCentralRings};
    int isInsideCircle = 0;

    for (int circle = 0; circle < arraylength(radii); circle++){
        if (isInCircle(ringCenters[2*circle], ringCenters[2 * circle + 1], radii[circle]) == 1){
            isInsideCircle = 1;
            if(isOnRing(ringCenters[2 * circle], ringCenters[2 * circle + 1], radii[circle], ringNums[circle]) == 1){
                Cout = GRAIN_COLOR;
            }
            else{
                float distFromCenter = sqrt( pow(u - ringCenters[2 * circle], 2) + pow(v - ringCenters[2 * circle + 1], 2) );
                float moduloRing = mod(distFromCenter, radii[circle] / ringNums[circle]);
                float colorVariance = smoothstep(0, 1, moduloRing / (radii[circle] / ringNums[circle]));

                point diff;

                for(int i = 0; i < 3; i++){
                    diff[i] = COOL_WOOD_COLOR[i] - REDDER_WOOD_COLOR[i];
                }

                color newWoodColor;
                for(int i = 0; i < 3; i++){
                    newWoodColor[i] = (REDDER_WOOD_COLOR[i]) + (diff[i] * abs(2 * (colorVariance - 0.5)) );
                }
                Cout = newWoodColor;
            }
        }
    }

    if(isInsideCircle == 0){
        point stripeNumbers = findStripeNumber(ringCenters, radii, numBands);
        float displaceNoise = grainNoiseDisplace();
        float widthNoise = (grainNoiseWidth(stripeNumbers[0]) * 2.0) - 1.0;
        float bandRange = stripeNumbers[2] - stripeNumbers[1];
        float bandCenter = stripeNumbers[1] + (bandRange / 2.0);

        float stripeWidthScale = 0.15;
        float stripeRange = bandRange * (bandwidthProp + (widthNoise * stripeWidthScale));

        float stripeDisplaceScale = 0.2;
        float stripeCenter = bandCenter + (displaceNoise * stripeDisplaceScale * bandRange);

        float halfStripeWidth = stripeRange / 2.0;

        float outOfGrainRange = (bandRange - (halfStripeWidth * 2.0)) / 2.0;
        float colorVariance;
        float bound;
        int isInStripe = 0;
        if(stripeCenter - halfStripeWidth <= v && v <= stripeCenter + halfStripeWidth){
            Cout = GRAIN_COLOR;
            isInStripe = 1;
        }
        if(v < stripeCenter - halfStripeWidth){
            bound = stripeCenter -halfStripeWidth;
            colorVariance = smoothstep(0,1,(bound - v) / (bound - ( (bandRange/ 2.0) - halfStripeWidth)));

        }
        if(stripeCenter + halfStripeWidth < v){
             bound = stripeCenter + halfStripeWidth;
             colorVariance = smoothstep(0,1,(v - bound)/( (bandRange / 2.0) - halfStripeWidth ));
        }
        if(isInStripe == 0){
            point diff;

            for(int i = 0; i < 3; i++){
                diff[i] = REDDER_WOOD_COLOR[i] - COOL_WOOD_COLOR[i];
            }

            color newWoodColor;
            for(int i = 0; i < 3; i++){
                newWoodColor[i] = COOL_WOOD_COLOR[i] + (diff[i] * colorVariance);
            }
            Cout = newWoodColor;
        }
    }



}