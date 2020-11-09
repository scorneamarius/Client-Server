import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientReader extends Thread {

    private Socket socket;
    private Client client;

    public ClientReader(Socket socket , Client client)
    {
        this.client=client;
        this.socket=socket;
    }

    public void run()
    {
        try {
            BufferedReader inputFromServer= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
            String response;
            while(true)
            {
                response=inputFromServer.readLine();
                while(response==null){
                    try{
                        Thread.sleep(10);
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                }

                if(response.equals("quit")){
                    if(this.socket!=null){
                        socket.close();
                        break;
                    }
                }
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
