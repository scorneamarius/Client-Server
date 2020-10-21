import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {


    int port;
    ArrayList<ClientSlave>clientsSlaves = new ArrayList<ClientSlave>();
    Object object = new Object();
    public ArrayList<ClientSlave> getClientsSlave()
    {
        return this.clientsSlaves;
    }

    public void sendMessage(String message,String nameClient)
    {
        synchronized (object)
        {
            PrintWriter out;
            for(ClientSlave clientSlave :clientsSlaves)
            {
                if((clientSlave.nameClient).equals(nameClient))
                {
                    try {
                        out=new PrintWriter(new DataOutputStream((clientSlave.socket).getOutputStream()),true);
                        out.println(message);
//                        System.out.println("4");
                        break;
                    }catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public Server(int port)
    {
        this.port=port;
    }

    public static void main (String argv[])
    {
        Server server = new Server(5555);
        server.execute();
    }

    public void execute()
    {
        System.out.println("Server started ...");
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            PrintWriter out ;
            BufferedReader in;
            String response;
            System.out.println("Chat Server is listening on port " + port);
            while (true)
            {
                Socket socket = serverSocket.accept();
                out = new PrintWriter(new DataOutputStream(socket.getOutputStream()),true);
                in= new BufferedReader(new InputStreamReader(socket.getInputStream()));



                ClientSlave clientSlave = new ClientSlave(this,socket);
                clientsSlaves.add(clientSlave);
                clientSlave.start();
            }
        } catch (IOException ex)
        {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


