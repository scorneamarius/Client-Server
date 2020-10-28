import java.util.ArrayList;

public class Topic {

    private ArrayList<String> messages = new ArrayList<String>();
    private String type;
    private int timeToLeave; // in minutes

    public Topic(String type, int timeToLeave) {
        this.type = type;
        this.timeToLeave = timeToLeave;
    }

    public String getType() {
        return this.type;
    }

    public int getTimeToLeave() {
        return this.timeToLeave;
    }

    public ArrayList<String> getMessages()
    {
        if(messages.size()==0)
            return null;
        return this.messages;
    }
    public void addMessage(String message)
    {
        this.messages.add(message);
    }
    public void deleteMessage(String message)
    {
        this.messages.remove(message);
    }

}
