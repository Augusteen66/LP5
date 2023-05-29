import AdditionModule.AdditionPOA;

public class AdditionImpl extends AdditionPOA{
    
    AdditionImpl() {
        super();
        System.out.println("Reverse object created");
    }

    public double add(double num1, double num2) {
        return (num1 + num2);
    }

}
