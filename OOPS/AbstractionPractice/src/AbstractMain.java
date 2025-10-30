


public class AbstractMain {
    public static void main(String [] args){
        Circle myCircle = new Circle(10.0,"Blue");
        System.out.println("\n---AbstractShape Usage (Inheritance) ---");
        myCircle.printInfo();
        System.out.printf("Area is: %.2f\n",myCircle.calArea());
        System.out.println("--- Drawable usage (Capability) ---");
        System.out.println("Border thickness: "+Drawable.BORDER_THICKNESS);
        myCircle.draw();
        myCircle.resize();

        // Rectangle
        Rectangle myRectangle = new Rectangle(10.0,9.0,"Violet");
        myRectangle.displayShape();
        System.out.println("The area of Rectangle is: "+myRectangle.calArea());
        myRectangle.printInfo();
        myRectangle.resize();
    }
}
