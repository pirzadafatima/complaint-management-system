public class Complaint 
{
    private int complaintID;
    private String type;
    private String details;
    private String tchUsername;
    private String dept; 
    private State state;
    boolean isNew;
    private String solution = "no solution";
    private final NotificationService notificationService;

    public Complaint(int complaintID, String type, String details, String tchUsername, String dept)
    {
        this.complaintID = complaintID;
        this.type = type;
        this.details = details;
        this.tchUsername = tchUsername;
        this.dept = dept;
        this.state = new NewState(this);
        notificationService = new NotificationService();
    } 

    public Complaint(int complaintID, String type, String details, String tchUsername, String dept, boolean isNew)
    {
        this.complaintID = complaintID;
        this.type = type;
        this.details = details;
        this.tchUsername = tchUsername;
        this.dept = dept;
        this.isNew = isNew;
        notificationService = new NotificationService();
    } 

    public void setSolution(String solution)
    {
        if(!(solution.equals("no solution")))
        {
            this.solution = solution;
            notificationService.notifyObservers(Event.SET_SOL, this);
        }

        else
        {
            this.solution = solution;
        }
    }

    public void setState(State state)
    {
        this.state = state;

        //IF STATE SET TO RESOLVED 
        if(this.getState().getClass().getSimpleName().equals("ResolvedState"))
        {
            notificationService.notifyObservers(Event.STATE_RESOLVE, this);
        }
    }

    public NotificationService getService() 
    {
        return notificationService;
    }

    public String getSolution()
    {
        return solution;
    }

    public State getState()
    {
        return state;
    }

    public boolean newState(Complaint c) 
    {
        return state.newState(this);
    }

    public boolean assign(Complaint c) 
    {
        return state.assign(this);
    }

    public boolean resolve(Complaint c) 
    {
        return state.resolve(this);
    }

    public boolean close(Complaint c) 
    {
        return state.close(this);
    }

    public int getID()
    {
        return complaintID;
    }

    public String gettchUsername()
    {
        return tchUsername;
    }

    public String getdept()
    {
        return dept;
    }

    public String getdetails()
    {
        return details;
    }

    public String gettype()
    {
        return type;
    }

    public void printComplaint() 
    {
        System.out.println(String.format("%s %s %s %s %s %s", 
                                         this.getID(), this.gettype(), this.getdetails(), this.gettchUsername(), this.getdept(), this.getState().getClass().getSimpleName()));
    }
}
