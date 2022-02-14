package GeneralSubjects.Enum;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum EnumComparator implements Comparator<Money>{
    VALUE((m1,m2) -> (m1.value() - m2.value())),
    ALPHABETIC((m1,m2) -> (m1.getSerials().compareTo(m2.getSerials()))),
    ALPHABETIC_LENGTH((m1,m2) -> (m1.getSerials().length() - m2.getSerials().length()));
    Comparator<Money> comparator;
    EnumComparator(Comparator<Money> comparator){
        this.comparator = comparator;
    }
    @Override
    public int compare(Money m1, Money m2){
        return comparator.compare(m1,m2);
    }

    public static List<Money> sort(List<Money> moneyList, String period){
        Collections.sort(moneyList, EnumComparator.valueOf(period));
        return moneyList;
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

