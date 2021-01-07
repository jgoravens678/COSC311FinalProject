import java.util.*;
//=============================
/*
* Generate a toroidal vortex of points.
 */
//=============================
public class Vortex extends Ri{
    private double[] center = {0.0, 0.0, 0.0};
    private double radius = 1.0;
    private double height = 4.0;
    private int numPts = 10000;
    private double ptWidth = 0.25;

    double[] PALE_GOLD = {0.94, 0.82, 0.37};
    double[] GOLD = {0.906, 0.652, 0.016};

    double[] LIGHT_GRAY = {0.8, 0.8, 0.8};

    public Vortex(double r, double h, double x, double y, double z){
        setRadius(r);
        setHeight(h);
        setDimensions(x,y,z);
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    public void setDimensions(double x, double y, double z){
        this.center = new double[] {x,y,z};
    }

    public void setHeight(double height){
        this.height = height;
    }

    void makeVortex(){
        Random rand = new Random();
        double[] points[] = new double[numPts][3];
        double[] widths = new double[numPts];
        double param;
        double spiralRadius;
        double spiralHeight;
        double spiralAngle;
        double twoPi = Math.PI * 2.0;

        for (int i = 0; i < this.numPts; i++){
            param = rand.nextDouble();
            spiralRadius = this.radius * param;
            spiralHeight = this.height * (1.0 - param);
            spiralRadius = this.radius * (1.0 - (spiralHeight / this.height) + (param - 0.5));
            spiralAngle = (6.0 * param) * twoPi;

            points[i][0] = this.center[0] + spiralRadius * Math.cos(spiralAngle) - (spiralRadius / 2.0 * rand.nextDouble());
            points[i][1] = this.center[1] + spiralHeight;
            points[i][2] = this.center[2] + spiralRadius * Math.sin(spiralAngle) - (spiralRadius / 2.0 * rand.nextDouble());

            param = rand.nextDouble();
            widths[i] = param * 2.0 * ptWidth;
        }

        RiTransformBegin();
        RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",LIGHT_GRAY, "float glowGain", 0.5, "color glowColor", LIGHT_GRAY);
        RiPoints(this.numPts, RI_P, points, "width", widths, RI_NULL);
        RiTransformEnd();
    }
}