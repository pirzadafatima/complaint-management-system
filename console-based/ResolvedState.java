public class ResolvedState extends State
{
    ResolvedState(Complaint c)
    {
        super(c);
        saveToFile("complaint_state_file.txt");
    }

    public ResolvedState(Complaint c, boolean isNew) 
    {
        super(c);
        //System.out.println("I am in resolved state.");
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
        // Transition to AssignedState if manager or teacher did not approve solution 
        c.setState(new AssignedState(c));

        return true;
    }

    @Override
    public boolean resolve(Complaint c)
    {
        System.out.println("Already resolved");
        return false;
    }
    
    @Override
    public boolean close(Complaint c)
    {
        // Transition to CloseState if solution approved
        c.setState(new ClosedState(c));

        return true;
    }
}
