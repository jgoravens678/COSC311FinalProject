import java.util.*;
public class LightWand extends Ri{

    private double bulbHeight = 0.0;
    private double wandLength = 0.0;

    private double[] Black = {0.0, 0.0, 0.0};
    private double[] LightGray = {0.8, 0.8, 0.8};
    double[] Pink = {1.0, 0.3, 0.4};
    double[] PALE_GOLD = {0.94, 0.82, 0.37};
    double[] GOLD_LIGHT_COLOR = {0.87, 0.62, 0.16};
    double[] LIGHT_GRAY = {0.75, 0.75, 0.75};
    double[] BLACK = {0.0, 0.0, 0.0};

    /*
    * Create the bulb for the lightwand
    * Parameter values:
    * double radius: refers to the (regular-hexagonal: distance from centroid to vertex) radius of the base of the bulb
    * double height: height of the bulb
     */

    //Actual height = 1.5*height
    void makeLightWand(double radius, double height, double intensity){
        RiTransformBegin();
        //RiRotate(180.0, 0.0, 1.0, 0.0);
        RiRotate(90.0, 0.0, 0.0, 1.0);
        //RiTranslate(0.0, 0.75*height, 0.0);
        //RiTranslate(0.0, -30.0, 0.0);
        //RiRotate(45.0, 0.0, 1.0, 0.0);
        makeBulb(radius, 1.2 * height);
        RiTranslate(0.0, 1.2 * height / 4.0, 0.0);
        makeLight(radius, height / 2.0, intensity);
        RiTranslate(0.0, -0.75*height, 0.0);
        makeWand(0.5 *radius, 0.75* height);
        RiTransformEnd();
    }

    private void makeBulb(double radius, double height){
        String scheme = "catmull-clark";
        int numFaces = 23;
        int[] nverts = {
                4,4,4,4,4,4,
                4,4,4,4,4,4,
                4,4,4,4,4,4,
                4,4,4,3,3
        };
        //int[] vertids = {0,1,2,3, 0,3,4,7, 1,0,7,6, 1,6,5,2, 3,2,5,4, 6,7,4,5};
        int[] vertids = {
                0,1,7,6,
                7,1,3,9,
                9,3,5,11,
                11,5,4,10,
                10,4,2,8,
                8,2,0,6,

                6,7,13,12,
                7,9,15,13,
                9,11,17,15,
                11,10,16,17,
                10,8,14,16,
                8,6,12,14,

                12,13,19,18,
                19,13,15,21,
                21,15,17,23,
                23,17,16,22,
                22,16,14,20,
                20,14,12,18,

                22,20,18,24,
                23,22,24,25,
                18,19,25,24,
                23,25,21,
                25,19,21
        };

        int ntags = 1;
        String[] tags = {"crease"};
        //int[] nargs= {};
        //int[] intargs = {};
        //double[] floatargs = {};

        int[] nargs = {7,1};
        int[] intargs = {
                0,1,3,5,4,2,0
        };

        double[] floatargs = {8.0};


        String P = "P";
        //double hs = s / 2.0;

        double sinSixty = Math.sin(3.14159/3.0), cosSixty = Math.cos(60);

        //Points in a hexagon with centroid-vertex distance = 1, centered at the origin.
        double[][] hexBasePoints = {
                {-0.5, 0.0, sinSixty},
                {0.5, 0.0, sinSixty},
                {-1.0, 0.0, 0.0},
                {1.0, 0.0, 0.0},
                {-0.5, 0.0, -sinSixty},
                {0.5, 0.0, -sinSixty},
        };


        double[][] points = new double[26][3];

        double radiusMultiplier = 0.0;
        double layerHeight = 0.0;
        for (int i = 0; i < 24; i++){
            if (i / 6 == 0){
                radiusMultiplier = radius;
                layerHeight = 0.0;
            }
            if(i / 6 == 1){
                radiusMultiplier = 2.0 * radius;
                layerHeight = height / 4.0;
            }
            if(i / 6 == 2){
                radiusMultiplier = 4.5 * radius;
                layerHeight = height / 2.0;
            }
            if(i / 6 == 3){
                radiusMultiplier = 3.0 * radius;
                layerHeight = 3.0 * height / 4.0;
            }
            points[i][0] = hexBasePoints[i % 6][0] * radiusMultiplier;
            points[i][2] = hexBasePoints[i % 6][2] * radiusMultiplier;
            points[i][1] = layerHeight;
        }

        //Note: we're still in the last conditional case for radiusMultiplier here...3*radius...corresponding to topmost hexagonal cross-section
        points[24] = new double[] {0.5 * radiusMultiplier, height, 0.0};
        points[25] = new double[] {1.5 * radiusMultiplier, height, 0.0};


        RiTransformBegin();
        RiBxdf("PxrSurface","surface","int diffuseDoubleSided",1,
                "float refractionGain", 0.6, "float reflectionGain", 0.6, "color refractionColor", LightGray, "float glassRoughness", 0.1);
        RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        RiTransformEnd();
    }

