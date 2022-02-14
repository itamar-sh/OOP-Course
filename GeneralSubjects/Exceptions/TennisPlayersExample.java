package GeneralSubjects.Exceptions;

public class TennisPlayersExample {
}

class TennisPlayException extends Exception{
    public void throwMethod () throws TennisPlayException{
        throw this;
    }

    public void throwing () throws TennisPlayException{
        try{
            this.throwMethod();
        }catch(DoubleFaultException | NetCallException e){
            throw e;
        }catch(TennisPlayException e){
            System.out.print("unrecognized exception");
            throw e;
        }
    }
}

class NetCallException extends TennisPlayException{

}

class DoubleFaultException extends TennisPlayException{

}
