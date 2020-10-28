import java.io.*;
import java.net.Socket;

public class ClientWriter extends Thread {

    Socket socket;
    Client client;
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter out;

    public ClientWriter(Socket socket,Client client)
    {
        this.socket=socket;
        this.client=client;
        try {
            out = new PrintWriter(new DataOutputStream(socket.getOutputStream()),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        out.println(client.name);
        System.out.println("Welcome " + client.name + "!");
        try {
            while(true)
            {
                String input = keyboard.readLine();
                out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
