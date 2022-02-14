package Exams.Exam2021A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table<S, O, V>{
    private static class OutOfBoundariesException extends Exception{ }
    private ArrayList<S> subjects;
    private ArrayList<O> options;
    private ArrayList<ArrayList<V>> values;
    private HashMap<Integer, HashMap<Integer, V>> values2;

    public S getSubject(int index){
        return subjects.get(index);
    }
    public O getOption(int index){
        return options.get(index);
    }
    public V getValue(int row, int col){
        return values.get(row).get(col);
    }
    public void setSubject(S s, int index){
        subjects.set(index, s);
    }
    public void setOptions(O o, int index){
        options.add(index, o);
    }
    public void setSubject(V v, int row, int col){
        values.get(row).add(col, v);
    }
    public void setAllSubjects(ArrayList<S> arr) throws OutOfBoundariesException{
        if(arr.size() != options.size()){
            throw new OutOfBoundariesException();
        }
        subjects = new ArrayList<>(arr);
    }
    public void setAllOptions(ArrayList<O> arr) throws OutOfBoundariesException{
        if(arr.size() != options.size()){
            throw new OutOfBoundariesException();
        }
        this.options = new ArrayList<>(arr);
    }
    public void replaceValue(V v1, V v2){
        for(int i = 0; i < values.size();i++){
            for(int j=0;j< values.get(i).size();j++){
                if(values.get(i).get(j).equals(v1)){
                    values.get(i).set(j, v2);
                }
            }
        }
    }
    public void replaceValue2(V v1, V v2){
        for(HashMap<Integer, V> map: values2.values()){
            for(Map.Entry<Integer,V> entry: map.entrySet()){
                if(entry.getValue().equals(v1)){
                    entry.setValue(v2);
                }
            }
        }

    }
    public static <S extends Comparable<S>, O, V> int compareTables(Table<S,O,V> t1, Table<S,O,V> t2){
        return t1.subjects.get(0).compareTo(t2.subjects.get(0));
    }
}
class Main5{
    public static boolean func(ArrayList<String> arr, String s){
        for(String temp: arr){
            if(temp.length() != s.length()){
                continue;
            }
            temp = String.join(temp, temp);
            if(temp.contains(s)){
                return true;
            }
        }
        return false;
    }
}