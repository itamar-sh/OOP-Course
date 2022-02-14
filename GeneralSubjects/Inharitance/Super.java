package GeneralSubjects.Inharitance;

public class Super {
    public static void main(String[] args) {
        Dog d = new Dog("d");
        System.out.println(d.color);  // black and white
        d.eat();  // eating bread
    }
}

class Animal{
    String color="white";
    void eat(){System.out.print("eating ");}
}
class Dog extends Animal {
    String color = "black and " + super.color;  // 1 - for using parent variables
    void eat(){
        super.eat(); // 2 - for using parent methods
        System.out.print("bread ");}
    public Dog(String s){
        super();  // 3 - using parent constructor
    }
}

class Pudel extends Dog{
    public Pudel(){
        super("pudel");  // without parent default constructor we need to call specific real constructor

    }
}



