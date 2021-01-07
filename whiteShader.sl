surface whiteShader(output color Cout = 0){
    float g = 1.0 - v;
    float b = 1.0 - v;

    Cout = color (1.0, g,b);
}