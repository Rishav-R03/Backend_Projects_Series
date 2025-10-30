public class SmartPhone implements AudioPlayer,Camera{
    public final String deviceName = "SmartPhone";

    @Override
    public void playSound(String trackName){
        System.out.println("Playing audio track "+trackName);
    }

    @Override
    public void takePhoto(){
        System.out.println("Photo captured successfully");
    }
    @Override
    public void viewGallery(){
        System.out.println("Accessing "+deviceName+" gallery");
    }

    public void call(String number){
        System.out.println("Calling "+number+"...");
    }
}
