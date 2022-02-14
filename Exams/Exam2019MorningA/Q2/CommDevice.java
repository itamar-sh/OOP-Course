package Q2;

public class CommDevice {
    private CommDevice connectedDevice  = null;
    private String lastMessage;
    public boolean connect(CommDevice d){
        connectedDevice = d;
        return true;
    }
    public void removeConnection(){
        connectedDevice = null;
    }
    public boolean acceptConnection(CommDevice d){
        if (connectedDevice == null){
            connect(d);
            return true;
        }
        return false;
    }
    public void receiveMessage(String msg){
        lastMessage = msg;
    }
    public boolean sendMessage(String msg){
        if (connectedDevice != null){
            connectedDevice.receiveMessage(msg);
            return true;
        }
        return false;
    }
    public void printMessage(){
        System.out.println(lastMessage);
    }

}
