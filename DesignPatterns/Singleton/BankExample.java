package DesignPatterns.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public final class BankExample {
    private static BankExample instance = null;
    private BankExample(){

    }
    public static BankExample getInstance(){
        if(instance==null){
            instance = new BankExample();
        }
        return instance;
    }
}






