public interface Camera {
    void takePhoto();
    default void viewGallery(){
        System.out.println("Accessing the digital gallery");
    }
}
