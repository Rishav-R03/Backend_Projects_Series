public abstract class AbstractShape {
    // field
    protected String color;
    // constructor
    public AbstractShape(String color){
        this.color = color;
        System.out.println("Shape created with color");
    }
    // abstract method
    public abstract double calArea();

    public void printInfo(){
        System.out.println("This is a " + color +" shape.");
    }
}