    private void makeLight(double radius, double height, double intensity){
        String scheme = "catmull-clark";
        int numFaces = 10;
        int[] nverts = {4,4,4,4,4,4,4,4,4,4};
        int[] vertids = {
                0,1,3,2,
                1,0,4,5,
                0,2,6,4,
                2,3,7,6,
                3,1,5,7,
                5,4,8,9,
                7,5,9,11,
                6,7,11,10,
                4,6,10,8,
                8,10,11,9
        };
        int ntags = 2;
        String[] tags = {"crease", "crease"};
        int[] nargs = {5,1,5,1};
        int[] intargs = {
                0,1,3,2,0,
                8,10,11,9,8
        };

        double[] floatargs = {10.0, 0.5};

        String P = "P";

        double thirdRadius = radius / 3.0;
        double[][] points = {
                {-radius, 0.0, -thirdRadius},
                {-thirdRadius, 0.0, -thirdRadius},
                {-thirdRadius, 0.0, -radius},
                {-radius, 0.0, -radius},
                {-radius, height / 2.0, radius},
                {-thirdRadius, height / 2.0, radius},
                {-thirdRadius, height / 2.0, thirdRadius},
                {-radius, height / 2.0, thirdRadius},
                {thirdRadius, height, radius},
                {radius, height, radius},
                {radius, height, thirdRadius},
                {thirdRadius, height, thirdRadius},
        };

        RiAttributeBegin();
        RiTransformBegin() ;
        RiLight("PxrMeshLight", "bulb", "float intensity", intensity, "color lightColor", GOLD_LIGHT_COLOR, RI_NULL);
        RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",PALE_GOLD);
        //RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        for(int i = 0; i < 4; i++) {
            RiRotate(90.0, 0.0, 1.0, 0.0);
            RiSubdivisionMesh(scheme, numFaces, nverts, vertids, ntags, tags, nargs, intargs, floatargs, P, points);
        }
        RiTransformEnd();
        RiAttributeEnd();
    }

    private void makeWand(double radius, double length){
        double swirlRadius = length / 5.0;
        double[] baselinePts[] = {
                {-swirlRadius, length, -swirlRadius / 6.0},
                {-swirlRadius, length, -swirlRadius / 18.0},
                {-swirlRadius, length, swirlRadius / 18.0},
                {-swirlRadius, length, swirlRadius / 6.0},
        };

        double[] points[] = new double[44][3];
        for (int i = 0; i < 4; i++){
            points[i] = baselinePts[i];
        }

        double splineLengthDiff = (swirlRadius / 6.0) / 8.0;
        double currRadius = splineLengthDiff;
        double angleStart = Math.PI / 3.0;
        double angleDiff = angleStart;
        double angle = angleStart;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 4; j++){
                points[4*(i+1) + j] = new double[] {baselinePts[j][0] + Math.cos(angle) * currRadius,
                        baselinePts[j][1] + Math.sin(angle) * currRadius, (9.0 + (double)i)* baselinePts[j][2] };
            }
            angle -= angleDiff;
            currRadius += splineLengthDiff;
        }

        for (int i = 36; i < 40; i++){
            points[i] = new double[] {0.0, length / 2.0, baselinePts[i - 36][2]};
            points[i+4] = new double[] {baselinePts[i - 36][0] / 4.0, 0.0, baselinePts[i - 36][2] / 8.0};
        }

        int nu = 11;
        int nv = 4;
        int uorder = 4;
        int vorder = 4;
        double[] uknot = {0,0,0,0,1,2,3,4,5,6,7,8,8,8,8};
        double[] vknot = {0,0,0,0,1,1,1,1};
        double umin = 0, umax = length / 4.0, vmin = 0, vmax = 3;


        double ninety = 90.0;
        double ang = ninety;
        //for(double ang = ninety; ang <= 2 * Math.PI; ang += ninety) {

        RiTransformBegin();
        RiBxdf("PxrSurface", "surface", "color specularFaceColor", LIGHT_GRAY, "color specularEdgeColor", BLACK,
                "color specularIor", new double[] {4.369684, 2.916713, 1.654698},
                "float specularRoughness", 1.0, "color specularExtinctionCoeff", new double[] {5.20643, 4.231366, 3.7549695}, RI_NULL);

        //RiTranslate(0.0, -60.0, 60.0);
        //RiRotate(15.0, 0.0, 0.0, 1.0);
        RiRotate(ang, 0.0, 1.0, 0.0);
        RiRotate(-15.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
        //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);

        RiRotate(15.0, 0.0, 0.0, 1.0);
        RiRotate(ang, 0.0, 1.0, 0.0);
        RiRotate(-15.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
        //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);

        RiRotate(15.0, 0.0, 0.0, 1.0);
        RiRotate(ang , 0.0, 1.0, 0.0);
        RiRotate(-15.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
        //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);

        RiRotate(15.0, 0.0, 0.0, 1.0);
        RiRotate(ang, 0.0, 1.0, 0.0);
        RiRotate(-15.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
        //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);
        RiTransformEnd();
    }


}