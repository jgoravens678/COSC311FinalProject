import java.util.*;
//============================
/*
This is the masterclass that produces the image.
 */
//============================
public class Scene extends Ri{
    public static void main(String[] args){
        new Scene().produceScene();
    }

    void produceScene(){
        int frameCount = 100;

        RiBegin(RI_NULL);

        // here we set options that will hold for all frames

        RiFormat(800,600,1.0);

        RiProjection("perspective",RI_NULL);




        String fileName = "frame8.tiff";

        // writes the name of the file for the frame into
        // fileName (String)




        // set the display as a file for the frame
        RiDisplay(fileName,"file","rgba",RI_NULL);

        // start the frame

        RiFrameBegin(1);

        // If you give RiWorldBegin a parameter, it is used to set the intensity of the default
        // light source.  Without a parameter, it is set to 0.1 (Note: the default light source
        // is something I built in to the Amherst system

        RiWorldBegin(1.0);

        //RiRotate(-35.0, 0.0, 1.0, 0.0);
        RiTranslate(0.0, -50.0, 0.0);
        RiRotate(-30.0, 0.0, 1.0, 0.0);

        // move all whole world away from the origin

        //RiTranslate(0.0,0.0,1.0);

        //List of objects to reference
        Piano piano = new Piano();
        Ground ground = new Ground();
        MusicNotes notes = new MusicNotes();
        LightWand lights = new LightWand();
        //Vortex vortex = new Vortex();
        Random rand = new Random();

        //First, create the groundwork.
        double displacementAmt = 2.0;
        double zPushback = 40.0;

        RiAttributeBegin();
        RiTransformBegin();
        RiAttribute("displacementbound", "sphere", 3.0, RI_NULL);
        RiPattern("cobbleShader", "cobbleShader", "float numStones", 80, "float stoneProportion", 0.85, "float displaceAmount", 2.0);


        RiDisplace("PxrDisplace","displacer","reference vector dispVector","cobbleShader:changeV",RI_NULL);

        RiBxdf("PxrSurface","surface1", "int diffuseDoubleSided", 1, "reference color diffuseColor", "cobbleShader:Cout",
            "reference float diffuseRoughness", "cobbleShader:roughness",
            "reference float fuzzGain", "cobbleShader:fuzz", "reference color fuzzColor", "cobbleShader:Cout",
            "reference normal bumpNormal", "cobbleShader:NN", RI_NULL);

        RiTranslate(0.0, 0.0, 20.0);
        new Ground().makeGround(100.0, 70.0, 100.0);
        RiTransformEnd();
        RiAttributeEnd();
        //Now, initialize the vortex.
        double vortexRadius = 30.0;
        double vortexHeight = ground.dimensions[1] / 2.0;
        Vortex vortex = new Vortex(vortexRadius, vortexHeight, (ground.dimensions[0]/3.0) - vortexRadius, 0.0, 20.0);
        RiTransformBegin();
        vortex.makeVortex();
        RiTransformEnd();

        //sphere light
        RiTransformBegin();
        RiTranslate(60.0, 20.0, -40.0);
        RiLight("PxrSphereLight", "sphereLight", "float intensity", 1000, RI_NULL);
        RiTransformEnd();

        //Create the piano.
        RiAttributeBegin();
        RiTranslate(0.0,piano.PianoDimensions[1] / 2.0 + displacementAmt ,80.0 + 20.0);
        RiRotate(-30.0, 0.0, 1.0, 0.0);
        piano.makePiano();
        RiAttributeEnd();



        //Create the scattered music notes.
        //First, generate the points.
        int numNotes = 2000;
        double[] noteCenters[] = new double[numNotes][3];
        for (int note = 0; note < numNotes; note++){
            noteCenters[note][0] = 2.0 * (ground.dimensions[0] / 3.0) * rand.nextDouble() - ground.dimensions[0] / 3.0;
            noteCenters[note][1] = (ground.dimensions[1] / 2.0) * rand.nextDouble();
            noteCenters[note][2] = 2.0*(ground.dimensions[2] / 3.0) * rand.nextDouble() - ground.dimensions[2] / 3.0;
        }

        //Draw the notes.
        for (double[] noteCenter : noteCenters){
            RiTransformBegin();
            //RiScale(15.0, 15.0, 15.0);
            RiTranslate(noteCenter[0], noteCenter[1], noteCenter[2] +20.0);
            RiScale(2.0, 2.0, 2.0);
            RiRotate(Math.atan(noteCenter[2] / noteCenter[0]), 0.0, 1.0, 0.0);
            notes.makeRandomNote(true);
            RiTransformEnd();
        }


        //Scatter lights along the ground.
        int numLights = 800;
        double lightRadius = 0.75;
        double lightHeight = 16.0;
        double maxIntensity = 100.0;
        double[] lightLocations[] = new double[numLights][3];
        double[] intensities = new double[numLights];
        double[] rotationAngle = new double[numLights];

        for (double[] lightLoc : lightLocations){
            lightLoc[0] = 2.0 * (ground.dimensions[0] / 3.0) * rand.nextDouble() - ground.dimensions[0] / 3.0;
            lightLoc[1] = displacementAmt + lightRadius;
            lightLoc[2] = 2.0 * (ground.dimensions[2] / 3.0) * rand.nextDouble() - ground.dimensions[2] / 3.0;
        }

        for (int i = 0; i < numLights; i++){
            intensities[i] = maxIntensity * rand.nextDouble();
            rotationAngle[i] = 360.0 * rand.nextDouble();
        }

        for (int i = 0; i < numLights; i++){
            RiTransformBegin();
            RiTranslate(lightLocations[i][0], lightLocations[i][1], lightLocations[i][2] + 20.0);
            RiRotate(rotationAngle[i], 0.0, 1.0, 0.0);
            lights.makeLightWand(lightRadius, lightHeight, intensities[i]);
            RiTransformEnd();
        }




        // a backdrop
        double[] Unit[] = {
                {-1.0, -1.0, 0.0},
                {-1.0,  1.0, 0.0},
                { 1.0,  1.0, 0.0},
                { 1.0, -1.0, 0.0}};

        double[] Grey = {0.5,0.5,0.5};
        RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor", Grey);
        //RiTranslate(0.0,0.0,10.0);
        RiScale(100.0,100.0,1.0);
        RiPolygon(4, RI_P, Unit, RI_NULL);


        // render frame, destroy lighting, objects and state created in the world,
        // restore start to that before the world begin

        RiWorldEnd();
        RiFrameEnd();

        RiEnd();
    }
}