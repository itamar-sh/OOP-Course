package GeneralSubjects.Equals_Hashcode_CompareTo;

public class OverrideForHashMap {

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

    @Override
    public boolean equals(Object other){
        Money otherMoney = (Money) other;
        return otherMoney.getSerials().equals(this.getSerials());
    }
    @Override
    public int hashCode(){
        return serials.hashCode();
    }
}