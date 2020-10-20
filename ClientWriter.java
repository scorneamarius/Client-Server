import java.io.*;
import java.net.Socket;

public class ClientWriter extends Thread {

    Socket socket;
    Client client;

    public ClientWriter(Socket socket,Client client)
    {
        this.socket=socket;
        this.client=client;
    }

    public void run()
    {

        try {

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new DataOutputStream(socket.getOutputStream()),true);

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
