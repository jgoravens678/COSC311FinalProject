import java.util.*;

public class Experiment extends Ri {


public static void main 
               (String args[]) { 
 
new Experiment().main();
}



double[] White = {1.0,1.0,1.0};
double[] Blue = {0.0,0.0,1.0};
double[] Red = {1.0,0.0,0.0};
double[] Grey = {0.3,0.3,0.3};
double[] Black = {0.0, 0.0, 0.0};
double[] Yellow = {0.45, 1.0, 0.0};
double[] Pink = {1.0, 0.3, 0.4};
double epsilon = 0.0;
boolean increasing = true;

  public static double[] unit[] = {
          {-40.0, -40.0, 0.0},
          {-40.0,  40.0, 0.0},
          { 40.0,  40.0, 0.0},
          { 40.0, -40.0, 0.0}};

  void makeWand(double radius, double length){
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
    double umin = 0, umax = 20, vmin = 0, vmax = 3;


    double ninety = 90.0;
    double ang = ninety;
    //for(double ang = ninety; ang <= 2 * Math.PI; ang += ninety) {
    RiTransformBegin();

    RiTranslate(0.0, -60.0, 60.0);
    //RiRotate(-15.0, 0.0, 0.0, 1.0);
    RiRotate(ang + 3.0 * ninety, 0.0, 1.0, 0.0);
    RiRotate(15.0, 0.0, 0.0, 1.0);
    RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
    //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);

    RiRotate(-15.0, 0.0, 0.0, 1.0);
    RiRotate(ang +2.0*ninety, 0.0, 1.0, 0.0);
    RiRotate(15.0, 0.0, 0.0, 1.0);
    RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
    //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);

    RiRotate(-15.0, 0.0, 0.0, 1.0);
    RiRotate(ang + ninety, 0.0, 1.0, 0.0);
    RiRotate(15.0, 0.0, 0.0, 1.0);
    RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
    //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);





    RiRotate(-15.0, 0.0, 0.0, 1.0);
    RiRotate(ang, 0.0, 1.0, 0.0);
    RiRotate(15.0, 0.0, 0.0, 1.0);
    RiNuPatch(nu, uorder, uknot, umin, umax, nv, vorder, vknot, vmin, vmax, "P", points);
    //RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);
    RiTransformEnd();
    //}

         /*
        RiTransformBegin();
        RiTranslate(0.0, -50.0, 40.0);
        RiRotate(-90.0, 0.0, 1.0, 0.0);
        //RiNuPatch(nv, vorder, vknot, vmin, vmax, nu, uorder, uknot, umin, umax, "P", points);
        RiPatchMesh("bicubic", 4, "nonperiodic", 11, "nonperiodic", "P", points);
        RiTransformEnd();

          */
  }

  private void LightSource(String string, String string2, double[] lightSourcel, String string3, double intens,
                           Object riNull) {

    RiLight("PxrSphereLight","light3",  "float intensity",4000,RI_NULL);
  }

