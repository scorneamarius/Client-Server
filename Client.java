import java.io.*;
import java.net.Socket;

public class Client {

    int port;
    String hostname;
    String name;

    public Client(int port , String hostname){
        this.port=port;
        this.hostname=hostname;
    }

    public void execute()
    {
        try {

            Socket socket=new Socket(hostname,port);
            System.out.println("Connected to the server!");

            BufferedReader inputFromServer= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new DataOutputStream(socket.getOutputStream()),true);
            BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));

            String response=inputFromServer.readLine();
            System.out.println(response);
            response=keyboard.readLine();
            this.name=response;
            out.println(response);

            ClientReader clientReader = new ClientReader(socket , this);
            ClientWriter clientWriter = new ClientWriter(socket, this );

            clientReader.start();
            clientWriter.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main (String args[]) {

        Client client = new Client(5555, "localhost");
        client.execute();
    }
}
