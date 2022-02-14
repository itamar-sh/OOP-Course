package DesignPatterns.Delegation;

public class example {
}
class Delegated{
    public void delgateMe(){
    }
}
class Delegator{
    Delegated d;
    public void useMe(){
        d.delgateMe();
    }
}
