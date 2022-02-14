package DesignPatterns.Observer;

import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleExample {
}

class MessagesApp implements Observed{
    ArrayList<Observer> observers;
    String s;
    @Override
    public void updateAll() {
        for(Observer o: observers){
            o.update(s);
        }
    }
}

class ObserverPhone implements Observer{
    ArrayList<String> contacts;

    @Override
    public void update(String s) {
        contacts.add(s);
    }
}

class ObserverComputer implements Observer{
    LinkedList<String> contacts;

    @Override
    public void update(String s) {
        contacts.add(s);
    }
}

interface Observer{
    void update(String s);
}
interface Observed{
    void updateAll();
}
