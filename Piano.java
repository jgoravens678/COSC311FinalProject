import java.util.*;

public class Piano extends Ri{
    public static final double[] PianoDimensions = {76.0, 65.6, 26.0};

    //baseline instantaited objects to reference
    public RectangularPrism genericPrism = new RectangularPrism();
    public PianoKeys Keyboard = new PianoKeys();


    void makePiano(){
        double baseboardDiff = 5.0;
        double baseboardHeight = 5.0;



        double[] KeyboardBaseDimensions = {PianoDimensions[0], 2.0, 15.0};
        double keyboardHeight = 28.0;
        RiTransformBegin();
        RiPattern("woodShader", "woodShader");

        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "woodShader:Cout",  RI_NULL);
        RiTranslate(0.0,keyboardHeight - PianoDimensions[1] / 2.0, -(PianoDimensions[2] + KeyboardBaseDimensions[2]) / 2.0);
        genericPrism.createPrism(KeyboardBaseDimensions[2], KeyboardBaseDimensions[0], KeyboardBaseDimensions[1]);
        RiTransformEnd();

        double[] sideMoldDimensions = {4.0, 8.0, KeyboardBaseDimensions[2] * 0.9};
        double[] innerMoldDimensions = {sideMoldDimensions[0] * .8, sideMoldDimensions[1] * 1.1, KeyboardBaseDimensions[2] };
        RiTransformBegin();
        RiTranslate((-PianoDimensions[0] / 2.0) + (sideMoldDimensions[0] / 2.0), keyboardHeight + KeyboardBaseDimensions[1] / 2.0 - PianoDimensions[1] / 2.0, -PianoDimensions[2] / 2.0  );

        makeSideMolds(innerMoldDimensions);
        makeSideMolds(sideMoldDimensions);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate((PianoDimensions[0] / 2.0) -(sideMoldDimensions[0] / 2.0 ), keyboardHeight + KeyboardBaseDimensions[1] / 2.0 - PianoDimensions[1] / 2.0, -PianoDimensions[2] / 2.0  );
        //RiRotate(-90.0, 1.0, 0.0, 0.0);
        makeSideMolds(innerMoldDimensions);
        makeSideMolds(sideMoldDimensions);
        RiTransformEnd();

        double keyboardScaleFactor = (PianoDimensions[0] - 2.0 *sideMoldDimensions[0]) /  Keyboard.DIMENSIONS[0];
        double keyboardSetback = 1.0;

