package DesignPatterns.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class sortStrategyExample<T> {
    ArrayList<String> s;
    public void sort(Comparator<String> sorter){
        Collections.sort(s, sorter);
        // s.sort(sorter);
    }
}
