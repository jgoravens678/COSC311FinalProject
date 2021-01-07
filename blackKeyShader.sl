#define BLACK       color(0.0, 0.0, 0.0)
#define DARK_GRAY   color(0.3, 0.3, 0.3)
#define GRAY        color(0.6, 0.6, 0.6)
#define LIGHT_GRAY  color(0.9, 0.9, 0.9)


surface blackKeyShader(output color Cout = BLACK, output normal N1 = N){
    vector upV = vector (0,1,0);
    normal NN;
    float dotProd;
    float cosine;
    float noise1;
    float noise2;
    float noise3;
    float noise4;
    float aggNoise;
    color c1;
    color c2;
    normal N2 = normal (0.3, -0.5, -1.0);

    NN = transform("common", "shader", N);
    NN = normalize(NN);
    dotProd = dot(upV, NN);

    cosine = acos(dotProd);

    if (cos(2.0 * M_PI / 9.0) <= dotProd && dotProd <= 1){
        noise1 = noise(P);
        noise2 = noise(8*u, 13*v);
        aggNoise = noise1 + noise2 / 2.0;
        if(aggNoise > 0.9){
            Cout = DARK_GRAY;
            N1 = cross(N,N2);
    }
}
}