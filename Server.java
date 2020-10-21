import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    int port;
    public ArrayList<ClientSlave> clientsSlaves = new ArrayList<ClientSlave>();
    public ArrayList<Topic> topics = new ArrayList<Topic>();

    public ArrayList<ClientSlave> getClientsSlave() {
        return this.clientsSlaves;
    }
    public ArrayList<Topic> getTopics(){ return this.topics; }

    public synchronized boolean addTopic(Topic topic) { // add a new topic with a type doesn't exist
        for (Topic topic_index : topics) {
            if ((topic_index.getType()).equals(topic.getType())) {
                return false;
            }
        }
        this.topics.add(topic);
        new EraserTopics(topic, this).start();
        return true;
    }

    public synchronized void eraseTopic(Topic topic) // erase a topic when time to leave expires
    {
        int index_current = 0;
        for (Topic topic_index : topics) {
            if (topic_index.equals(topic)) {
                topics.remove(index_current);
                return;
            }
            index_current++;
        }
    }

    public synchronized ArrayList<String> getAllTopicsType() {
        ArrayList<String> topics_type = new ArrayList<String>();
        if(topics.size()==0) return null;
        for (Topic topic : topics) {
            topics_type.add(topic.getType());
        }
        return topics_type;
    }

    public synchronized ArrayList<String> getMessagesFromTopic(String type_of_topic) {
        for (Topic topic : topics) {
            if ((topic.getType()).equals(type_of_topic))
            {
                if(topic.getMessages()!=null)
                    return topic.getMessages();
            }
        }
        return null;
    }

    public synchronized boolean writeInTopic(String type_of_topic, String message,int time_to_leave) {
        for (Topic topic : topics) {
            if ((topic.getType()).equals(type_of_topic)) {
                topic.addMessage(message);
                new EraserMessages(topic,time_to_leave,message,this).start();
                return true;
            }
        }
        return false;
    }


    public Server(int port) {
        this.port = port;
    }


    public void execute() {
        System.out.println("Server started ...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientSlave clientSlave = new ClientSlave(this, socket);
                clientsSlaves.add(clientSlave);
                clientSlave.start();
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String argv[]) {
        Server server = new Server(5555);
        server.execute();
    }
}


