#define GOLD             color (0.94, 0.82, 0.37)
shader bassClefShader(string filePath = "Bass_Clef.tex", output float presence = 1.0, output color Cout = GOLD){
    color pxColor;
    pxColor = (color) texture(filePath,u,v, "fill", 1.0);
    if (pxColor[0] > 0.5 && pxColor[1] > 0.5 && pxColor[2] > 0.5){
        presence = 0.0;
    }

    if(v > 0.9 || v < 0.1){
        presence = 0.0;
    }

    //Set borders manually...it's easier than other sorcery.
    if(u > 0.9 || u < 0.1){
        presence = 0.0;
    }
}