abstract class Shape{
    public final double pi = 3.14; 
    public abstract void draw();
    public double area(){
        return 1.0;
    }
}

class Circle extends Shape{
    @Override
    public void draw(){
        System.out.println("Drawing circle...");
    }
    public double area(int r){
        return pi*r*r;
    }
}

class Rectangle extends Shape{
    @Override
    public void draw(){
        System.out.println("Drawing rectangle...");
    }

    public double area(int l, int r){
        return (double) l * r;
    }
}

class Triangle extends Shape{
    @Override 
    public void draw(){
        System.out.println("Drawing triangle...");
    }
    public double area(int b, int h){
        return (double) 0.5*b*h;
    }
}

public class ShapesApp {
    public static void main(String [] args){
        Circle circle = new Circle();
        circle.draw();
        double cir = circle.area(4);
        System.out.println("Circle area: "+cir);
        Rectangle rectangle = new Rectangle();
        rectangle.draw();
        double rec = rectangle.area(2,4);
        System.out.println("Rectangle area: "+rec);
        Triangle triangle = new Triangle();
        triangle.draw();
        double tri = triangle.area(2,4);
        System.out.println("Triangle area: "+tri);
    }
}
