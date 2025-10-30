interface Vehicle{
    String VEHICLE_TYPE = "Land Transport"; // constant
    // Abstract method (implicitly public abstract) - declares what vehicles must do
    void startEngine();
    // abstract method what vehicle must do
    void stopEngine();
    // abstract method with parameter
    void accelerate(int speedIncrease);
}
// implementing above interface
class Car implements Vehicle{
    private int currSpeed = 0;
    private boolean isRunning = false;

    @Override
    public void startEngine(){
        if(currSpeed == 0 && !isRunning){
            isRunning = true;
            System.out.println("Car engine started...");
        }else{
            System.out.println("Car is already running");
        }
    }

    @Override
    public void stopEngine(){
        if(isRunning) {
            isRunning = false;
            currSpeed = 0;
            System.out.println("Car engine stopped...");
        }else{
            System.out.println("Could not stop the car");
        }
    }

    @Override
    public void accelerate(int increaseSpeed){
        if(isRunning){
            currSpeed += increaseSpeed;
            System.out.println("Accelerating car " + currSpeed);
        }else{
            System.out.println("Car is stopped, kindly start the engine");
        }
    }

    public void honkHorn(){
        System.out.println("Beep beep");
    }
}
public class InterfaceTut{
    static void main(String [] args){
    Vehicle mycar = new Car();
    mycar.startEngine();
    mycar.accelerate(10);
    mycar.accelerate(30);
    mycar.stopEngine();
    mycar.accelerate(10);
    }
}
