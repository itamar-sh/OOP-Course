package Exams.Exam2019B.Q1;

import java.util.ArrayList;

public class Squadron<W extends Weapon, A extends Armour> extends ArrayList<Ship<W,A>> implements Attackable{
    private int maxLength;
    public Squadron(int maxLength){
        this.maxLength = maxLength;
    }

    @Override
    public int getHit(Weapon weapon) {
        return 0;
    }

}
