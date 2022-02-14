import java.util.Random;

public class Q1 {
    public static void main(String[] args) {

    }
}

interface Weapon{
    int fire();
    int quality();
    void printQuality();
}

abstract class Ship<W extends Weapon> implements Comparable<Ship<W>>{
    protected W weapon;
    protected int hitPoints = 100;
    abstract void attack(Ship<? extends Weapon> ship);
    abstract void gotHit(Weapon w);
    public int compareTo(Ship<W> other){
        return Integer.compare(weapon.quality(), other.weapon.quality());
    }
}

class SimpleShip<W extends Weapon> extends Ship<W>{
    public void attack(Ship<?> ship){
        ship.gotHit(this.weapon);
    }
    public void gotHit(Weapon w){
        this.hitPoints = Math.max(this.hitPoints-w.fire() ,0);
    }
}

class ProtectedShip<W extends Weapon> extends Ship<W>{
    public void attack(Ship<?> ship){
        ship.gotHit(this.weapon);
    }
    public void gotHit(Weapon w){
        Random rnd = new Random();
        this.hitPoints = Math.max(this.hitPoints-w.fire() -rnd.nextInt(25) -1,0);
    }
}

class HiddenShip<W extends Weapon> extends Ship<W>{
    public void attack(Ship<?> ship){
        ship.gotHit(this.weapon);
    }
    public void gotHit(Weapon w){
        Random rnd = new Random();
        if(rnd.nextInt(4) != 0){
            this.hitPoints = Math.max(this.hitPoints-w.fire() -rnd.nextInt(25) -1,0);
        }
    }
}