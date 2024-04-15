import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Employee extends User
{
    private String name;
    private SupportingDept dept;
    private List<Complaint> Assignedcomplaints;
    
    public Employee (String username, String password, String name, SupportingDept dept) 
    {
        super(username, password, "Employee");
        this.name = name;
        this.dept = dept;
        this.Assignedcomplaints = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public DepartmentName gettDept()
    {
        return dept.getDeptName();
    }

    public List<Complaint> geComplaints()
    {
        return Assignedcomplaints;
    }

    public void printEmployee() 
    {
        System.out.println("Username: " + this.getUsername() + ", Name: " + this.getName());
    }

    public List<Complaint> loadAssignedComplaints()
    {
        Assignedcomplaints = Manager.getAssignedComplaintsForEmployee(this.getUsername()); //all complaints assigned to this employee at any time 
        List<Complaint> newlyAssigned = Assignedcomplaints.stream().filter(complaint -> complaint.getState() instanceof AssignedState).collect(Collectors.toList());
        return newlyAssigned;
    }

    public boolean setSolution(String sol, Complaint c)
    {
        if (c == null) 
        {
            System.out.println("Complaint is null");
            return false;
        }

        //String nullString = null;
        String currentSolution = c.getSolution();
        //System.out.println("Current solution: " + currentSolution);
        //System.out.println("Length of solution: " + c.getSolution().length());
        //System.out.println("Is null: " + "null".equals(c.getSolution()));

        if (currentSolution.equals("no solution"))   //only set solution if any other employee has not already set it
        {
            //get specific dept manager 
            Manager m = dept.getManagerofDept();
            //System.out.println("Mname: " + m.getName());
            c.getService().subscribe(Event.SET_SOL, m);   //adding observer for Observer state pattern
            c.setSolution(sol);
            ComplaintMgmnt.updateComplaintInFile(c);
            return true;
        }

        else
        {
            return false;
            //System.out.println("The solution to this complaint has already been provided by another employee.");
        }
        
    }
}

    
   /*  public void resolveComplaint(Complaint complaintsAssigned) {
        //complaintsAssigned.removeIf(assignment -> assignment.getJob().equals(job));
        complaintsCompleted.add(job);
        System.out.println(name + " completed job " + job);
    }
    */