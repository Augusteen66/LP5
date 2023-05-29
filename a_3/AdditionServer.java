import org.omg.CORBA.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CosNaming.NameComponent;
import AdditionModule.AdditionHelper;
import AdditionModule.Addition;
import org.omg.CosNaming.NamingContextPackage.*; 

public class AdditionServer {
    
    public static void main(String args[]) {
        
        try {

            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            System.out.println("Step 0");
            
            // Get reference from servant class
            AdditionImpl addImp = new AdditionImpl();
            

            org.omg.CORBA.Object obj = rootPOA.servant_to_reference(addImp);

            System.out.println("Step 1");

            Addition a = AdditionHelper.narrow(obj);

            System.out.println("Step 2");

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            System.out.println("Step 3");

            NamingContextExt naming_context = NamingContextExtHelper.narrow(objRef);

            System.out.println("Step 4");

            String name = "Addition";
            NameComponent path[] = naming_context.to_name(name);
            naming_context.rebind(path, a);

            System.out.println("Reverse Server reading and watting....");

            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
