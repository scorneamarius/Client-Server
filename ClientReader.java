import java.io.*;
import java.net.Socket;

public class ClientReader extends Thread {

    Socket socket;
    Client client;

    public ClientReader(Socket socket , Client client)
    {
        this.client=client;
        this.socket=socket;
    }

    public void run()
    {

        try {
            BufferedReader inputFromServer= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String response;
            while(true)
            {
                response=inputFromServer.readLine();
                System.out.println(response);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
