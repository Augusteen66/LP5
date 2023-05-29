import java.io.*;
import java.net.*;

public class Server {
    
    public static void main(String args[]) {
        
        try {

            

            //while (true) {
                ServerSocket s = new ServerSocket(6666);

                Socket soc = s.accept();
                

                System.out.println("Connection established");

                BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                

                PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
                

                while (true) {
                    String str = br.readLine();
                    System.out.println("Client sent : " + str);
                    pw.println("Server says : " + str);
                }

                soc.close();
                s.close();

            //}

            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
