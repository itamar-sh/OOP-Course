package DesignPatterns.Memento;

import java.util.ArrayList;
import java.util.List;

public class SimpleExample {
}
class Memento<T>{
    private T state;
    public Memento(T state){
        this.state = state;
    }
    public T getState(){
        return state;
    }
}

class Originator<T>{
    private T state;
    public void setState(T state){this.state = state;}
    public T getState(){
        return state;
    }
    public Memento<T> saveStateToMemento(){
        return new Memento<T>(state);
    }
    public void getStateFromMemento(Memento<T> m){
        state = m.getState();
    }
}

class CareTaker<T>{
    private List<Memento<? extends T>> memList = new ArrayList<>();
    public void add(Memento<T> m){
        memList.add(m);
    }
    public Memento<? extends T> get(int index){
        return memList.get(index);
    }
}