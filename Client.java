import java.io.*;
import java.net.Socket;


public class Client {

    private int port;
    private String hostname;
    private String name;

    public Client(int port , String hostname , String name){
        this.port=port;
        this.hostname=hostname;
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void execute()
    {
        Socket socket=null;
        try {

            socket=new Socket(hostname,port);
            System.out.println("Connected to the server.");

            ClientReader clientReader = new ClientReader(socket , this);
            ClientWriter clientWriter = new ClientWriter(socket, this );

            clientReader.start();
            clientWriter.start();

        } catch (IOException e) {
            e.printStackTrace();
            try{
                socket.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static void main (String[] args) {

        System.out.println("Type your name: ");
        BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));
        String inputName = null;
        try {
            inputName = keyboard.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client client = new Client(5555, "localhost",inputName);
        client.execute();
    }
}
