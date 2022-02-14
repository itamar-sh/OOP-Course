package GeneralSubjects.AnonimusClassses;

public class InsideMethod {
}

class IpPacket implements IpHeader {
    private int destinationId;
    private int sourceId;
    protected IpPacket(IpHeader packet){
        this.destinationId = packet.getDestinationId();
        this.sourceId = packet.getSourceId();
    }
    public IpPacket(int sourceId, int destinationId){
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }
    public int getDestinationId(){
        return this.destinationId;
    }
    public int getSourceId(){
        return this.sourceId;
    }
    @Override
    public IpPacket encapsulate(final int destination){  // AnonimusClass
        return new IpPacket(this){
            @Override
            public int getDestinationId(){
                return destination;
            }
            @Override
            public IpPacket deencapsulate() throws NotEncapsulatedException{
                return IpPacket.this;
            }
        };
    }
    @Override
    public IpPacket deencapsulate() throws NotEncapsulatedException{
        throw new NotEncapsulatedException();
    }

}
interface Encapsulatable{
    IpHeader encapsulate(int newDestId);
    IpHeader deencapsulate() throws NotEncapsulatedException;
}
interface IpHeader extends Encapsulatable {
    int getDestinationId();
    int getSourceId();
}
class NotEncapsulatedException extends Exception {
}