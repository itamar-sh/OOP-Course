package GeneralSubjects.Equals_Hashcode_CompareTo;

/*
every override of equals has to be:
1) reflexively
2) transitively
so if a==a ,we get true and if a==b and b==c than a==c
*/
public class Equals {
    int x;
    int y;
    String name;
    public Equals(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public boolean equals(Equals other) {
        return (x==other.x && y==other.y && name.equals(other.name)); // with string we have to use "equals"
    }

    public static void main(String[] args) {
        Equals a = new Equals(1,2,"a");
        Equals b = new Equals(1,2,"b");
        Equals c = new Equals(2,2,"a");
        Equals d = new Equals(2,2,"a");
        System.out.println(a==b); // false - check if the same object and its not!
        System.out.println(a==c); // false - check if the same object and its not!
        System.out.println(d==c); // false - check if the same object and its not!
        System.out.println(a.equals(b)); // false - name different
        System.out.println(a.equals(c)); // false - x different
        System.out.println(c.equals(d)); // true - not the same object but the same values!
    }
}
