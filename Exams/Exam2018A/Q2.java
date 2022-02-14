package Exams.Exam2018A;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class Q2 {
    public static void main(String[] args) {
        Q2.manageGame(args);
    }

    public static void manageGame(String[] playersStream){
        // init players
        ArrayList<Player> players = new ArrayList<>();
        for (String s : playersStream) {
            players.add(Q2.factoryPlayers(s));
        }

        // fight
        int[] winners = new int[players.size()];
        int maxSize = players.size();
        while(maxSize != 1){
            for (int i = 0; i < maxSize; i += 2) {
                winners[i/2] = i/2 + Q2.battle(players.get(i), players.get(i+1));
            }
            maxSize = maxSize/2;
        }
        System.out.println("player " + players.get(winners[0]) +  " has won!\nthe " + winners[0]+ " in the list");
    }
    public static Player factoryPlayers(String s){
        switch (s){
            case "S":
                return new Student();
            case "TA":
                return new TA();
            case "T":
                return new Teacher();
            default:
                return new Student();
        }
    }

    public static int battle(Player a, Player b){
        return a.power() >= b.power() ? 1 : 0;
    }
}

interface Player{
    int power();
}

class RND{
    static int numberBetween(int num){
        Random rnd = new Random();
        return rnd.nextInt(num);
    }
}

class Student implements Player{
    protected int skill;
    protected int study;
    private int fatigue;
    public Student(){
        this.skill = RND.numberBetween(10)+1;
        this.study = RND.numberBetween(10)+1;
        this.fatigue = RND.numberBetween(10)+1;
    }
    public int power(){
        return RND.numberBetween(this.skill * this.study) - RND.numberBetween(this.fatigue);
    }
}

class TA extends Student{
    protected int xp;
    public TA(){
        this.xp = RND.numberBetween(10)+1;
    }

    @Override
    public int power() {
        return RND.numberBetween((this.skill + this.study) * this.xp) + 1;
    }
}

class Teacher extends TA{
    protected int prestigue;
    public Teacher(){
        this.prestigue = RND.numberBetween(10) + 1;
    }

    @Override
    public int power() {
        return RND.numberBetween((this.skill*7 + this.study)) + this.xp + this.prestigue + 1;
    }
}
