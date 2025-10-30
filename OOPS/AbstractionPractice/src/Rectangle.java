public class Rectangle extends AbstractShape implements Drawable{
    private double length;
    private double breadth;

    public Rectangle(double length,double breadth,String color){
        super(color);
        this.length = length;
        this.breadth = breadth;
    }

    @Override
    public double calArea(){
        return length*breadth;
    }
    @Override
    public void draw(){
        System.out.println("Drawing a Rectangle of length "+length + " and with breadth " +breadth);

    }
    @Override
    public void resize(){
        System.out.println("Drawing a Rectangle of length "+length + " and with breadth " +breadth);
    }
    public void displayShape(){
        System.out.println("This is shape is rectangle...");
    }
}
