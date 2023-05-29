import java.rmi.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main (String args[]) {

        try {

            //String url = "rmi://" + args[0] + "/Server";
            String url = "rmi://127.0.0.1/Server";

            ServerIntf serverIntf = (ServerIntf)Naming.lookup(url);

            Scanner sc = new Scanner(System.in);

            //System.out.println("The first num is: " + args[1]);
            //double d1 = Double.valueOf(args[1]).doubleValue();

            System.out.println("Enter first number : ");
            double d1 = sc.nextDouble();

            System.out.println("Enter second number : ");
            double d2 = sc.nextDouble();
            //double d2 = Double.valueOf(args[2]).doubleValue();

            System.out.println("Sum is: " + serverIntf.add(d1, d2));

        } catch (Exception q){

            System.out.println("Exception");

        }

    }

}