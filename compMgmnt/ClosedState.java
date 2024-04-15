public class ClosedState extends State
{
    ClosedState(Complaint c)
    {
        super(c);
        saveToFile("complaint_state_file.txt");
    }

    public ClosedState(Complaint c, boolean isNew) 
    {
        super(c);
        //System.out.println("I am in close state.");
    }

    @Override
    public boolean newState(Complaint c) 
    {
        System.out.println("The complaint has been closed.");
        return false;

    }

    @Override
    public boolean assign(Complaint c)
    {
        System.out.println("The complaint has been closed.");
        return false;
    }

    @Override
    public boolean resolve(Complaint c)
    {
        System.out.println("The complaint has been closed.");
        return false;
    }
    
    @Override
    public boolean close(Complaint c)
    {
        System.out.println("The complaint has been closed.");
        return false;
    }
}
