//===============================
/*
* This class simply creates the ground for our scene.
 */
//===============================
public class Ground extends Ri{

    public static double[] dimensions = {9.0, 8.0, 9.0};

    public static double[] heights[] = {
            {8,8,8,8,8,8,8},
            {7,7,7,7,7,7,8},
            {2,2,2,2,2,7,8},
            {4,4,4,4,2,7,8},
            {2,2,2,4,2,7,8},
            {0,0,2,4,2,7,8},
            {0,0,2,4,2,7,8},
            {0,0,2,4,2,7,8},
            {2,2,2,4,2,7,8},
            {4,4,4,4,2,7,8},
            {2,2,2,2,2,7,8},
            {7,7,7,7,7,7,8},
            {8,8,8,8,8,8,8}
    };

    public static double[] baseVertices[] = {
            {0, 0, 0},
            {3, 0, 0},
            {3, 0, -3},
            {0, 0, -3}
    };

    //increase x, z by 2
    public static double[] meshPoints[] = {
            {0, 0, 0},
            {3, 0, 0},
            {5, 4, 0},
            {6, 2, 0},
            {8, 8, 0},

            {0, 0, -3},
            {3, 0, -3},
            {5, 4, -3},
            {6, 2, -3},
            {8, 8, -3},

            {0, 4, -5},
            {3, 4, -5},
            {5, 4, -5},
            {6, 2, -5},
            {8, 8, -5},

            {0, 2, -6},
            {3, 2, -6},
            {5, 2, -6},
            {6, 2, -6},
            {8, 8, -6},

            {0, 8, -8},
            {3, 8, -8},
            {5, 8, -8},
            {6, 8, -8},
            {8, 8, -8}
    };

    public static double[] basePatch[] = {
            {0, 0, 0},
            {1, 0, 0},
            {2, 3, 0},
            {3, 4, 0},
            {0, 0, -1},
            {1, 0, -1},
            {2, 3, -1},
            {3, 4, -1},
            {0, 3, -2},
            {1, 3, -2},
            {2, 3, -2},
            {3, 4, -2},
            {0, 4, -3},
            {1, 4, -3},
            {2, 4, -3},
            {3, 4, -3}
    };

    public static double[] backPatch[] = {
            {3, 0, 0},
            {4, 1, 0},
            {5, 4, 0},
            {6, 0, 0},
            {3, 0, -1},
            {4, 1, -1},
            {5, 4, -1},
            {6, 0, -1},
            {3, 0, -2},
            {4, 1, -2},
            {5, 4, -2},
            {6, 0, -2},
            {3, 0, -3},
            {4, 1, -3},
            {5, 4, -3},
            {6, 0, -3}
    };

    public static double[] rightPatch[] = {
            {0, 0, -3},
            {1, 0, -3},
            {2, 0, -3},
            {3, 0, -3},
            {0, 1, -4},
            {1, 1, -4},
            {2, 1, -4},
            {3, 1, -4},
            {0, 4, -5},
            {1, 4, -5},
            {2, 4, -5},
            {3, 4, -5},
            {0, 0, -6},
            {1, 0, -6},
            {2, 0, -6},
            {3, 0, -6}
    };

    public static double[] cornerPatch[] = {
            {3, 0, -3},
            {4, 1, -3},
            {5, 4, -3},
            {6, 0, -3},
            {3, 1, -4},
            {4, 1, -4},
            {5, 4, -4},
            {6, 0, -4},
            {3, 4, -5},
            {4, 4, -5},
            {5, 4, -5},
            {6, 0, -5},
            {3, 0, -6},
            {4, 0, -6},
            {5, 0, -6},
            {6, 0, -6}
    };

    public static double[][] patches[] = {
           backPatch, rightPatch, cornerPatch
    };

    private static double scaleX = 1.0;
    private static double scaleY = 1.0;
    private static double scaleZ = 1.0;

    public Ground(double sx, double sy, double sz){
        this.scaleX = sx;
        this.scaleY = sy;
        this.scaleZ = sz;
    }

    public Ground(){}

    private void scalePatches(){
        for (double[][] patch : patches){
            for (double[] point : patch){
                point[0] *= scaleX;
                point[1] *= scaleY;
                point[2] *= scaleZ;
            }
        }
    }

    private void scalePatches(double sx,double sy,double sz){
        for (double[][] patch : patches){
            for (double[] point : patch){
                point[0] *= sx;
                point[1] *= sy;
                point[2] *= sz;
            }
        }
        for(double[] point : baseVertices){
            point[0] *= sx;
            point[1] *= sy;
            point[2] *= sz;
        }
        this.dimensions = new double[] {sx*this.dimensions[0], sy*this.dimensions[1], sz*this.dimensions[2]};
        this.scaleX = sx;
        this.scaleY = sy;
        this.scaleZ = sz;
    }

    public void makeGround(double sx, double sy, double sz){
        scalePatches(sx, sy, sz);
        int nu = 7;
        int nv = 7;
        int uorder = 4;
        int vorder = 4;
        double[] uknot = {0,0,0,0,1,2,3,4,4,4,4};
        double[] vknot = {0,0,0,0,1,2,3,4,4,4,4};
        double umin = 0, umax = 1, vmin = 0, vmax = 1;


        double[] points[] = new double[49][3];

        for (int row = 0; row < 4; row++){
            for(int i = 0; i < 4; i++){
                points[7*row + i] = basePatch[4*row + i];
            }
            for(int i = 1; i < 4; i++){
                points[7*row + i + 3] = backPatch[4*row + i];
            }
        }

        for (int row = 4; row < 7; row++){
            for(int i = 0; i < 4; i++){
                points[7*row + i] = rightPatch[4*(row - 3) + i];
            }
            for(int i = 1; i < 4; i++){
                points[7*row + i + 3] = cornerPatch[4*(row - 3) + i];
            }
        }

        double[][] patchPts = new double[16][3];
        RiTransformBegin();
        //RiScale(50.0, 35.0, 50.0);
        for(int numHills = 0; numHills < 4; numHills++) {
            RiRotate(-90.0, 0.0, 1.0, 0.0);
            //RiPatchMesh("bicubic", 5, "nonperiodic", 5, "nonperiodic", "P", meshPoints);
            //RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);

            for (double[][] patch : patches){
                RiPatch("bicubic", "P", patch);
            }
            for (int i = 0; i < 16; i++) {
                patchPts[i][0] = backPatch[i][0] + this.scaleX*3.0;
                patchPts[i][1] = 2.0 * backPatch[i][1];
                patchPts[i][2] = 2.0 * backPatch[i][2];
            }
            RiPatch("bicubic", "P", patchPts);
            for (int i = 0; i < 16; i++) {
                patchPts[i][0] = 2.0 * rightPatch[i][0];
                patchPts[i][1] = 2.0 * rightPatch[i][1];
                patchPts[i][2] = rightPatch[i][2] - this.scaleZ * 3.0;
            }
            RiPatch("bicubic", "P", patchPts);
            for (int i = 0; i < 16; i++) {
                patchPts[i][0] = cornerPatch[i][0] + this.scaleX* 3.0;
                patchPts[i][1] = 2.0 * cornerPatch[i][1];
                patchPts[i][2] = cornerPatch[i][2] - this.scaleZ* 3.0;
            }
            RiPatch("bicubic", "P", patchPts);

            RiPolygon(4, RI_P, baseVertices, RI_NULL);
        }
        RiTransformEnd();

    }


}