        RiTransformBegin();
        //RiScale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor);
        RiTranslate(0.0, -(PianoDimensions[1] / 2.0 - keyboardHeight - KeyboardBaseDimensions[1] / 2.0) , -(PianoDimensions[2] / 2.0) - (KeyboardBaseDimensions[2]) + (keyboardSetback) );
        RiScale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor);
        Keyboard.makeKeyboard();
        RiTransformEnd();

        //Piano Legs/Supports
        //Recall: dimensions of
        double legAugmentFactor = 1.0;

        double legCrossDistance = legAugmentFactor * (1 / (Math.sin(60))) * KeyboardBaseDimensions[2];
        double[] legStructDimensions = {8,26,16};

        double sx = 1.0, sy = keyboardHeight / legStructDimensions[1], sz = 1.0;//(legCrossDistance/ 2) / (legStructDimensions[2]);

        double wheelRadius = KeyboardBaseDimensions[2] / 2.0;
        double wheelDepth = 0.8 * sx * legStructDimensions[0];

        RiTransformBegin();
        RiPattern("woodShader", "woodShader");
        //RiBxdf("PxrSurface", "checkerShader", "color diffuseColor", "");
        //RiBxdf("PxrSurface","checkerShader","color diffuseColor", "checkerShader:Cout", "color transmissionColor", "jerShader:Cout");
        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "woodShader:Cout",  RI_NULL);
        RiTranslate( (-PianoDimensions[0] / 2.0) + (1.2 * legStructDimensions[0] / 2.0) + (wheelDepth), -PianoDimensions[1] / 2.0, -(PianoDimensions[2] / 2.0) - (legAugmentFactor * KeyboardBaseDimensions[2] / 2.0) );
        RiRotate(-180.0, 0.0, 1.0, 0.0);
        new Piano().makePianoLeg(sx * 1.2, sy*1.0, sz*0.8);
        new Piano().makePianoLeg(sx*1.1, sy*1.0, sz*0.9);
        new Piano().makePianoLeg(sx*1.0, sy*1.0, sz*1.0);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate( (PianoDimensions[0] / 2.0) - (1.2 * legStructDimensions[0] / 2.0) - (wheelDepth), -PianoDimensions[1] / 2.0, -(PianoDimensions[2] / 2.0) - (legAugmentFactor * KeyboardBaseDimensions[2] / 2.0) );
        RiRotate(180.0, 0.0, 1.0, 0.0);
        new Piano().makePianoLeg(sx * 1.2, sy*1.0, sz*0.8);
        new Piano().makePianoLeg(sx*1.1, sy*1.0, sz*0.9);
        new Piano().makePianoLeg(sx*1.0, sy*1.0, sz*1.0);
        RiTransformEnd();



        RiTransformBegin();
        RiTranslate(-(PianoDimensions[0] / 2.0) + (wheelDepth / 2.0),-(PianoDimensions[1] / 2.0) - (wheelRadius) + (keyboardHeight),-wheelRadius - (PianoDimensions[2] / 2.0));
        RiRotate(90.0, 0.0, 1.0, 0.0);
        new Piano().makeConcaveCylinder(wheelRadius * 0.8, wheelDepth * 1.2);
        new Piano().makeConcaveCylinder(wheelRadius * 0.9, wheelDepth * 1.1);
        new Piano().makeConcaveCylinder(wheelRadius, wheelDepth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(-(PianoDimensions[0] / 2.0) + (3.0 * wheelDepth / 2.0) + (1.2*legStructDimensions[0]),-(PianoDimensions[1] / 2.0) - (wheelRadius) + (keyboardHeight),-wheelRadius - (PianoDimensions[2] / 2.0));
        RiRotate(90.0, 0.0, 1.0, 0.0);
        new Piano().makeConcaveCylinder(wheelRadius * 0.8, wheelDepth * 1.2);
        new Piano().makeConcaveCylinder(wheelRadius * 0.9, wheelDepth * 1.1);
        new Piano().makeConcaveCylinder(wheelRadius, wheelDepth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate((PianoDimensions[0] / 2.0) - (wheelDepth / 2.0),-(PianoDimensions[1] / 2.0) - (wheelRadius) + (keyboardHeight),-wheelRadius - (PianoDimensions[2] / 2.0));
        RiRotate(90.0, 0.0, 1.0, 0.0);
        new Piano().makeConcaveCylinder(wheelRadius * 0.8, wheelDepth * 1.2);
        new Piano().makeConcaveCylinder(wheelRadius * 0.9, wheelDepth * 1.1);
        new Piano().makeConcaveCylinder(wheelRadius, wheelDepth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate((PianoDimensions[0] / 2.0) - (3.0 * wheelDepth / 2.0) - (1.2*legStructDimensions[0]),-(PianoDimensions[1] / 2.0) - (wheelRadius) + (keyboardHeight),-wheelRadius - (PianoDimensions[2] / 2.0));
        RiRotate(90.0, 0.0, 1.0, 0.0);
        new Piano().makeConcaveCylinder(wheelRadius * 0.8, wheelDepth * 1.2);
        new Piano().makeConcaveCylinder(wheelRadius * 0.9, wheelDepth * 1.1);
        new Piano().makeConcaveCylinder(wheelRadius, wheelDepth);
        RiTransformEnd();


        RiTransformBegin();
        RiTranslate(0.0, baseboardHeight, 0.0);
        makeTopper(0.65, baseboardDiff);
        RiTransformEnd();

        RiTransformBegin();
        makeTendrils(0.65, PianoDimensions[1]);
        RiTransformEnd();

        RiTransformBegin();
        RiPattern("woodShader", "woodShader");
        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "woodShader:Cout",  RI_NULL);
        RiTranslate(0.0, -PianoDimensions[1] / 2.0  ,0.0);
        makeBaseboard(PianoDimensions[0], PianoDimensions[2] , PianoDimensions[0] + (baseboardDiff / 2.0), PianoDimensions[2] + (baseboardDiff / 2.0), baseboardHeight / 2.0, false, 0.0);
        RiTranslate(0.0, -baseboardHeight / 2.0, 0.0);
        makeBaseboard(PianoDimensions[0] + (baseboardDiff / 2.0), PianoDimensions[2] + (baseboardDiff / 2.0), PianoDimensions[0] + baseboardDiff, PianoDimensions[2] + baseboardDiff, baseboardHeight / 2.0, false
        ,0.0);
        RiTransformEnd();

        //Make trims on the body of the piano.
        //Contingent upon the placement of the keyboard.

        //First, the trim on the front.

        double trimWidth = 3.0;
        double trimScaleFactor = 0.7;
        double frontTrimWidth = 0.7 *(PianoDimensions[1] - keyboardHeight), frontTrimLength = 0.7 * PianoDimensions[0];

        RiTransformBegin();
        RiTranslate(0.0, (-PianoDimensions[1] / 2.0) + (keyboardHeight) + (frontTrimWidth / 2.0) + ( ((1 - trimScaleFactor) / trimScaleFactor) * frontTrimWidth / 2.0 ) ,-PianoDimensions[2]/2.0);
        makeSquareTrim(frontTrimLength, frontTrimWidth, trimWidth);
        RiTransformEnd();

        //Now, make the side trims.
        double sideTrimLength = trimScaleFactor * (PianoDimensions[1]), sideTrimWidth = trimScaleFactor * PianoDimensions[2];

        RiTransformBegin();
        RiTranslate(- PianoDimensions[0] / 2.0, 0.0, 0.0);
        RiRotate(90.0, 0.0, 0.0, -1.0);
        RiRotate(-90.0, 1.0, 0.0, 0.0);
        makeSquareTrim(sideTrimLength, sideTrimWidth, trimWidth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(PianoDimensions[0] / 2.0, 0.0, 0.0);
        RiRotate(-90.0, 0.0, 0.0, -1.0);
        RiRotate(-90.0, 1.0, 0.0, 0.0);
        makeSquareTrim(sideTrimLength, sideTrimWidth, trimWidth);
        RiTransformEnd();


        //Make Piano Body
        RiTransformBegin();
        RiTranslate((PianoDimensions[0] / 2.0 - 1.0), 0.0, 0.0);
        genericPrism.createPrism(PianoDimensions[2], 2.0, PianoDimensions[1]);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(-(PianoDimensions[0] / 2.0 - 1.0), 0.0, 0.0);
        genericPrism.createPrism(PianoDimensions[2], 2.0, PianoDimensions[1]);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(0.0, 0.0, (PianoDimensions[2] / 2.0 - 1.0));
        genericPrism.createPrism(2.0, PianoDimensions[0], PianoDimensions[1]);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(0.0, 0.0, -(PianoDimensions[2] / 2.0 - 1.0));
        genericPrism.createPrism(2.0, PianoDimensions[0], PianoDimensions[1]);
        RiTransformEnd();
        return;
    }

    void makeTendrils(double breakpoint, double heightAbovePiano){
        Random rand = new Random();
        int numCurves = 100;
        int numPoints = 7; //make sure this is odd
        /*
        * Restrict control points to particular y-planes.
        * First control vertex should lie within the body of the piano.
        * Second should lie at the piano's opening, on the same xz-plane as the top.
        * Randomize the next n-2 in particular planes above the piano.
        * Recall that the piano is still centered at the origin when we're creating these curves.
         */
        double xTranslateOpening = PianoDimensions[0] / 2.0 - (PianoDimensions[0] * breakpoint) - (PianoDimensions[0] * (1 - breakpoint) / 2.0);
        double[] pointAtOpening = {xTranslateOpening, PianoDimensions[1] / 2.0, PianoDimensions[2] / 2.0};

        double points[][] = new double[numCurves * numPoints][3];
        double radius = PianoDimensions[2];
        double numHeightTiers = ((numPoints - 2) + 1) / 2.0;
        double tierCount = numHeightTiers;
        //double heightOfTier = heightAbovePiano / numHeightTiers;
        double heightOfTier = heightAbovePiano / (numPoints - 2);

        for(int curveNo = 0; curveNo < numCurves; curveNo++){
            for (int i = 0; i < 3; i++) {
                points[numPoints * curveNo][i] = 0.8 * (PianoDimensions[i] * rand.nextDouble() - PianoDimensions[i] / 2.0);
            }
            points[numPoints*curveNo + 1] = pointAtOpening;
            double currHeight = PianoDimensions[1] / 2.0;
            for (int pointNo = 2; pointNo < numPoints; pointNo++){
                points[numPoints*curveNo + pointNo][0] = pointAtOpening[0] + (pointNo - 1) * 2 * radius * (rand.nextDouble() - 0.5);
                points[numPoints*curveNo + pointNo][2] = pointAtOpening[2] + (pointNo - 1) * 2 * radius * (rand.nextDouble() - 0.5);
                if (true){
                    points[numPoints*curveNo + pointNo][1] = currHeight + heightOfTier;
                    currHeight += heightOfTier;
                }
                else{
                    points[numPoints*curveNo + pointNo][1] = currHeight - heightOfTier;
                    currHeight -= heightOfTier;
                }
                //tierCount--;
            }
        }

        int[] numVertsArray = new int[numCurves];
        for (int i = 0; i < numCurves; i++){
            numVertsArray[i] = numPoints;
        }

        double[] normals[] = new double[3*numCurves][3];
        for (int i = 0; i < numCurves; i++){
            normals[3*i] = new double[]{15.0, 0.0, 0.0};
            normals[3*i+1] = new double[]{-8.0, 0.0, 15.0};
            normals[3*i+2] = new double[]{-8.0, 0.0, -15.0};
            //normals[4*i+3] = new double[]{-1.0, 1.0, 0.0};
        }

        double[] PALE_GOLD = {0.94, 0.82, 0.37};
        double[] GOLD = {0.906, 0.652, 0.016};
        RiTransformBegin();
        RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",GOLD, "float glowGain", 0.5, "color glowColor", PALE_GOLD);
        RiCurves("cubic", numCurves, numVertsArray, "nonperiodic", "P", points, "constantwidth", 1, "N", normals);
        RiTransformEnd();
    }

    /*Make a square-shaped trim in the xy-plane (bottom of trim lies on the plane z=0)
    Dimensions: x = length, y = width
    trimWidth : refers to the width of the trim itself (note: distinct from the width parameter, which gives one dimension of the rectalgular geometry of the larger object)
    magnitudes based on the central polygon of the trim (e.g. draw a rectangle through the equicenter of all sides)
     */
    void makeSquareTrim(double length, double width, double trimWidth){
        RiTransformBegin();
        RiTranslate(-length / 2.0, 0.0, 0.0);
        makeGenericTrim(width, trimWidth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(length / 2.0, 0.0, 0.0);
        RiRotate(180.0, 0.0, 0.0, 1.0);
        makeGenericTrim(width, trimWidth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(0.0, -width / 2.0, 0.0);
        RiRotate(90.0, 0.0, 0.0, 1.0);
        makeGenericTrim(length, trimWidth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(0.0, width / 2.0, 0.0);
        RiRotate(-90.0, 0.0, 0.0, 1.0);
        makeGenericTrim(length, trimWidth);
        RiTransformEnd();
    }

    /*
    Make one side of a square trimming.
    Parameters:
    length: refers to the length of the longer part of the trim. on x-axis
    width: refers to how wide the strip should be (likewise determines depth). on y-axis
     */
    void makeGenericTrim(double length, double width){
        double hw = width / 2.0, hl = length / 2.0;
        double qw = width / 4.0;
        double[][] points = {
                {-hw, hl+hw, 0.0},
                {-qw, hl+qw, -qw},
                {0.0, hl, -width},
                {qw, hl-qw,-qw},
                {hw, hl-hw, 0.0},
                {-hw, -hl-hw, 0.0},
                {-qw,-hl-qw, -qw},
                {0.0, -hl, -width},
                {qw, -hl+qw, -qw},
                {hw,-hl+hw, 0.0}
        };

        int[][] faces = {
                {0,1,6,5},
                {0,4,9,5},
                {3,4,9,8},
                {1,2,7,6},
                {2,3,8,7},
        };

        int[][] pentFaces = {
                {0,1,2,3,4},
                {5,6,7,8,9}
        };

        double[][] polyPts = new double[4][3];
        for (int[] mappedPts : faces){
            for (int i = 0 ; i < 4; i++){
                polyPts[i] = points[mappedPts[i]];
            }
            RiTransformBegin();
            RiPolygon(4, RI_P, polyPts, RI_NULL);
            RiTransformEnd();
        }

        double[][] pentPolyPts = new double[5][3];
        for (int[] mappedPts : pentFaces){
            for (int i = 0 ; i < 5; i++){
                pentPolyPts[i] = points[mappedPts[i]];
            }
            RiTransformBegin();
            RiPolygon(5, RI_P, polyPts, RI_NULL);
            RiTransformEnd();
        }

    }

    void makeSideMolds(double[] dims){
        String scheme = "catmull-clark";
        int numFaces = 9;
        int[] nverts = {4,4,4,4,4,4,4,7,7};
        int[] vertids = {
                0,1,3,2,
                1,0,8,9,
                9,8,10,11,
                11,10,12,13,
                13,12,6,7,
                7,6,4,5,
                5,4,2,3,
                0,2,4,6,12,10,8,
                3,1,9,11,13,7,5
        };

        int ntags = 6;
        String crease = "crease";
        String[] tags = {crease, crease, crease, crease,crease,crease};
        //String[] tags = {"crease", "crease", "crease", "crease", "crease", "crease"};
        int[] nargs = {5,1, 5,1, 8,1, 8,1, 2,1, 5,1};
        int[] intargs = {0,8,9,1,0,
                9,8,10,11,9,
                0,2,4,6,12,10,8,0,
                3,1,9,11,13,7,5,3,
                3,2,
                7,6,4,5,7
        };
        double[] floatargs = {10.0, 10.0, 3.0, 3.0, 1.0, 2.0};

        String P = "P";
        double hd = 3.0;
        double[][] points = {
                {-hd, 12.0, 0.0},
                {hd, 12.0, 0.0},
                {-hd, 12.0, 9.0},
                {hd, 12.0, 9.0},
                {-hd, 6.0, 6.0},
                {hd, 6.0, 6.0},
                {-hd, 6.0, 12.0},
                {hd,6.0,12.0},
                {-hd, 0.0, 0.0},
                {hd, 0.0, 0.0},
                {-hd, 0.0, 9.0},
                {hd, 0.0, 9.0},
                {-hd, 2.0, 12.0},
                {hd, 2.0, 12.0}
        };

        RiTransformBegin();
        RiScale(dims[0]/6.0, dims[1]/12.0 , dims[2]/ 12.0 );
        RiRotate(-90.0, 1.0, 0.0, 0.0);
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        //genericPrism.createPrism(BlackKeyDimensions[0], BlackKeyDimensions[1], WhiteKeyDimensions[2]);
        RiTransformEnd();
    }

    void makePianoLeg(double sx, double sy, double sz){
        String scheme = "catmull-clark";
        int numFaces = 15;
        int[] nverts = {4,5,5,4,4,4,4,4,4,4,4,4,4,4,4};
        //int[] vertids = {0,1,2,3, 0,3,4,7, 1,0,7,6, 1,6,5,2, 3,2,5,4, 6,7,4,5};
        int[] vertids = {
                0,1,3,2,
                1,0,4,6,7,
                2,3,9,8,5,
                0,2,5,4,
                3,1,7,9,
                4,5,8,6,
                9,7,11,13,
                7,6,10,11,
                8,9,13,12,
                6,8,12,10,
                10,12,16,14,
                12,13,17,16,
                11,10,14,15,
                13,11,15,17,
                15,14,16,17
        };

        int ntags = 8;
        String crease = "crease";
        String[] tags = new String[8];
        for (int i = 0; i < tags.length; i++){
            tags[i] = crease;
        }
        //String[] tags = {"crease", "crease", "crease", "crease", "crease", "crease", "corner"};
        int[] nargs = {2,1, 6,1, 6,1, 5,1, 5,1, 5,1, 5,1, 5,1};
        int[] intargs = {
                0,2,
                1,0,4,6,7,1,
                2,3,9,8,5,2,
                8,9,13,12,8,
                7,6,10,11,7,
                12,13,17,16,12,
                11,10,14,15,11,
                15,14,16,17,15
        };

        double topEdgeIntens = 8.0;
        double sideIntens = 2.0;
        double bottomIntens  = 3.0;
        double[] floatargs = {topEdgeIntens, sideIntens, sideIntens, sideIntens, sideIntens, sideIntens, sideIntens, bottomIntens};

        String P = "P";

        double[][] points = {
                {4,26,0},
                {4,26,16},
                {-4,26,0},
                {-4,26,16},
                {4,18,0},
                {-4,18,0},
                {4,12,8},
                {4,12,16},
                {-4,12,8},
                {-4,12,16},
                {3,4,6},
                {3,4,12},
                {-3,4,6},
                {-3,4,12},
                {2,0,10},
                {3,0,14},
                {-2,0,10},
                {-3,0,14}
        };

        RiTransformBegin();
        //RiBxdf("PxrSurface","marbleShader","color diffuseColor", new double[] {1.0, 0.0, 0.0});
        RiScale(sx, sy, sz);
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        RiTransformEnd();

        //return new double[] {8, 26, 16};
    }

    //Concave faces oriented perpendicular to z-axis.
    void makeConcaveCylinder(double radius, double depth){
        String scheme = "catmull-clark";
        int numFaces = 12;
        String P = "P";
        double hr = radius , hd = depth / 2.0;
        double epsilon = hd / 15.0;
        double[][] pointsConcave = {
                {-hr,-hr,hd},
                {hr,-hr,hd},
                {hr,-hr,0.0},
                {-hr,-hr,0.0},
                {-hr,hr,0.0},
                {hr,hr,0.0},
                {hr,hr,hd},
                {-hr,hr,hd}
        };
        double[][] points = {
                {-hr,-hr,hd},
                {hr,-hr,hd},
                {hr,-hr,-hd},
                {-hr,-hr,-hd},
                {-hr,hr,-hd},
                {hr,hr,-hd},
                {hr,hr,hd},
                {-hr,hr,hd},
                {0.0,0.0,epsilon},
                {0.0, 0.0, -epsilon}
        };
        int[] nverts = {4,4,4,4,3,3,3,3,3,3,3,3};
        int[] vertids = {0,1,2,3, 0,3,4,7, 1,6,5,2, 6,7,4,5, 0,8,1, 0,7,8, 6,8,7, 1,8,6, 2,5,9, 5,4,9, 4,3,9, 3,2,9};

        int ntags = 3;
        int ntagsConcave = 2;
        int ntagsCylinder = 4;
        String crease = "crease", corner = "corner";
        String[] tagsConcave = {"hole", crease};
        String[] tagsCylinder = {"hole", "hole", crease, crease};
        String[] tags = {crease, crease, corner};
        int[] nargs = {5,1,5,1,2,1};

        int[] intargs = {
                1,0,7,6,1,
                3,2,5,4,3,
                8,
                9
        };

        int[] nargsConcave = {1,0, 5, 1};
        int[] nargsCylinder = {1,0,1,0, 5,1, 5,1};

        int[] intargsConcave = {
                2,
                1,0,7,6,1
        };
        int[] intargsCylinder = {
                2,
                4,
                1,0,7,6,1,
                3,2,5,4,3
        };
        double[] floatargsConcave = {2.0};
        double[] floatargsCylinder = {2.0, 2.0};
        double[] floatargs = {1.0,1.0,5.0};

        double[] backfacingVals = {2,2,2,2,2,2,2,2,2,2,2,2};

        RiTransformBegin();
        RiSides(2);
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        RiSides(2);
        //RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntagsCylinder, tagsCylinder, nargsCylinder, intargsCylinder, floatargsCylinder, P, pointsCylinder);
        //RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntagsConcave, tagsConcave, nargsConcave, intargsConcave, floatargsConcave, P, pointsConcave);
        //RiRotate(180.0, 1.0, 0.0, 0.0);
        //RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntagsConcave, tagsConcave, nargsConcave, intargsConcave, floatargsConcave, P, pointsConcave);
        RiTransformEnd();

        /*
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
         */
        //double[] floatargs = {10.0, 9.0, 9.0, 9.0, 7.0, 10.0};

        //String P = "P";
        /*
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
        */




    }


    void makeSpikyCube(double s){
        String scheme = "catmull-clark";
        int numFaces = 6;
        int[] nverts = {4,4,4,4,4,4};
        int[] vertids = {0,1,2,3, 0,3,4,7, 1,0,7,6, 1,6,5,2, 3,2,5,4, 6,7,4,5};

        int ntags = 7;
        String[] tags = {"crease", "crease", "crease", "crease", "crease", "crease", "corner"};
        int[] nargs = {5,1,5,1,5,1,5,1,5,1,5,1,8,1};
        int[] intargs = {
                0,1,2,3,0,
                7,6,5,4,7,
                2,1,6,5,2,
                0,3,4,7,0,
                1,0,7,6,1,
                3,2,5,4,3,
                0,1,2,3,4,5,6,7
        };

        double creaseIntens = 0.2;
        double[] floatargs = {creaseIntens, creaseIntens, creaseIntens, creaseIntens, creaseIntens, creaseIntens, 4.0};

        String P = "P";
        double hs = s / 2.0;
        double[][] points = {
                {-hs,-hs,hs},
                {hs,-hs,hs},
                {hs,-hs,-hs},
                {-hs,-hs,-hs},
                {-hs,hs,-hs},
                {hs,hs,-hs},
                {hs,hs,hs},
                {-hs,hs,hs}
        };

        RiTransformBegin();
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        RiTransformEnd();
    }

    /*Creates a baseboard mold that's effecticely been smashed/broken through.
    * Simulates the appearance of broken wood.
    * Parameters
    * double breakpointRatio: indicates what percentage over from the right of the baseboard geometry should the break begin.
     */
    void makeTopper(double breakpointRatio, double distToOuterEdge){
        double breakpoint = (PianoDimensions[0] / 2.0) - (PianoDimensions[0] * breakpointRatio);
        double breakRange = PianoDimensions[0] / 20.0;
        int numPts = 200; //MUST be even
        //double distToOuterEdge = 4.0;


        Random rand = new Random();
        double[] deepTopCoords = new double[numPts];
        double[] deepBottomCoords = new double[numPts];
        double[] longLeftCoords = new double[numPts];
        double[] longRightCoords = new double[numPts];
        double[][] allCoords = {deepTopCoords, deepBottomCoords, longLeftCoords, longRightCoords};

        for (int i = 0; i < numPts; i++){
            deepTopCoords[i] = ((PianoDimensions[2] + 2.0 * distToOuterEdge) * rand.nextDouble()) - (PianoDimensions[2] / 2.0 + distToOuterEdge);
            deepBottomCoords[i] = (PianoDimensions[2] * rand.nextDouble()) - (PianoDimensions[2] / 2.0);
            longLeftCoords[i] = - rand.nextDouble() * (breakRange) + (breakpoint);
            longRightCoords[i] = - rand.nextDouble() * (breakRange) + (breakpoint - breakRange);
        }

        for (double[] arr : allCoords){
            Arrays.sort(arr);
        }

        //Points specifies all coordinates making up the jagged pieces in the broken wood plank.
        //Ordered first by z coordinate (ascending), then by y coordinate (descending on premise, but randomized below)
        //Code to proceed defines all points.
        double[][] points = new double[2 * numPts + 4][3];

        points[0] = new double[] {breakpoint, PianoDimensions[1] / 2.0, -(PianoDimensions[2] ) / 2.0 - distToOuterEdge};
        points[1] = new double[] {breakpoint, PianoDimensions[1] / 2.0 - distToOuterEdge, -(PianoDimensions[2] ) / 2.0 };
        points[2 * numPts + 2] = new double[] {breakpoint, PianoDimensions[1] / 2.0, (PianoDimensions[2] ) / 2.0 + distToOuterEdge};
        points[2 * numPts + 3] = new double[] {breakpoint, PianoDimensions[1] / 2.0 - distToOuterEdge, (PianoDimensions[2] ) / 2.0 };

        double lowerHeight = PianoDimensions[1] / 2.0 - distToOuterEdge;
        double upperHeight = PianoDimensions[1] / 2.0;
        double bottomScaleFactor = PianoDimensions[2] / (PianoDimensions[2] + 2 * distToOuterEdge);

        for (int index = 2; index < 2 * numPts + 2; index = index + 2){
            int coordIndex = index / 2;
            if (index % 4 == 2){
                points[index][0] = longLeftCoords[coordIndex - 1];
                points[index+1][0] = longLeftCoords[coordIndex];
            }
            else{
                points[index][0] = longRightCoords[coordIndex - 2];
                points[index+1][0] = longRightCoords[coordIndex - 1];
            }
            points[index][1] = upperHeight;
            points[index+1][1] = lowerHeight;

            points[index][2] = deepTopCoords[(index - 2)/2];
            points[index+1][2] = deepTopCoords[(index - 2)/2];
            //points[index+1][2] = deepBottomCoords[(index - 2)/2]; //add back in to simulate ribbons
            /*
            double coinFlip = rand.nextDouble();
            if (coinFlip < 0.5){
                points[index][2] =
            }
            else{

            }

             */
        }

        //Now, we proceed to construct the polygonal representatiom/rendering of this object.

        //First, make a broken baseboard. Rotate and translate accordingly.
        RiTransformBegin();
        RiTranslate(0.0, PianoDimensions[1] / 2.0 , 0.0);
        RiRotate(180.0, 1.0, 0.0, 0.0);
        makeBaseboard(PianoDimensions[0], PianoDimensions[2], PianoDimensions[0] + 2.0 *distToOuterEdge, PianoDimensions[2] + 2.0 *distToOuterEdge, distToOuterEdge, true, breakpoint );
        RiTransformEnd();

        //Next, make the jagged/broken part of the baseboard.
        double[][] polyPts = new double[4][3];
        for (int i = 0; i < points.length - 2; i = i + 2){
            polyPts[0] = points[i];
            polyPts[1] = points[i+2];
            polyPts[2] = points[i+3];
            polyPts[3] = points[i+1];
            RiTransformBegin();
            RiPattern("splinterShader", "splinterShader");
            RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "splinterShader:Cout", "reference normal bumpNormal", "splinterShader:NN", "reference float presence", "splinterShader:presence",  RI_NULL);
            RiPolygon(4, RI_P, polyPts, RI_NULL);
            RiTransformEnd();
        }

        //Finally, we create the complex (and concave) polygon that defines the topper for the broken board.
        for (int i = 0; i < points.length - 2; i = i + 2){
            polyPts[0] = points[i];
            polyPts[1] = points[i+2];
            polyPts[2] = new double[] {PianoDimensions[0] / 2.0 + distToOuterEdge ,polyPts[1][1], polyPts[1][2]};
            polyPts[3] = new double[] {PianoDimensions[0] / 2.0 + distToOuterEdge ,polyPts[0][1], polyPts[0][2]};
            RiTransformBegin();
            RiPolygon(4, RI_P, polyPts, RI_NULL);
            RiTransformEnd();
        }



        /*
        double[][] triPolyPts = new double[3][3];
        for (int i = 0; i <= 2 *numPts -2; i = i+4){
            triPolyPts[0] = points[2*i];

        }
        topPolyPts[topPolyPts.length - 2] = new double[] {PianoDimensions[0] / 2.0 + distToOuterEdge, PianoDimensions[1] / 2.0, (PianoDimensions[2] ) / 2.0 + distToOuterEdge};
        topPolyPts[topPolyPts.length-1] = new double[] {PianoDimensions[0] / 2.0 + distToOuterEdge, PianoDimensions[1] / 2.0, -(PianoDimensions[2] ) / 2.0 - distToOuterEdge};

        RiTransformBegin();
        RiGeneralPolygon(topPolyPts.length, RI_P, topPolyPts, RI_NULL);
        RiTransformEnd();
        */






    }


    //copycat method
    //
    void makeBaseboard(double innerLength, double innerWidth, double outerLength, double outerWidth, double height, boolean broken, double breakpoint){
        double lengthDiff = outerLength - innerLength, widthDiff = outerWidth - innerWidth;
        double lv2length = outerLength - (lengthDiff / 4.0), lv2width = outerWidth - (widthDiff / 4.0);
        double lv1height = height / 4.0;
        double lv2height = 6.0 * height / 8.0;
        double[][] dims = {
                {outerLength, outerWidth, 0.0},
                {outerLength, outerWidth, lv1height},
                {lv2length, lv2width, lv2height},
                {innerLength, innerWidth, height}
        };

        double[][] points = defineBaseboardVertices(broken, breakpoint, dims);
        /*
        int dim = 0;

        double[][] points = new double[16][3];
        for (int lvl = 0; lvl < 4; lvl = lvl + 1){
            //dim = 0;
            points[4*lvl][dim] = -dims[lvl][dim]/2.0;
            points[4*lvl+1][dim] = dims[lvl][dim]/2.0;
            points[4*lvl+2][dim] = -dims[lvl][dim]/2.0;
            points[4*lvl+3][dim] = dims[lvl][dim]/2.0;
            //dim = 1;
            points[4*lvl][1] = dims[lvl][2];
            points[4*lvl+1][1] = dims[lvl][2];
            points[4*lvl+2][1] = dims[lvl][2];
            points[4*lvl+3][1] = dims[lvl][2];
            //dim = 2;
            points[4*lvl][2] = -dims[lvl][1]/2.0;
            points[4*lvl+1][2] = -dims[lvl][1]/2.0;
            points[4*lvl+2][2] = dims[lvl][1]/2.0;
            points[4*lvl+3][2] = dims[lvl][1]/2.0;
        }

         */

        int[][] vertids = {
                {0,2,3,1},
                {0,4,6,2},
                {0,1,5,4},
                {1,3,7,5},
                {2,6,7,3},
                //4,6,7,5,
                {8,10,6,4},
                {10,11,7,6},
                {11,9,5,7},
                {9,8,4,5},
                //8,10,11,9,
                {10,8,12,14},
                {14,15,11,10},
                {15,13,9,11},
                {13,12,8,9},
                {13,15,14,12}
        };

        double[][] polyPoints = new double[4][3];
        for (int[] mappedPts : vertids){
            for (int i = 0; i < 4; i++){
                polyPoints[i] = points[mappedPts[3-i]];
            }
            RiTransformBegin();
            RiSides(2);
            RiPolygon(4, RI_P, polyPoints, RI_NULL);
            RiSides(2);
            RiTransformEnd();
        }

    };

    //assume breaking from right
    double[][] defineBaseboardVertices(boolean broken, double breakpoint, double[][] dims){
        double[][] points = new double[16][3];
        if (!broken) {
            for (int lvl = 0; lvl < 4; lvl = lvl + 1) {
                //dim = 0;
                points[4 * lvl][0] = -dims[lvl][0] / 2.0;
                points[4 * lvl + 1][0] = dims[lvl][0] / 2.0;
                points[4 * lvl + 2][0] = -dims[lvl][0] / 2.0;
                points[4 * lvl + 3][0] = dims[lvl][0] / 2.0;
                //dim = 1;
                points[4 * lvl][1] = dims[lvl][2];
                points[4 * lvl + 1][1] = dims[lvl][2];
                points[4 * lvl + 2][1] = dims[lvl][2];
                points[4 * lvl + 3][1] = dims[lvl][2];
                //dim = 2;
                points[4 * lvl][2] = -dims[lvl][1] / 2.0;
                points[4 * lvl + 1][2] = -dims[lvl][1] / 2.0;
                points[4 * lvl + 2][2] = dims[lvl][1] / 2.0;
                points[4 * lvl + 3][2] = dims[lvl][1] / 2.0;
            }
        }
        else{
            for (int lvl = 0; lvl < 4; lvl = lvl + 1) {
                int dim = 0;
                points[4 * lvl][dim] = breakpoint;
                points[4 * lvl + 1][dim] = dims[lvl][dim] / 2.0;
                points[4 * lvl + 2][dim] = breakpoint;
                points[4 * lvl + 3][dim] = dims[lvl][dim] / 2.0;
                //dim = 1;
                points[4 * lvl][1] = dims[lvl][2];
                points[4 * lvl + 1][1] = dims[lvl][2];
                points[4 * lvl + 2][1] = dims[lvl][2];
                points[4 * lvl + 3][1] = dims[lvl][2];
                //dim = 2;
                points[4 * lvl][2] = -dims[lvl][1] / 2.0;
                points[4 * lvl + 1][2] = -dims[lvl][1] / 2.0;
                points[4 * lvl + 2][2] = dims[lvl][1] / 2.0;
                points[4 * lvl + 3][2] = dims[lvl][1] / 2.0;
            }
        }
        return points;
    }



    //length = x, width = z, height = y
    void makeBaseboardMold(double innerLength, double innerWidth, double outerLength, double outerWidth, double height){
        double lengthDiff = outerLength - innerLength, widthDiff = outerWidth - innerWidth;
        double lv2length = outerLength - (lengthDiff / 4.0), lv2width = outerWidth - (widthDiff / 4.0);
        double lv1height = height / 4.0;
        double lv2height = 6.0 * height / 8.0;
        double[][] dims = {
                {outerLength, outerWidth, 0.0},
                {outerLength, outerWidth, lv1height},
                {lv2length, lv2width, lv2height},
                {innerLength, innerWidth, height}
        };
        int dim = 0;
        double[][] points = new double[16][3];
        for (int lvl = 0; lvl < 4; lvl = lvl + 1){
            //dim = 0;
            points[4*lvl][dim] = -dims[lvl][dim]/2.0;
            points[4*lvl+1][dim] = dims[lvl][dim]/2.0;
            points[4*lvl+2][dim] = -dims[lvl][dim]/2.0;
            points[4*lvl+3][dim] = dims[lvl][dim]/2.0;
            //dim = 1;
            points[4*lvl][1] = dims[lvl][2];
            points[4*lvl+1][1] = dims[lvl][2];
            points[4*lvl+2][1] = dims[lvl][2];
            points[4*lvl+3][1] = dims[lvl][2];
            //dim = 2;
            points[4*lvl][2] = -dims[lvl][1]/2.0;
            points[4*lvl+1][2] = -dims[lvl][1]/2.0;
            points[4*lvl+2][2] = dims[lvl][1]/2.0;
            points[4*lvl+3][2] = dims[lvl][1]/2.0;
        }

        String scheme = "catmull-clark";
        int numFaces = 14;
        int[] nverts = new int[14];
        for (int i = 0; i < 14; i++){
            nverts[i] = 4;
        }
        int[] vertids = {
                0,2,3,1,
                0,4,6,2,
                0,1,5,4,
                1,3,7,5,
                2,6,7,3,
                //4,6,7,5,
                8,10,6,4,
                10,11,7,6,
                11,9,5,7,
                9,8,4,5,
                //8,10,11,9,
                10,8,12,14,
                14,15,11,10,
                15,13,9,11,
                13,12,8,9,
                13,15,14,12
        };

        int ntags = 8;
        String crease = "crease";
        String[] tags = {crease, crease, crease, crease, crease, crease, crease,crease};
        //String[] tags = {"crease", "crease", "crease", "crease", "crease","crease","crease","crease","crease","crease","crease","crease","crease","crease","crease","crease", };
        //int[] nargs = {5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1, 5,1};
        int[] nargs = {5,1, 5,1, 5,1, 5,1, 4,1, 4,1, 4,1, 4,1};

        int[] intargs = {0,1,3,2,0,
                4,5,7,6,4,
                8,9,11,10,8,
                12,13,15,14,12,
                0,4,8,12,
                1,5,9,11,
                3,7,11,15,
                2,6,10,14
        };

        /*
        int[] intargs = {
                 0,2,3,1,0,
                4,5,7,6,4,
                8,9,11,10,8,
                 0,4,6,2,0,
                 0,1,5,4,0,
                 1,3,7,5,1,
                 2,6,7,3,2,
                 8,10,6,4,8,
                 10,11,7,6,10,
                 11,9,5,7,11,
                 9,8,4,5,9,
                 10,8,12,14,10,
                 14,15,11,10,14,
                 15,13,9,11,15,
                 13,12,8,9,13,
                 13,15,14,12,13
        };

         */
        //double[] floatargs = {10.0, 10.0, 10.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 10.0};
        double[] floatargs = {4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0};

        //double length = BlackKeyDimensions[0], width = BlackKeyDimensions[1], height = BlackKeyDimensions[2];
        //double whiteHeight = WhiteKeyDimensions[2] / 2.0;

        String P = "P";

        RiTransformBegin();
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        RiTransformEnd();

    };


}