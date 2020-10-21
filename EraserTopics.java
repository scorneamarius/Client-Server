public class EraserTopics extends Thread {
    private Topic topic;
    private Server server;

    public EraserTopics(Topic topic,Server server)
    {
        this.topic=topic;
        this.server=server;
    }

    public void run()
    {
        try {
            Thread.sleep(topic.getTimeToLeave()*1000*60);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.eraseTopic(this.topic);
    }
}
