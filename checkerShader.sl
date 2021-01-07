
surface checkerShader ( float width = 0.1,
	output color Cout = 0)

{

	float up = mod(u,width);
	float vp = mod(v,width);
   	if (((up < width/2.0) && (vp < width/2.0)) || ((up >= width/2.0) && (vp >= width/2.0)))
   		Cout = color (0.1, 0.2, 0.4);
   	else
   		Cout = color (0.2, 0.1, 0.0);

}