  void makeLeaf(){
    double maxLength = 10.0;
    double[] mainSpline[] = {
            {0.0, 0.0, 0.0},
            {0.0, 0.3, 0.4},
            {0.0, 0.7, 0.4},
            {0.0, 1.0, 0.8}
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
    //points[15][2] = 0.0;

    double[] negPoints[] = new double[16][3];
    for (int i= 0; i < 16; i++){
      negPoints[i] = new double[]{-points[i][0], points[i][1], points[i][2]};
    }
    double[] one = {0.0, 0.0, 0.0};
    double[] two = {-maxLength / 2.0, 0.0, 0.0};
    double theta = 360.0;

    RiTransformBegin();
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
    RiCurves("cubic", 1, new int[]{4}, "nonperiodic", "P", new double[][]{{0.0, 0.0, 0.0}, {-maxLength / 3.0, 2.0, -2.0}, {-2.0*maxLength / 3.0, -2.0, 2.0}, {-maxLength, 0.0, 0.0}}, "constantwidth", 0.3);
    RiTransformEnd();

  }



void main ()

{

  int frameCount = 1;

  RiBegin(RI_NULL);

  // here we set options that will hold for all frames

  RiFormat(800,600,1.0);

  RiProjection("perspective",RI_NULL);


  for (int frameNo=1; frameNo<frameCount+1; frameNo++) {

    String fileName;

    // writes the name of the file for the frame into 
    // fileName (String)

    if (frameNo<10)
      fileName = "frame00"+frameNo+".tiff";
    else if (frameNo<100) fileName = "frame0"+frameNo+".tiff";
    else fileName = "frame"+frameNo+".tiff";


    // set the display as a file for the frame

    RiDisplay("frame0028.tiff","file","rgba",RI_NULL);
    
    // start the frame

    RiFrameBegin(frameNo-1);
    
    // If you give RiWorldBegin a parameter, it is used to set the intensity of the default
    // light source.  Without a parameter, it is set to 0.1 (Note: the default light source
    // is something I built in to the Amherst system
    
    RiWorldBegin(1.0);

     
    // move all whole world away from the origin

    RiTranslate(0.0,0.0,1.0);


    RiAttributeBegin();
    RiTransformBegin();
    double [] lightSourcel	= {0.0, 12.0, 0.2};
    double [] lightSource2	= {0.0,	8.0, 0.2};
    double [] lightSource3	= {0.0,	4.0, 0.2};

    double intens = 30.0;


    //RiCoordinateSystem("shaderDefine");
    //RiAttribute("displacementbound", "sphere", 2.4, RI_NULL);
    //RiPattern("cobbleShader", "cobbleShader", "float numStones", 120, "float stoneProportion", 0.85, "float displaceAmount", 2.0);
    //RiBxdf("PxrSurface", "checkerShader", "color diffuseColor", "");
    //RiBxdf("PxrSurface","checkerShader","color diffuseColor", "checkerShader:Cout", "color transmissionColor", "jerShader:Cout");

    //RiDisplace("PxrDisplace","displacer","reference vector dispVector","cobbleShader:changeV",RI_NULL);
    /*
    RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "cobbleShader:Cout",
            "reference float diffuseRoughness", "cobbleShader:roughness",
            "reference float fuzzGain", "cobbleShader:fuzz", "reference color fuzzColor", "cobbleShader:Cout",
            "reference normal bumpNormal", "cobbleShader:NN", RI_NULL);

     */
    RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",Pink);
    //
    //RiRotate(-45.0, 0.0, 1.0, 0.0);
    RiTranslate(0.0, -80.0, 80.0);
    //RiRotate(55.0, 0.0, 1.0, 0.0);
    //RiRotate(-30.0, 1.0, 1.0, 0.0);
    new Vine().makeVine(3, new double[][]{{0.0, 0.0},{0.0, 0.0}, {0.0, 0.0}}, new int[]{5, 4, 3}, new double[] {0.0, 60.0, -45.0}, 4, new double[]{0.7, 0.2, 0.1} );
    //new PianoKeys().makeBlackKey();
    //RiRotate(-70.0, 1.0, 1.0, 0.0);
    //RiScale(20.0, 20.0, 20.0);
    //new Ground().makeGround();
    //RiPolygon(4, RI_P, unit, RI_NULL);
    //new MusicNotes().makeBassClef();
    //makeLeaf();
    //new LightWand().makeLightWand(5.0, 80.0, 20.0);
    //new LightWand().makeWand(5.0, 100.0);
    //new LightWand().makeBulb(5.0, 80.0);
    //RiTranslate(0.0, 20.0, 0.0);
    //new LightWand().makeLight(4.0, 25.0);
    //new Piano().makeSquareTrim(40.0, 20.0, 5.0);
    //new Piano().makeTopper(0.6);
    //new Piano().makePiano();
    //RiRotate(-90.0, 0.0, 1.0, 0.0);
    //new Piano().makeBaseboard(35.0, 30.0, 40.0, 35.0, 5.0);

    //new Piano().makePianoLeg(1.2, 1.0, 0.8);
    //new Piano().makePianoLeg(1.1, 1.0, 0.9);
    //new Piano().makePianoLeg(1.0, 1.0, 1.0);


    //RiTranslate(30.0, 0.0, 40.0);

    //RiRotate(45.0, 0.0, 1.0, 0.0);
    //new Piano().makeConcaveCylinder(20.0, 15.0);
    //new Piano().makeConcaveCylinder(25.0, 15.0);
    //new Piano().makeConcaveCylinder(30.0, 15.0);

    //RiRotate(90.0, 1.0, 0.0, 0.0);
    //new Piano().makeGenericTrim(40.0, 8.0);
    //RiRotate(-90.0, 0,1,0);
    //double[] arr = new double[] {6.0, 12.0, 12.0};
    //new Piano().makeSideMolds(arr);
    //new Piano().makeBaseboardMold(35, 30, 40, 35, 5);
    //RiTranslate(40.0, 0.0, 0.0);
    //new Piano().makeSpikyCube(30.0);
    //RiTranslate(0.0, 0.0, -20.0);
    //RiTranslate(-0.0, 0.0, -10.0);
    //RiTranslate(30.0, -10.0, -40.0);
    //LightSource("pointlight","from",lightSourcel,
            //"intensity",intens,RI_NULL);
    RiTransformEnd();
    RiAttributeEnd();






    // a backdrop

    double[] Unit[] = {
            {-1.0, -1.0, 0.0},
            {-1.0,  1.0, 0.0},
            { 1.0,  1.0, 0.0},
            { 1.0, -1.0, 0.0}};

    double[] Grey = {0.5,0.5,0.5};
	RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor", Grey);

    RiTranslate(0.0,0.0,500.0);
    RiScale(1000.0,1000.0,1.0);
    RiPolygon(4, RI_P, Unit, RI_NULL);


    // render frame, destroy lighting, objects and state created in the world,
    // restore start to that before the world begin

    RiWorldEnd();


    RiFrameEnd();

  }  


  RiEnd();


}




}

