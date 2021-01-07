import java.util.*;
public class MusicNotes extends Ri{

    public static double[] GOLD = {0.94, 0.82, 0.37};

    public static double[] Unit[] = {
            {-2.0, -2.0, 0.0},
            {-2.0,  2.0, 0.0},
            { 2.0,  2.0, 0.0},
            { 2.0, -2.0, 0.0}};

    public double[] flatSplineBasis[] = {
            {-3.0, 3.0, 0.0},
            {-1.0, 3.0, 0.0},
            {1.0, 3.0, 0.0},
            {3.0, 3.0, 0.0},
            {-3.0, 1.0, 0.0},
            {-1.0, 1.0, 0.0},
            {1.0, 1.0, 0.0},
            {3.0, 1.0, 0.0},
            {-3.0, -1.0, 0.0},
            {-1.0, -1.0, 0.0},
            {1.0, -1.0, 0.0},
            {3.0, -1.0, 0.0},
            {-3.0, -3.0, 0.0},
            {-1.0, -3.0, 0.0},
            {1.0, -3.0, 0.0},
            {3.0, -3.0, 0.0},
    };

    void makeRandomNote(boolean isBendy){
        Random rand = new Random();
        double[] basis[] = new double[16][3];
        if(isBendy){
            //double[] basis[] = new double[16][3];
            for (int i = 0; i < 16; i++){
                for (int j = 0; j < 3; j++){
                    basis[i][j] = flatSplineBasis[i][j] + 2.25 *(rand.nextDouble() - 0.5);
                }
            }
        }
        else{
            basis = flatSplineBasis;
        }

        int noteChoice = rand.nextInt(4);
        switch(noteChoice){
            case 1: noteChoice = 0;
                    makeBassClef(basis);
                    break;
            case 2: noteChoice = 1;
                makeSixteenthNote(basis);
                break;
            case 3: noteChoice = 2;
                makeTrebleClef(basis);
                break;
            case 4: noteChoice = 3;
                makeEighthNote(basis);
                break;
        }
    }

    public double[] BLUE = {0.0, 0.0, 0.6};
    void makeEighthNote(double[][] basis){
        RiTransformBegin();
        RiPattern("eighthNoteShader", "eighth_note");
        RiBxdf("PxrSurface", "surface", "reference color specularFaceColor", "eighth_note:Cout", "reference color specularEdgeColor", "eighth_note:Cout",
                "color specularIor", new double[] {4.369684, 2.916713, 1.654698},
                "float specularRoughness", 0.1, "color specularExtinctionCoeff", new double[] {5.20643, 4.231366, 3.7549695},
                "reference float presence", "eighth_note:presence", RI_NULL);
        //RiRotate(-90.0,0.0,0.0,1.0);
        RiPatch("bicubic", "P", basis);
        RiTransformEnd();
    }

    void makeSixteenthNote(double[][] basis){
        RiTransformBegin();
        RiPattern("sixteenthNoteShader", "sixteenth_note");
        RiBxdf("PxrSurface", "surface", "reference color specularFaceColor", "sixteenth_note:Cout", "reference color specularEdgeColor", "sixteenth_note:Cout",
                "color specularIor", new double[] {4.369684, 2.916713, 1.654698},
                "float specularRoughness", 0.1, "color specularExtinctionCoeff", new double[] {5.20643, 4.231366, 3.7549695},
                "reference float presence", "sixteenth_note:presence", RI_NULL);
        //RiRotate(-90.0,0.0,0.0,1.0);
        RiPatch("bicubic", "P", basis);
        RiTransformEnd();
    }

    void makeTrebleClef(double[][] basis){
        RiTransformBegin();
        RiPattern("trebleClefShader", "treble_clef");
        RiBxdf("PxrSurface", "surface", "reference color specularFaceColor", "treble_clef:Cout", "reference color specularEdgeColor", "treble_clef:Cout",
                "color specularIor", new double[] {4.369684, 2.916713, 1.654698},
                "float specularRoughness", 0.1, "color specularExtinctionCoeff", new double[] {5.20643, 4.231366, 3.7549695},
                "reference float presence", "treble_clef:presence", RI_NULL);
        //RiRotate(-90.0,0.0,0.0,1.0);
        RiPatch("bicubic", "P", basis);
        RiTransformEnd();
    }

    void makeBassClef(double[][] basis){
        RiTransformBegin();
        RiPattern("bassClefShader", "bass_clef");
        RiBxdf("PxrSurface", "surface", "reference color specularFaceColor", "bass_clef:Cout", "reference color specularEdgeColor", "bass_clef:Cout",
                "color specularIor", new double[] {4.369684, 2.916713, 1.654698},
                "float specularRoughness", 0.1, "color specularExtinctionCoeff", new double[] {5.20643, 4.231366, 3.7549695},
                "reference float presence", "bass_clef:presence", RI_NULL);
        //RiRotate(-90.0,0.0,0.0,1.0);
        RiPatch("bicubic", "P", basis);
        RiTransformEnd();
    }
}