public class NewState extends State 
{
    public NewState(Complaint c) 
    {
        super(c);
        //System.out.println("I am in new state.");
        saveToFile("complaint_state_file.txt");
    }

    public NewState(Complaint c, boolean isNew) 
    {
        super(c);
        //System.out.println("I am in new state.");
    }

    @Override
    public boolean newState(Complaint c) 
    {
        System.out.println("Already a new complaint.");
        return false;
    }

    @Override
    public boolean assign(Complaint c) 
    {
        // Transition to AssignedState
        c.setState(new AssignedState(c));
        return true;
    }

    @Override
    public boolean resolve(Complaint c) 
    {
        System.out.println("Complaint has not been assigned to anyone yet.");
        return false;
    }

    @Override
    public boolean close(Complaint c) 
    {
        System.out.println("Complaint has not been assigned to anyone yet.");
        return false;

    }
}
