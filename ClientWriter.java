import java.io.*;
import java.net.Socket;

public class ClientWriter extends Thread {

    private Socket socket;
    private Client client;
    private BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter out;

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

        out.println(client.getName());
        System.out.println("Welcome " + client.getName() + "!");

        try {
            while(true)
            {
                String input = keyboard.readLine();
                if(input.equals("quit")){
                    out.println(input);
                    if(socket!=null){
                        this.socket.close();
                        break;
                    }
                }
                out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
