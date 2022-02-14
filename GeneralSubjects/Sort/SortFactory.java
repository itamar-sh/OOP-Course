package GeneralSubjects.Sort;


import java.util.ArrayList;
import java.util.Comparator;

public class SortFactory{
    public static Comparator<Money> factory(String period){
        switch(period){
            case "VALUE": return (a,b)->(a.value() - b.value());
            case "ALPHABETIC": return (a,b)->(a.getSerials().compareTo(b.getSerials()));
            case "ALPHABETIC_LENGTH": return (a,b)->(a.getSerials().length() - b.getSerials().length());
            default: return null;
        }
    }

    public static ArrayList<Money> sort(ArrayList<Money> arr, String Period){
        arr.sort(SortFactory.factory(Period));
        return arr;
    }
}

class Money {
    private int value;
    private String serials;
    public Money(String serials, int value) {
        this.value = value;
        this.serials = serials;
    }
    public String getSerials(){
        return serials;
    }
    public int value(){
        return value;
    }
}


