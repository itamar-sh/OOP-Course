package DesignPrinciples;

public class Encapsulation {
}

// we don't care how the car is driving or how the driver go to shopping, its hidden and separate to areas.
class Car{
    private int numOfWheels;
    private String EngineName;
    private int sumOfFuel;
    private int sumOfWater;
    private int speed;
    private boolean carIsGood(){
        return numOfWheels == 4 && EngineName.equals("GoodEnine") && sumOfFuel > 10 && sumOfWater > 10;
    }

    public void speedUp(){
        if(carIsGood()){
            speed = 80;
        }
    }

    public void stop(){
        speed = 0;
    }
}

class Driver{
    private Car car;
    public void goToShoping() throws InterruptedException {
        car.speedUp();
        System.out.wait(100);
        car.stop();
    }
}