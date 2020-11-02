public class Message {

    private String message;
    private String receiver;
    private String transmitter;

    public Message(String message , String receiver , String transmitter)
    {
        this.message=message;
        this.receiver=receiver;
        this.transmitter=transmitter;
    }

    public String getMessage(){return this.message;}
    public String getReceiver(){return this.receiver;}
    public String getTransmitter(){return this.transmitter;}

    public String displayMessageForServer()
    {
        return this.transmitter+": "+this.message+ " -> " +this.receiver;
    }
    public String displayMessageForClientSlave()
    {
        return this.transmitter+": "+this.message;
    }

}

