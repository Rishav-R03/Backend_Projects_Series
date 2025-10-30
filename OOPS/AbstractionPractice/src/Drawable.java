public interface Drawable {
    int BORDER_THICKNESS = 1;

    void draw();

    // provide default implementation
    default void resize(){
        System.out.println("Resizing the drawable object.");
    }
}
