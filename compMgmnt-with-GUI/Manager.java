import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Manager extends User implements Observer
{
    private String name;
    private SupportingDept dept;
    private static final String ASSIGNEDCOMPLAINTSFILE = "AssignedComplaints.txt";
    
    public Manager(String username, String password, String name, SupportingDept dept) 
    {
        super(username, password, "Manager");
        this.name = name;
        this.dept = dept;
    }
   
    String getName()
    {
        return name;
    }

    DepartmentName gettDept()
    {
        return dept.getDeptName();
    }

    public List<Complaint> getNewComplaintNotification()  
    {
        List<Complaint> c = dept.getDeptComplaints();
        List<Complaint> newComplaints = new ArrayList<>();

        for(Complaint comp: c)
        {
            if(comp.getState().getClass().getSimpleName().equals("NewState"))
            {
                newComplaints.add(comp);
            }
        }

        return newComplaints;
    }

    public void assignComplaint(Complaint c, List<Employee> selectedEmployees) {
        StringBuilder assignedEmployeesLine = new StringBuilder();

        for (Employee emp : selectedEmployees) {
            emp.geComplaints().add(c);
            assignedEmployeesLine.append(emp.getUsername()).append(":");
        }

        saveAssignedComplaintToFile(c.getID(), assignedEmployeesLine.toString());

        // Set the complaint state to assigned
        c.assign(c);
    }

    private void saveAssignedComplaintToFile(int complaintID, String assignedEmployeesLine) 
    {
        File file = new File(ASSIGNEDCOMPLAINTSFILE);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) 
        {
            writer.write(String.format("%d:%s", complaintID, assignedEmployeesLine));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Complaint> getAssignedComplaintsForEmployee(String employeeUsername) 
    {
        List<Complaint> assignedComplaints = new ArrayList<>();
    
        try (Scanner scanner = new Scanner(new File(ASSIGNEDCOMPLAINTSFILE))) 
        {
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
    
                //System.out.println("Parts: " + Arrays.toString(parts));
    
                if (parts.length > 1) 
                {
                    // Iterate over usernames starting from parts[1]
                    for (int i = 1; i < parts.length; i++)
                     {
                        String username = parts[i];
                        //System.out.println("Username: " + username);
    
                        if (username.equals(employeeUsername)) 
                        {
                            int complaintID = Integer.parseInt(parts[0]);
                            // Load the complaint from your system based on complaintID
                            Complaint complaint = ComplaintMgmnt.loadComplaintFromSystem(complaintID);
                            assignedComplaints.add(complaint);
                            break;  // No need to check further if the employee is found in the line
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    
        return assignedComplaints;
    }
    
    public List<Employee> selectEmployees() 
    {
        List<Employee> allEmployees = dept.getEmployees();
        List<Employee> selectedEmployees = new ArrayList<>();
    
        System.out.println("Employees in " + dept.getDeptName() + " Department:");
        for (Employee employee : allEmployees) {
            employee.printEmployee();
        }
    
        Scanner input = new Scanner(System.in);
        System.out.println("Enter employee usernames (separated by spaces) of the employees you wish to assign this job to:");
        String[] usernames = input.nextLine().split(" ");
    
        for (String u : usernames) {
            for (Employee employee : allEmployees) {
                if (employee.getUsername().equals(u)) {
                    selectedEmployees.add(employee);
                    break; // Break to the next input username
                }
            }
        }
    
        input.close();
    
        return selectedEmployees;
    }

    @Override
    public void update(Event eventType, Complaint c)
    {
        //save all the complaints ID, solution and mangerUsername in notification file 
        //when manager logs in show from notification file 
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Notifications.txt", true))) 
        {
            writer.write(this.getUsername() + ":" + c.getID() + ":" + c.getdetails() + ":" + c.getSolution());
            writer.newLine();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public List<String> viewNotifications(String username) 
    {
        File file = new File("Notifications.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        List<String> managerNotifications = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("Notifications.txt"))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(":");
                if (parts.length >= 4 && parts[0].equals(username))
                {
                    int complaintID = Integer.parseInt(parts[1]);
                    Complaint complaint = findComplaintById(complaintID);
    
                    // Check if the complaint is in AssignedState so that old notifications saved in file are not included everytime 
                    if (complaint != null && complaint.getState() instanceof AssignedState) 
                    {
                        String managerNotification = parts[1] + ":" + parts[2] + ":" + parts[3];
    
                        managerNotifications.add(managerNotification);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return managerNotifications;
    }
    
    // Helper method to find a complaint by ID
    private Complaint findComplaintById(int complaintID)
     {
        List<Complaint> complaints = dept.getDeptComplaints();
        for (Complaint complaint : complaints) 
        {
            if (complaint.getID() == complaintID) 
            {
                return complaint;
            }
        }
        return null;
    }
    
    public List<String> viewAllFiledComplaints()   //see all complaints and statuses in a given dept 
    {
        List <Complaint> allComps = dept.getDeptComplaints();
        List<String> deptComplaints = new ArrayList<>();

        for(Complaint c: allComps)
        {
            int id = c.getID();
            String problem = c.getdetails();
            String status = c.getState().getClass().getSimpleName();

            // System.out.print("Id: " + id + " ");
            // System.out.print("Problem: " + problem + " ");
            // System.out.print("Status: " + status + " ");

            String comp = id + ":" + " " + problem + ":" + status;
            deptComplaints.add(comp);

        }

        return deptComplaints;
    }

    public void reviewComplaintSolution(int Cid, boolean satisfied)
    {
        Complaint c = findComplaintById(Cid);
        System.out.println(satisfied);
        if(satisfied)
        {
            //get teacher who filed complaint and add in the list of observers so that he/she is notified of complaint resolved
            Teacher t = Administrator.findTeacherByUsername(c.gettchUsername());
            c.getService().subscribe(Event.STATE_RESOLVE, t);   //adding observer for Observer state pattern
            c.resolve(c);
        }
        else
        {

            //c.newState(c);
            c.setSolution("no solution");
            System.out.print("you have entered else");
            ComplaintMgmnt.updateComplaintInFile(c);
        }
    }
        
}

