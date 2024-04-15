public class AssignedState extends State
{

    AssignedState(Complaint c)
    {
        super(c);
        saveToFile("complaint_state_file.txt");
    }

    public AssignedState(Complaint c, boolean isNew) 
    {
        super(c);
        //System.out.println("I am in assigned state.");
    }

    @Override
    public boolean newState(Complaint c) 
    {
        // Transition to NewState
        c.setState(new NewState(c));
        return true;

    }

    @Override
    public boolean assign(Complaint c)
    {
        System.out.println("Already assigned.");
        return false;
    }

    @Override
    public boolean resolve(Complaint c)
    {
        // Transition to ResolvedState
        c.setState(new ResolvedState(c));

        return true;
    }
    
    @Override
    public boolean close(Complaint c)
    {
        System.out.println("This has not been resolved yet.");
        return false;
    }
    
}
