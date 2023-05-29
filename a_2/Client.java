import java.io.*;
import java.net.*;

public class Client {
    
    public static void main(String args[]) {
        
        try {


                Socket soc = new Socket("localhost",6666);

                System.out.println("Connection established");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                

                PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
                

                BufferedReader serverMsg = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                

                while (true) {
                    System.out.println("Enter A String: ");
                    String str = br.readLine();

                    pw.println("Server says : " + str);

                    System.out.println(serverMsg.readLine());
                }


                soc.close();


    

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
