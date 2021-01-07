//========
/*
* Encapsulates the rendering capabilities/functionalities to create the keyboard for our piano.
* The bottom left corner of the keyboard is centered at the origin, intrinsically.
* Dimensions of Keyboard in DIMENSIONS, for reference.
 */
//========
public class PianoKeys extends Ri{
    //Dimensions of Piano Keys, immutable; order: Length, Width, Height
    public static final double[] WhiteKeyDimensions = {15.0, 2.2, 2.0};
    public static final double[] BlackKeyDimensions = {9.5, 0.95, 1.0};

    //Number of piano keys
    public static final double numWhiteKeys = 2 + 7 * (3 + 4);

    //Space between piano keys, likewise immutable
    public static final double space = 0.04;

    //Dimensions of the keyboard.
    public static final double[] DIMENSIONS = {WhiteKeyDimensions[1] * numWhiteKeys + (space * (numWhiteKeys - 1)), WhiteKeyDimensions[2], WhiteKeyDimensions[0]};

    //baseline object to reference
    public RectangularPrism genericPrism = new RectangularPrism();

    void makeKeyboard(){
        double transUnit = WhiteKeyDimensions[1] + space;

        /*
        RiTransformBegin();
        RiTranslate(-2.0 * transUnit DIMENSIONS[0], 0.0, 0.0);
        RiTransformEnd();

         */

        RiTransformBegin();
        RiTranslate(-DIMENSIONS[0] / 2.0, 0.0, 0.0);
        makeThreeKeyTile();
        RiTransformEnd();

        double numKeysSurpassed = 2.0;
        for(int i = 0; i < 7; i++) {
            RiTransformBegin();
            RiTranslate( (numKeysSurpassed) * transUnit - (DIMENSIONS[0] / 2.0), 0.0, 0.0);
            makeFiveKeyTile();
            RiTranslate( 3.0 * transUnit , 0.0, 0.0);
            makeSevenKeyTile();
            //RiTranslate((numKeysSurpassed + 3.0) * transUnit, 0.0, 0.0);
            //makeFiveKeyTile();
            RiTransformEnd();
            numKeysSurpassed = numKeysSurpassed + 7.0;
        }

        /*
        RiTransformBegin();
        RiTranslate(-2.0 * transUnit + DIMENSIONS[0], 0.0, 0.0);
        RiTransformEnd();

         */
    }

    void makeThreeKeyTile(){
        double dx = ( WhiteKeyDimensions[1] + space ) / 2.0;
        double tailLength = WhiteKeyDimensions[0] - BlackKeyDimensions[0];
        //double dz = ( WhiteKeyDimensions[0] - BlackKeyDimensions[0] + space) / 2.0;
        double dz = tailLength + (BlackKeyDimensions[0] / 2.0) + space;
        RiTransformBegin();
        RiTranslate(dx, 0.0, 0.0);
        makeLeftKey();
        RiTranslate(dx, 0.0, dz);
        makeBlackKey();
        RiTranslate(dx, 0.0, -dz);
        makeRightKey();
        RiTransformEnd();
    }

    void makeFiveKeyTile(){
        double dx = ( WhiteKeyDimensions[1] + space ) / 2.0;
        double tailLength = WhiteKeyDimensions[0] - BlackKeyDimensions[0];
        //double dz = ( WhiteKeyDimensions[0] - BlackKeyDimensions[0] + space) / 2.0;
        double dz = tailLength + (BlackKeyDimensions[0] / 2.0) + space;
        RiTransformBegin();
        RiTranslate(dx, 0.0, 0.0);
        makeLeftKey();
        RiTranslate(dx, 0.0, dz);
        makeBlackKey();
        RiTranslate(dx, 0.0, -dz);
        makeMiddleKey();
        RiTranslate(dx, 0.0, dz);
        makeBlackKey();
        RiTranslate(dx, 0.0, -dz);
        makeRightKey();
        RiTransformEnd();
    }

    void makeSevenKeyTile(){
        double dx = ( WhiteKeyDimensions[1] + space ) / 2.0;
        double tailLength = WhiteKeyDimensions[0] - BlackKeyDimensions[0];
        //double dz = ( WhiteKeyDimensions[0] - BlackKeyDimensions[0] + space) / 2.0;
        double dz = tailLength + (BlackKeyDimensions[0] / 2.0) + space;
        RiTransformBegin();
        RiTranslate(dx, 0.0, 0.0);
        makeLeftKey();
        RiTranslate(dx, 0.0, dz);
        makeBlackKey();
        RiTranslate(dx, 0.0, -dz);
        makeMiddleKey();
        RiTranslate(dx, 0.0, dz);
        makeBlackKey();
        RiTranslate(dx, 0.0, -dz);
        makeMiddleKey();
        RiTranslate(dx, 0.0, dz);
        makeBlackKey();
        RiTranslate(dx, 0.0, -dz);
        makeRightKey();
        RiTransformEnd();
    }

