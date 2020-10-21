import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ClientSlave extends Thread {

    Server server;
    Socket socket;
    String nameClient;
    BufferedReader clientInput;
    PrintWriter clientOutput;

    public ClientSlave(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientOutput = new PrintWriter(new DataOutputStream(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeTask(String response) {

        if (response.equals("menu")) {
            clientOutput.println("For add TOPICS type -> add topic | [type_of_topic] | [time_to_leave_in_minutes]");
            clientOutput.println("To see TOPICS_TYPE available type -> read topics_type");
            clientOutput.println("To display message from a topic -> display message | [type of topic]");
            clientOutput.println("To write in a topic type -> write | [type_of_topic] | [message] | [time_to_leave_in_minutes]");
        } else if (response.contains("add topic")) {
            String[] arrayWords = response.split("\\| ");
            String type_of_topic = arrayWords[1].substring(0, arrayWords[1].length() - 1);
            int time_to_leave_in_minutes = parseInt(arrayWords[2]);
            System.out.println(type_of_topic + " "+time_to_leave_in_minutes);
            if (server.addTopic(new Topic(type_of_topic, time_to_leave_in_minutes)) == true)
                clientOutput.println("Topic added with succesfully");
            else
                clientOutput.println("Sorry but a topic with this type already exists");
        } else if (response.equals("read topics_type")) {
            ArrayList<String> topics_type = server.getAllTopicsType();
            if (topics_type ==null)
            {
                clientOutput.println("No topics available ");
            }
            else
            {
                int index_topic_type = 0;
                for (String topic : topics_type) {
                    clientOutput.println("Topic " + index_topic_type++ + " : " + topic);
                }
            }
        } else if (response.contains("display message ")) {
            String[] arrayWords = response.split("\\| ");
            String type_of_topic = arrayWords[1];
            ArrayList<String> messages = server.getMessagesFromTopic(type_of_topic);
            if (messages != null) {
                for (String message : messages) {
                    clientOutput.println("The message is : " + message);
                }
            } else {
                clientOutput.println("Sorry but this topic doesn't exist , it expires or it haven't any messages");
            }
        }else if(response.contains("write"))
        {
            String[] arrayWords = response.split("\\|");
            String type_of_topic = arrayWords[1].substring(1,arrayWords[1].length()-1);
            String message = arrayWords[2].substring(1,arrayWords[2].length()-1);
            int time_to_leave = Integer.parseInt(arrayWords[3].substring(1));
//            System.out.println(type_of_topic + " "+message+" "+time_to_leave);
            if(server.writeInTopic(type_of_topic,message,time_to_leave)==true)
                clientOutput.println("Writing in topic succeded");
            else
                clientOutput.println("The topic doesn't exists or it expires");
        }
    }
    public void run() {
        try {
            String response;
            response = clientInput.readLine();
            this.nameClient = response;
            while (true) {
                response = clientInput.readLine();
                executeTask(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
