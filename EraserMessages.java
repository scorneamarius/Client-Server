public class EraserMessages extends Thread {

    private Topic topic;
    private int time_to_leave;
    private String message;
    private Server server;

    public EraserMessages(Topic topic , int time_to_leave , String message,Server server)
    {
        this.topic=topic;
        this.time_to_leave=time_to_leave;
        this.message=message;
        this.server=server;
    }

    public void run()
    {
        try {
            Thread.sleep(1000*60*this.time_to_leave);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(server.getTopics().contains(topic)) // daca topicul inca exista , stergem mesajul
        {
            topic.deleteMessage(this.message);
        }
    }
}
