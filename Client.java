import java.io.*;
import java.net.Socket;

public class Client {

    int port;
    String hostname;
    String name;

    public Client(int port , String hostname , String name){
        this.port=port;
        this.hostname=hostname;
        this.name=name;
    }

    public void execute()
    {
        try {

            Socket socket=new Socket(hostname,port);
            System.out.println("Connected to the server!");

            ClientReader clientReader = new ClientReader(socket , this);
            ClientWriter clientWriter = new ClientWriter(socket, this );

            clientReader.start();
            clientWriter.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main (String args[]) {

        System.out.println("Type your name please ");
        BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));
        String name = null;
        try {
            name = keyboard.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client client = new Client(5555, "localhost",name);

        client.execute();

    }
}
