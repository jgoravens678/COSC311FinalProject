surface
metalShader (
        float Ka = 1,
        float Ks = 1,
        float roughness =.1,)
{
        normal Nf = faceforward (N, I);
        vector V = -normalize (I);
        Oi = Os;
        Ci = Os * Cs * (Ka * ambient () + Ks * specular (Nf, V, roughness));
}