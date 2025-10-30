
public class Circle extends AbstractShape implements Drawable{
    private double rad;

    public Circle(double rad,String color){
        super(color);
        this.rad = rad;
    }
    @Override
    public double calArea(){
        return Math.PI * rad * rad;
    }
    @Override
    public void draw(){
        System.out.println("Drawing a "+color + " circle with radius "+rad);
    }
    @Override
    public void resize(){
        System.out.println("Resizing...Circle radius:"+rad);
    }

    public void displayRadius(){
        System.out.println("Circle Radius: "+rad);
    }
}
