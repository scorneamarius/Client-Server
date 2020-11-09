import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private int port;

    private ArrayList<ClientSlave> clientsSlaves = new ArrayList<ClientSlave>();
    private ArrayList<Topic> topics = new ArrayList<Topic>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private int maximumCapacityQueue=5;
    private ArrayList<String> names = new ArrayList<String>();
    private Object objectLockTopic=new Object();
    private Object objectLockMessage=new Object();


    public ArrayList<ClientSlave> getClientsSlave() {
        return this.clientsSlaves;
    }


    public ArrayList<String> getNames(){
        synchronized (objectLockMessage) {

            return names;
        }
    }
    public void addName(String name){
        synchronized (objectLockMessage) {
            this.names.add(name);
        }
    }

    public ArrayList<Topic> getTopics() {

        synchronized (objectLockTopic) {
            return this.topics;
        }
    }
    public void deleteNameClient(String name){
        synchronized (objectLockMessage) {
            this.names.remove(name);
        }
    }

    public void addMessageInQueue(Message message) {
        synchronized (objectLockMessage){
        if (messages.size() == maximumCapacityQueue)
            messages.remove(0);
        this.messages.add(message);
        for (Message m : messages) {
            System.out.println(m.displayMessageForServer());
        }
    }
    }

    public void removeMessageFromQueue(Message m)
    {
        synchronized (objectLockMessage) {
            ArrayList<Message> messagesAUX = new ArrayList<Message>();
            int index = 0;

            for (Message message : messages) {
                if (!((message.displayMessageForServer()).equals(m.displayMessageForServer()))) {
                    messagesAUX.add(message);
                }
                index++;
            }
            this.messages = messagesAUX;
        }
    }

    public ArrayList<Message> receiveMessagesFromQueue(String receiver)
    {
        synchronized (objectLockMessage) {
            ArrayList<Message> m = new ArrayList<Message>();
            for (Message message : messages) {
                if ((message.getReceiver().equals(receiver))) {
                    m.add(new Message(message.getMessage(), message.getReceiver(), message.getTransmitter()));
                }
            }
            for (Message message : m) {
                removeMessageFromQueue(message);
            }
            return m;
        }
    }

    public synchronized void deleteMessageFromQueue(Message message)
    {
        synchronized (objectLockMessage) {
            messages.remove(message);
        }
    }

    public boolean addTopic(Topic topic) { // add a new topic with a type doesn't exist

        synchronized (objectLockTopic) {
            for (Topic topic_index : topics) {
                if ((topic_index.getType()).equals(topic.getType())) {
                    return false;
                }
            }
            this.topics.add(topic);
            new EraserTopics(topic, this).start();
            return true;
        }
    }

    public void eraseTopic(Topic topic) // erase a topic when time to leave expires
    {
        synchronized (objectLockTopic) {
            int index_current = 0;
            for (Topic topic_index : topics) {
                if (topic_index.equals(topic)) {
                    topics.remove(index_current);
                    return;
                }
                index_current++;
            }
        }
    }

    public ArrayList<String> getAllTopicsType() {
        synchronized (objectLockTopic) {
            ArrayList<String> topics_type = new ArrayList<String>();
            if (topics.size() == 0) return null;
            for (Topic topic : topics) {
                topics_type.add(topic.getType());
            }
            return topics_type;
        }
    }

    public ArrayList<String> getMessagesFromTopic(String type_of_topic) {
        synchronized (objectLockTopic) {
            for (Topic topic : topics) {
                if ((topic.getType()).equals(type_of_topic)) {
                    if (topic.getMessages() != null)
                        return topic.getMessages();
                }
            }
            return null;
        }
    }

    public boolean writeInTopic(String type_of_topic, String message,int time_to_leave) {
        synchronized (objectLockTopic) {
            for (Topic topic : topics) {
                if ((topic.getType()).equals(type_of_topic)) {
                    topic.addMessage(message);
                    new EraserMessages(topic, time_to_leave, message, this).start();
                    return true;
                }
            }
            return false;
        }
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

    public static void main(String[] argv) {
        Server server = new Server(5555);
        server.execute();
    }
}
