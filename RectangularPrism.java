public class RectangularPrism extends Ri{
    private double LENGTH;
    private double WIDTH;
    private double HEIGHT;

    public RectangularPrism(){

    }

    public RectangularPrism(double length, double width, double height){
        this.LENGTH =  length;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public RectangularPrism(double length, double width, double height, double dx, double dy, double dz){
        this(length, width, height);
    }

    void draw(){
        RiTransformBegin();
        createPrism(LENGTH, WIDTH, HEIGHT);
        RiTransformEnd();
    }

    //Create a rectangle with the given parameters, centered at the origin.
    void rectangle(double width, double height){
        double x_min = -width / 2.0;
        double x_max = width / 2.0;
        double y_min = -height/2.0;
        double y_max = height/2.0;

        //vertices for rectangle in xy-plane
        double[] vertices[] = {
                {x_min, y_min, 0.0},
                {x_min, y_max, 0.0},
                {x_max, y_max, 0.0},
                {x_max, y_min, 0.0}
        };

        RiTransformBegin();
        RiPolygon(4, RI_P, vertices, RI_NULL);
        RiTransformEnd();
    }

    //Create a rectangular prism with the given parameters, centered at the origin.
    /*
    Variable Names Correspondence:
    length \mapsto depth
    width \mapsto width
    height \mapsto height
    z,x,y
     */
    void createPrism(double depth, double width, double height){
        double transAmount = depth / 2.0;
        //RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",White);

        RiTransformBegin();
        RiTranslate(0.0,0.0,-transAmount);
        rectangle(width, height);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(0.0,0.0, transAmount);
        rectangle(width, height);
        RiTransformEnd();

        transAmount = width / 2.0;
        //RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",Cyan);

        RiTransformBegin();
        RiTranslate(transAmount,0.0,0.0);
        RiRotate(90.0,0.0,1.0,0.0);
        rectangle(depth, height);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(-transAmount,0.0,0.0);
        RiRotate(90.0,0.0,1.0,0.0);
        rectangle(depth, height);
        RiTransformEnd();

        transAmount = height / 2.0;
        //RiBxdf("PxrSurface","surface1","int diffuseDoubleSided",1,"color diffuseColor",White);

        RiTransformBegin();
        RiTranslate(0.0,-transAmount,0.0);
        RiRotate(90.0,1.0,0.0,0.0);
        rectangle(width, depth);
        RiTransformEnd();

        RiTransformBegin();
        RiTranslate(0.0,transAmount,0.0);
        RiRotate(90.0,1.0,0.0,0.0);
        rectangle(width, depth);
        RiTransformEnd();

    }

}