public class Main {
    public static void main(String []args){
        SmartPhone smartphone = new SmartPhone();
        String deviceName = smartphone.deviceName;
        smartphone.playSound("Bring me back");
        smartphone.call("9311571439");
        smartphone.takePhoto();
        smartphone.viewGallery();
    }
}
