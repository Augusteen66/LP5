import AdditionModule.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class AdditionClient {
    
    public static void main(String args[]) {
        
        try {

            ORB orb = ORB.init(args, null);

            String name = "Addition";

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            NamingContextExt n_cont = NamingContextExtHelper.narrow(objRef);

            Addition addn_impl = (Addition)AdditionHelper.narrow(n_cont.resolve_str(name));

            double num1 = 10;
            double num2 = 20;

            System.out.println("Fine till here");

            double res = addn_impl.add(num1, num2);

            System.out.println("Result is " + res);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
