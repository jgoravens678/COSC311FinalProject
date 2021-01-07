import java.util.*;

public class Vine extends Ri{
    public static double leafLength = 20.0;
    public static double[] DARK_GREEN = {0.234, 0.391, 0.059};
    public static double[] GOLD = {0.94, 0.82, 0.37};
    public static double[] SILVER = {0.8, 0.8, 0.8};
    public static double matteGlow = 0.0;
    public static double glowy = 0.8;
    public static double veryGlowy = 1.6;
    Random rand = new Random();

    private static double[] currColor = DARK_GREEN;
    private static double currGlow = 0.0;

    void makeVine(int numBranches, double[][] positions, int[] lengths, double[] rotations, int depth, double[] probs){
        if (depth != 0){
            //Random rand = new Random();
            //setColorParams(probs);
            double wobbleAngle = 15.0;
            double halfLength = leafLength / 2.0;
            double displace = 1.5;
            double[] newPositions[] = new double[numBranches * numBranches][2];
            int[] newLengths = new int[numBranches * numBranches];
            double[] newRotations = new double[numBranches * numBranches];

            double transAmount = 0.0;
            double radian;
            double absRadian;
            double ninety = Math.PI / 2.0;
            for (int branch = 0; branch < numBranches; branch++){
                for (int segment = 0; segment < lengths[branch]; segment++){
                    transAmount = halfLength * ((double) segment);
                    setColorParams(probs);
                    for (double layer = 0; layer < depth; layer++) {
                        //setColorParams(probs);
                        RiTransformBegin();
                        RiTranslate(positions[branch][0], positions[branch][1], 0.0);
                        RiRotate(rotations[branch], 0.0, 0.0, 1.0);
                        RiTranslate(0.0, transAmount, 0.0);
                        //RiTranslate(positions[branch][0], positions[branch][1], 0.0);
                        RiTranslate(0.0, 0.0, displace * layer);
                        RiRotate(2.0 * wobbleAngle * rand.nextDouble() - wobbleAngle, 0.0, 0.0, 1.0);
                        makeLeaf();
                        RiTransformEnd();
                    }
                }

                radian = rotations[branch] * (Math.PI / 180.0);
                absRadian = Math.abs(radian);
                for (int i=0; i < numBranches; i++) {
                    newPositions[branch*numBranches + i] = new double[]{transAmount * Math.cos(ninety + radian) + positions[branch][0], transAmount * Math.sin(ninety - absRadian) + positions[branch][1]};
                }
            }
            for (int i = 0; i < numBranches; i++){
                for (int j = 0; j < numBranches; j++){
                    newLengths[numBranches*i + j] = lengths[j];
                    newRotations[numBranches*i + j] = rotations[j];
                }
            }
            int newNumBranches = numBranches * numBranches;
            makeVine(newNumBranches, newPositions, newLengths, newRotations, depth - 1, probs);
        }
       return;
    }

    private void setColorParams(double[] probs){
        double colorChoice = rand.nextDouble();
        double glowChoice = rand.nextDouble();
        currGlow = (glowChoice <= 0.5) ? glowy : veryGlowy;
        if(colorChoice <= probs[0]){
            currColor = DARK_GREEN;
            currGlow = matteGlow;
        }
        else if(colorChoice <= probs[0] + probs[1]){
            currColor = GOLD;
        }
        else{
            currColor = SILVER;
        }
    }

    private void makeLeaf(){
        double maxLength = leafLength / 2.0;
        double[] mainSpline[] = {
                {0.0, 0.0, 0.0},
                {0.0, 0.3* maxLength / 10.0, 0.4 * maxLength / 10.0},
                {0.0, 0.7 * maxLength / 10.0, 0.4 * maxLength / 10.0},
                {0.0, maxLength / 10.0, 0.8* maxLength / 10.0}
        };

        int nu = 4;
        int nv = 4;
        int uorder = 4;
        int vorder = 4;
        double[] uknot = {0,0,0,0,1,2,3,4,5,6,7,8,8,8,8};
        double[] knot = {0,0,0,0,1,1,1,1};
        double umin = 0, umax = maxLength*30, vmin = 0, vmax = maxLength / 2.0;

        double[] points[] = new double[16][3];
        double angle = Math.PI / 18.0;
        double theSpline;
        for (int spline = 0; spline < 4; spline++){
            theSpline = (double) spline;
            for (int vertex = 0; vertex < 4; vertex++){
                points[4*spline + vertex][0] = (Math.sin(theSpline * angle)) * (maxLength - 2.0 * theSpline) * ((double) vertex / 4.0);
                points[4*spline + vertex][1] = mainSpline[vertex][1] * (Math.cos(theSpline * angle)) * (maxLength - 2.0 * theSpline);
                if(vertex == 0) {
                    points[4 * spline + vertex][2] = 1.5 * mainSpline[vertex][2] * maxLength;
                }
                else if (vertex != 3){
                    points[4 * spline + vertex ][2] = mainSpline[vertex][2] * maxLength / 2.0  ;
                }
                else{
                    points[4 * spline + vertex ][2] = 0.0;
                }
            }
        }

        double[] negPoints[] = new double[16][3];
        for (int i= 0; i < 16; i++){
            negPoints[i] = new double[]{-points[i][0], points[i][1], points[i][2]};
        }
        double[] one = {0.0, 0.0, 0.0};
        double[] two = {-maxLength / 2.0, 0.0, 0.0};
        double theta = 360.0;

        RiTransformBegin();
        RiRotate(90.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", negPoints);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", points);
        RiRotate(-180.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", negPoints);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", points);
        RiRotate(60.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", negPoints);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", points);
        RiRotate(60.0, 0.0, 0.0, 1.0);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", negPoints);
        RiNuPatch(nu, uorder, knot, umin, umax, nv, vorder, knot, vmin, vmax, "P", points);
        RiRotate(60.0, 0.0, 0.0, 1.0);
        RiBxdf("PxrSurface", "surface", "int diffuseDoubleSided", 1, "color diffuseColor", currColor,
                "float glowGain", currGlow, "color glowColor", currColor, RI_NULL);
        RiCurves("cubic", 1, new int[]{4}, "nonperiodic", "P", new double[][]{{0.0, 0.0, 0.0}, {-maxLength / 3.0, 2.0, -2.0}, {-2.0*maxLength / 3.0, -2.0, 2.0}, {-maxLength, 0.0, 0.0}}, "constantwidth", 0.3);
        RiTransformEnd();

    }
}