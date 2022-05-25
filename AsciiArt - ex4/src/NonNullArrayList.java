import java.util.ArrayList;

public class NonNullArrayList extends ArrayList {
    @Override
    public boolean add(Object e) {
        if (e == null) return false;
        else return super.add(e);
    }

}
