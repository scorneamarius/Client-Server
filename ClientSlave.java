import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientSlave extends Thread {

    Server server;
    Socket socket;
    String nameClient;
    public ClientSlave(Server server , Socket socket)
    {
        this.server=server;
        this.socket=socket;
    }


    public void run()
    {
        try {

            BufferedReader clientInput= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientOutput = new PrintWriter(new DataOutputStream(socket.getOutputStream()),true);
            String response;
            response = clientInput.readLine();
            System.out.println(response);
            this.nameClient=response;
            while(true)
            {
                response=clientInput.readLine();
                System.out.println(response);
                if(response.contains("Mihai"))
                    server.sendMessage(response, "Mihai");
                if(response.contains("quit"))
                {
                    socket.close();
                    clientInput.close();
                    clientOutput.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
    }

}
