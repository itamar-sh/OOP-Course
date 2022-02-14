package Exams.Exam2019B.Q1;

public class Ship<W extends Weapon,A extends Armour> implements Attackable{
    W w;
    A a;
    private int health = 100;
    public Ship(W w, A a){
        this.w = w;
        this.a = a;
    }
    public int getHit(Weapon weapon){
        health -= (int) (weapon.getBaseDamage() - a.absorbDamage(weapon));
        return health;
    }

    public int attack(Attackable target){
        return target.getHit(w);
    }

    public void getFixed(int h){
        health += h;
        if(health > 100){
            health = 100;
        }
    }

}
