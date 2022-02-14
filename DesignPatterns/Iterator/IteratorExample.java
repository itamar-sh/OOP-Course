package DesignPatterns.Iterator;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorExample implements Iterator<IteratorExample> {
    ArrayList<IteratorExample> arr = new ArrayList<>();
    int index = 0;
    @Override
    public boolean hasNext(){
        return index < arr.size();
    }
    @Override
    public IteratorExample next(){
        inc();
        return arr.get(index-1);
    }
    private void inc(){
        index++;
        if(index >= arr.size()){
            index = 0;
        }
    }

    public static void main(String[] args) {
        ArrayList<String> s = new ArrayList<>();
        Iterator<String> iter = s.iterator();
    }
}