    void makeMiddleKey(){
        double tailLength = WhiteKeyDimensions[0] - BlackKeyDimensions[0];
        double headWidth = WhiteKeyDimensions[1] - BlackKeyDimensions[1];
        RiTransformBegin();
        RiPattern("dingyKeyShader", "dingyKeyShader");
        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "dingyKeyShader:Cout",  RI_NULL);
        RiTranslate(0.0, 0.0, tailLength / 2.0);
        genericPrism.createPrism(tailLength, WhiteKeyDimensions[1], WhiteKeyDimensions[2]);
        //RiTranslate(0.0, 0.0, (tailLength + BlackKeyDimensions[0]) / 2.0);
        RiTranslate(0.0, 0.0, (tailLength + BlackKeyDimensions[0]) / 2.0);
        genericPrism.createPrism(BlackKeyDimensions[0], headWidth, WhiteKeyDimensions[2]);
        RiTransformEnd();
    };

    void makeLeftKey(){
        double tailLength = WhiteKeyDimensions[0] - BlackKeyDimensions[0];
        double headWidth = WhiteKeyDimensions[1] - (BlackKeyDimensions[1] / 2.0);
        RiTransformBegin();
        RiPattern("dingyKeyShader", "dingyKeyShader");
        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "dingyKeyShader:Cout",  RI_NULL);
        RiTranslate(0.0, 0.0, tailLength / 2.0);
        genericPrism.createPrism(tailLength, WhiteKeyDimensions[1], WhiteKeyDimensions[2]);
        RiTranslate(-(WhiteKeyDimensions[1] - headWidth)/ 2.0, 0.0, (tailLength + BlackKeyDimensions[0]) / 2.0);
        genericPrism.createPrism(BlackKeyDimensions[0], headWidth, WhiteKeyDimensions[2]);
        RiTransformEnd();
    };

    void makeRightKey(){
        double tailLength = WhiteKeyDimensions[0] - BlackKeyDimensions[0];
        double headWidth = WhiteKeyDimensions[1] - (BlackKeyDimensions[1] / 2.0);
        RiTransformBegin();
        RiPattern("dingyKeyShader", "dingyKeyShader");
        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "dingyKeyShader:Cout",  RI_NULL);
        RiTranslate(0.0, 0.0, tailLength / 2.0);
        genericPrism.createPrism(tailLength, WhiteKeyDimensions[1], WhiteKeyDimensions[2]);
        RiTranslate((WhiteKeyDimensions[1] - headWidth)/ 2.0, 0.0, (tailLength + BlackKeyDimensions[0]) / 2.0);
        genericPrism.createPrism(BlackKeyDimensions[0], headWidth, WhiteKeyDimensions[2]);
        RiTransformEnd();
    };

    void makeBlackKey(){
        //Here, we specify the parameters for our subdivision mesh scheme, which will form the top of the black piano keys.
        String scheme = "catmull-clark";
        int numFaces = 6;
        int[] nverts = {4,4,4,4,4,4};
        int[] vertids = {0,1,2,3, 0,3,4,7, 1,0,7,6, 1,6,5,2, 3,2,5,4, 6,7,4,5};

        int ntags = 6;
        String[] tags = {"crease", "crease", "crease", "crease", "crease", "crease"};
        int[] nargs = {5,1, 5,1, 5,1, 5,1, 5,1, 5,1};
        int[] intargs = {0,1,2,3,0,
                7,6,5,4,7,
                2,1,6,5,2,
                0,3,4,7,0,
                1,0,7,6,1,
                3,2,5,4,3
                };
        double[] floatargs = {10.0, 9.0, 9.0, 9.0, 7.0, 10.0};

        double length = BlackKeyDimensions[0], width = BlackKeyDimensions[1], height = BlackKeyDimensions[2];
        double whiteHeight = WhiteKeyDimensions[2] / 2.0;
        //Scaling coefficients to dictate molding on top of key
        double widthTaper = 1.0, frontCap = 0.8;
        String P = "P";
        double[][] points = {
                {- width / 2.0, whiteHeight, -length / 2.0},
                {width / 2.0, whiteHeight, -length / 2.0},
                {width / 2.0, whiteHeight, length / 2.0},
                {- width / 2.0, whiteHeight, length / 2.0},
                {- width * widthTaper / 2.0, whiteHeight + height, length / 2.0},
                {width * widthTaper / 2.0, whiteHeight + height, length / 2.0},
                {width * widthTaper / 2.0, whiteHeight + height, -length * frontCap / 2.0},
                {-width * widthTaper / 2.0, whiteHeight + height, -length * frontCap / 2.0}
        };
        RiTransformBegin();
        RiPattern("blackKeyShader", "blackKeyShader");
        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "blackKeyShader:Cout", "reference normal bumpNormal", "blackKeyShader:N1",  RI_NULL);
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        genericPrism.createPrism(BlackKeyDimensions[0], BlackKeyDimensions[1], WhiteKeyDimensions[2]);
        RiTransformEnd();
    }



}