import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Main
{
    public static void main(String[] args) 
    {
        System.out.println("Welcome to FAST-NU Complaint Management System.");

        userAuthentication userAuthentication = new userAuthentication();
        Scanner input = new Scanner(System.in);

        while (true) 
        {
            System.out.println("1. Log In");
            System.out.println("2. Exit");
            System.out.println("Choose any of the above options.");

            int check = input.nextInt();
            input.nextLine(); // Consume the newline

            if(check == 1)    //old user is logging in
            {
                //new LoginPage();
                   
                    System.out.println("Enter username: ");
                    String l_Username = input.nextLine();

                    System.out.println("Enter password: ");
                    String l_Password = input.nextLine();

                    DepartmentMngmtMain.getDept(DepartmentName.IT).setEmployees();
                    DepartmentMngmtMain.getDept(DepartmentName.IT).sManager();
                    DepartmentMngmtMain.getDept(DepartmentName.IT).setComplaints();

                    DepartmentMngmtMain.getDept(DepartmentName.Admin).setEmployees();
                    DepartmentMngmtMain.getDept(DepartmentName.Admin).sManager();
                    DepartmentMngmtMain.getDept(DepartmentName.Admin).setComplaints();

                    DepartmentMngmtMain.getDept(DepartmentName.Accounts).setEmployees();
                    DepartmentMngmtMain.getDept(DepartmentName.Accounts).sManager();
                    DepartmentMngmtMain.getDept(DepartmentName.Accounts).setComplaints();
                    
                    String s = userAuthentication.logIn(l_Username, l_Password);
                    if (s != null)
                    {
                        if (s.equals("Administrator")) 
                        {
                            Administrator a = new Administrator(l_Username, l_Password);
                            System.out.println("Press 1 if you want to add a Teacher.");
                            System.out.println("Press 2 if you want to remove a Teacher.");
                            System.out.println("Press 3 if you want to add a Manager.");
                            System.out.println("Press 4 if you want to remove a Manager.");
                            System.out.println("Press 5 if you want to add an Employee.");
                            System.out.println("Press 6 if you want to remove an Employee.");
                            System.out.println("Press 7 if you want to add a director.");
                            System.out.println("Press 8 if you want to remove a director.");
                            int c = input.nextInt();
                        
                            // Consume the newline character
                            input.nextLine();
                        
                            if (c == 1) 
                            {
                                while(true)
                                {
                                    System.out.println("Add Teacher Username.");
                                    String n = input.nextLine();
                                    System.out.println("Add Teacher Password.");
                                    String p = input.nextLine();
                                    System.out.println("Add Teacher Name.");
                                    String x = input.nextLine();
                                    System.out.println("Add Teacher Subject.");
                                    String sb = input.nextLine();
                                    System.out.println("Add Teacher Dept.");
                                    String dp = input.nextLine();
                            
                                    boolean tttt = a.AddTeacher(n, p, x, dp, sb);
                                    if (tttt == true)
                                    {
                                        break;
                                    }

                                    else if(tttt == false)
                                    {
                                        System.out.println("This username already exists. Try something else."); 
                                    }
                                }
                            }

                            else if (c == 2)
                            {
                                System.out.println("Enter Teacher Username.");
                                String n1 = input.nextLine();

                                boolean tt = a.RemoveTeacherfromAllfiles(n1);
                                if(tt == true)
                                {
                                    break;
                                }
                                else
                                {
                                    System.out.println("This username does not exist. Enter username again.");
                                }
                            }

                            else if (c == 3)
                            {
                                while(true)
                                {
                                    System.out.println("Add Manager Username.");
                                    String n = input.nextLine();
                                    System.out.println("Add Manager Password.");
                                    String p = input.nextLine();
                                    System.out.println("Add Manager Name.");
                                    String x = input.nextLine();
                                    System.out.println("Add Manager Dept.");  //only accts, admin or IT 
                                    String dp = input.nextLine();
                            
                                    boolean flag = a.addManager(n, p, x, dp);
                                    if (flag == true)
                                    {
                                        break;
                                    }

                                    else if(flag == false)
                                    {
                                        System.out.println("Cannot add manager. Check username or if 3 managers are already entered."); 
                                    }
                                }
                            }

                            else if (c == 4)
                            {
                                System.out.println("Enter Manager Username.");
                                String n1 = input.nextLine();

                                boolean tt = a.RemoveManagerfromAllfiles(n1);
                                if(tt == true)
                                {
                                    break;
                                }
                                else
                                {
                                    System.out.println("This username does not exist. Enter username again.");
                                }
                            }

                            else if (c == 5)
                            {
                                while(true)
                                {
                                    System.out.println("Add Employee Username.");
                                    String n = input.nextLine();
                                    System.out.println("Add Employee Password.");
                                    String p = input.nextLine();
                                    System.out.println("Add Employee Name.");
                                    String x = input.nextLine();
                                    System.out.println("Add Employee Dept."); //only accts, admin or IT
                                    String dp = input.nextLine();
                            
                                    boolean flag = a.AddEmployee(n, p, x, dp);
                                    if (flag == true)
                                    {
                                        break;
                                    }

                                    else if(flag == false)
                                    {
                                        System.out.println("This username already exists. Try something else."); 
                                    }
                                }
                            }

                            else if (c == 6)
                            {
                                System.out.println("Enter Employee Username.");
                                String n1 = input.nextLine();

                                boolean tt = a.RemoveEmployeefromAllfiles(n1);
                                if(tt == true)
                                {
                                    break;
                                }
                                else
                                {
                                    System.out.println("This username does not exist. Enter username again.");
                                }
                            }

                            else if (c == 7) 
                            {
                                while(true)
                                {
                                    System.out.println("Add Director Username.");
                                    String n = input.nextLine();
                                    System.out.println("Add Director Password.");
                                    String p = input.nextLine();
                                    System.out.println("Add Director Name.");
                                    String x = input.nextLine();
                            
                                    boolean tttt = a.AddDirector(n, p, x);
                                    if (tttt == true)
                                    {
                                        break;
                                    }

                                    else if(tttt == false)
                                    {
                                        System.out.println("Cannot add director. Check username or if director is already entered."); 
                                    }
                                }
                            }

                            else if (c == 8)
                            {
                                System.out.println("Enter Director Username.");
                                String n1 = input.nextLine();

                                boolean tt = a.RemoveDirectorfromAllfiles(n1);
                                if(tt == true)
                                {
                                    break;
                                }
                                else
                                {
                                    System.out.println("This username does not exist. Enter username again.");
                                }
                            }

                        }

                        else if(s.equals("Teacher"))
                        {
                            String[] teachDetails = getTeachDeetsBasedOnUName(l_Username);
                            Teacher t = new Teacher(teachDetails[0],teachDetails[1], teachDetails[2], teachDetails[3], teachDetails[4]);

                            t.LoadFiledComplaints();  //as teacher has logged in, load their filed complaints in system

                            List<String> notifications = t.viewNotifications(l_Username);
                            int numOfNotif = notifications.size();

                            System.out.println("You have " + numOfNotif + " new notification(s).");
                            System.out.println("Press 1 if you want to view new Notifications.");   //will give feedback on solutions as well
                            System.out.println("Press 2 if you want to view your filed complaints and see their status.");
                            System.out.println("Press 3 if you want to file a new complaint.");
                            int option = input.nextInt();

                            if(option == 1)
                            {
                                for(String ntf: notifications)
                                {
                                    System.out.println(ntf);
            
                                }
                                
                                for(int i = 0; i< notifications.size(); i++)
                                {
                                    input.nextLine();
                                    System.out.println("Enter complaintID and true/false(separated with space) if you are satisfied with solution/not satisfied with solution.");
                                    String[] feedback = input.nextLine().split(" ");

                                    int id = Integer.parseInt(feedback[0]);
                                    boolean satisfied = Boolean.parseBoolean(feedback[1]);

                                    // Call the reviewComplaintSolution function with the extracted values
                                    t.reviewComplaintSolution(id, satisfied);
                                    System.out.println("Enter 0 if you want to stop reviewing. Anything else if you wish to continue.");
                                    int g = input.nextInt();

                                    if(g == 0)
                                    {
                                        break;
                                    }
                                }
                                
                                System.out.println("Review completed.");
                                
                                
                            }

                            else if(option == 2)
                            {
                                List<Complaint> c = t.getFiledComplaints(); 

                                for(Complaint comp: c)
                                {
                                    comp.printComplaint();
                                }
                            }

                            else if(option == 3)
                            {
                                input.nextLine();
                                System.out.println("Enter type of complaint. Problem or request for equipment.");
                                String type = input.nextLine();
                                System.out.println("Enter details.");
                                String details = input.nextLine();
                                System.out.println("Enter department you want to fwd it to: IT, Accounts, Admin;");
                                String dept = input.nextLine();
                                
                                t.fileComplaint(type, details, l_Username, dept);
                            }
                        }

                        else if(s.equals("Manager"))
                        {
                            String[] mngrDetails = getMngrDeetsBasedOnUName(l_Username);
                            DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                            Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));

                            List<String> notifications = m.viewNotifications(l_Username);
                            int numOfNotif = notifications.size();

                            System.out.println("You have " + numOfNotif + " new notification(s).");
                            System.out.println("Press 1 if you want to view new Notifications.");
                            System.out.println("Press 2 if you want to view newly filed complaints and assign them.");
                            System.out.println("Press 3 if you want to see status of all department complaints.");
                            //System.out.println("Press 4 if you want to give feedback on any of assigned solutions."); //integrate this within notification?
                            int option = input.nextInt();

                            if(option == 1)
                            {
                                for(String ntf: notifications)
                                {
                                    System.out.println(ntf);
            
                                }
                                
                                for(int i = 0; i< notifications.size(); i++)
                                {
                                    input.nextLine();
                                    System.out.println("Enter complaintID and true/false(separated with space) if you are satisfied with solution/not satisfied with solution.");
                                    String[] feedback = input.nextLine().split(" ");

                                    int id = Integer.parseInt(feedback[0]);
                                    boolean satisfied = Boolean.parseBoolean(feedback[1]);

                                    // Call the reviewComplaintSolution function with the extracted values
                                    m.reviewComplaintSolution(id, satisfied);
                                    System.out.println("Enter 0 if you want to stop reviewing. Anything else if you wish to continue.");
                                    int g = input.nextInt();

                                    if(g == 0)
                                    {
                                        break;
                                    }
                                }
                                
                                System.out.println("Review completed.");
                                
                                
                            }

                            else if(option == 2)
                            {

                                List<Complaint> c = m.getNewComplaintNotification();

                                if(c.size() != 0)
                                {
                                    for(Complaint comp: c)
                                    {
                                        comp.printComplaint();
                                    }

                                    System.out.println("Enter the Complaint ID from the above which you wish to assign.");
                                    int x = input.nextInt();

                                    Complaint c1 = null;

                                    for(Complaint comp: c)
                                    {
                                        if(comp.getID() == x)
                                        {
                                            c1 = comp;
                                            break;
                                        }
                                    }

                                    if(c1 != null)
                                    {
                                        m.assignComplaint(c1);
                                    }
                                }

                                else
                                {
                                    System.out.println("You have no new filed complaints.");
                                }
                            }

                            else if(option == 3)
                            {
                                List<String> deptComplaints = m.viewAllFiledComplaints();

                                for(String compDpt: deptComplaints)
                                {
                                    System.out.println(compDpt);
            
                                }
                            }
                        }

                        else if(s.equals("Employee"))
                        {
                            //String nullString = null;
                            String[] empDetails = getEmpDeetsBasedOnUName(l_Username);  //if we send filename as Parameter then the same function can be used instead of making 3 separate functions
                            DepartmentName dept = DepartmentName.valueOf(empDetails[3]);
                            Employee e = new Employee(empDetails[0], empDetails[1], empDetails[2], DepartmentMngmtMain.getDept(dept));

                            List<Complaint> c = e.loadAssignedComplaints();

                            for(Complaint comp: c)
                            {
                                comp.printComplaint();
                                String sol = comp.getSolution();
                 
                                if(sol.equals("no solution"))
                                {
                                    System.out.println("Solution: No Solution.");
                                }

                                else
                                {
                                    System.out.println("Solution: " + comp.getSolution());
                                }
                            }

                            System.out.println("Enter the Complaint ID from the above which you wish to resolve. Enter 0 if there is no unsolved complaint.");
                            int x = input.nextInt();
                            input.nextLine();

                            if(x != 0)
                            {
                                System.out.println("Enter the solution.");
                                String s1 = input.nextLine();

                                Complaint c1 = null;

                                for(Complaint comp: c)
                                {
                                    if(comp.getID() == x)
                                    {
                                        c1 = comp;
                                        break;
                                    }
                                }

                                if(c1 != null)
                                {
                                    e.setSolution(s1, c1);
                                }
                            }

                            else
                            {
                                System.out.println("Bye.");
                            }


                        }

                        else if(s.equals("Director"))
                        {
                            String[] teachDetails = getDirectorDeetsBasedOnUName(l_Username);
                            Director d = new Director(teachDetails[0], teachDetails[1], teachDetails[2]);

                                System.out.println("If you want to see dept wise summary, enter the time frame.");
                                System.out.println("Enter start date (YYYY-MM-DD): ");
                                String startDateStr = input.nextLine(); 
                                Date sD = d.parseDate(startDateStr);

                                System.out.println("Enter end date (YYYY-MM-DD): ");
                                String endDateStr = input.nextLine(); 
                                Date eD = d.parseDate(endDateStr);

                                List<Complaint> filteredComplaints =  d.viewComplaintSummary(sD, eD);

                                List<Complaint> itComplaints = new ArrayList<>();
                                List<Complaint> adminComplaints = new ArrayList<>();
                                List<Complaint> accountsComplaints = new ArrayList<>();

                                for (Complaint complaint : filteredComplaints) 
                                {
                                    switch (complaint.getdept()) 
                                    {
                                        case "IT":
                                            itComplaints.add(complaint);
                                            break;
                                        case "Admin":
                                            adminComplaints.add(complaint);
                                            break;
                                        case "Accounts":
                                            accountsComplaints.add(complaint);
                                            break;
                                        // Add more cases for other departments if needed
                                    }
                                }

                                // Print department-wise summary----- i am not doing this in function because i think you can directly print arrays on interface
                                System.out.println();
                                System.out.println("IT Department Summary - New Complaints: " + itComplaints.size());
                                for (Complaint complaint : itComplaints) 
                                {
                                    System.out.print("ComplaintID: " + complaint.getID() + " ");
                                    String status = complaint.getState().getClass().getSimpleName();
                                    System.out.println("Status: " + status);
                                    System.out.println();
                                    System.out.println();
                                }
                                System.out.println();
                                System.out.println("Admin Department Summary - New Complaints: " + adminComplaints.size());
                                for (Complaint complaint : adminComplaints) 
                                {
                                    System.out.print("ComplaintID: " + complaint.getID() + " ");
                                    String status = complaint.getState().getClass().getSimpleName();
                                    System.out.println("Status: " + status);
                                    System.out.println();
                                    System.out.println();
                                }
                                System.out.println();
                                System.out.println("Accounts Department Summary - New Complaints: " + accountsComplaints.size());
                                for (Complaint complaint : accountsComplaints) 
                                {
                                    System.out.print("ComplaintID: " + complaint.getID() + " ");
                                    String status = complaint.getState().getClass().getSimpleName();
                                    System.out.println("Status: " + status);
                                    System.out.println();
                                    System.out.println();
                                }

                            System.out.println("Enter a complaint id from above for which you want to see details.");
                            int cid1 = input.nextInt();

                            Complaint c2 = ComplaintMgmnt.loadComplaintFromSystem(cid1);
                            System.out.println("Description: " + c2.getdetails());
                            System.out.println("Status: " + c2.getState().getClass().getSimpleName());
                            System.out.println("Teacher: " + Administrator.findTeacherByUsername(c2.gettchUsername()).getname());
                            DepartmentName dept = DepartmentName.valueOf(c2.getdept());
                            System.out.println("Manager: " + DepartmentMngmtMain.getDept(dept).getManagerofDept().getName());
                            String[] dates = d.getNewAssignedDates(cid1);
                            System.out.println("Complaint Date: " + dates[0]);
                            System.out.println("Assigned Date: " + dates[1]);

                            if(dates[1] != null)
                            {
                                //find employees who were assigned this 
                                List<String> assignedEmployees = d.getAssignedEmployees(cid1);
                                System.out.print("Assigned Employees: ");

                                for (String employeeUsername : assignedEmployees) 
                                {
                                    Employee employee = Administrator.findEmployeeByUsername(employeeUsername);
                                    System.out.print(employee.getName() + " ");
                                }
                            }


                        
                        }
                        //from here specific interface will be opened 
                        System.exit(0);
                    }
                    else 
                    {
                        System.out.println("Invalid credentials. Try again.");
                    }
                    break;
                    
            }

            else if (check == 2)
            {
                    System.out.println("Exiting the Complaint Management System. Goodbye!");
                    System.exit(0);
            }

            else
            {
                 System.out.println("Invalid option. Please choose a valid option.");
            }
            
            input.close();

        }
    }

    public static String[] getTeachDeetsBasedOnUName(String targetName) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("Teachers.txt"))) {
            String line = bufreader.readLine();
            String[] chunk = null; // Declare chunk outside the loop

            while (line != null) {
                chunk = line.split(":");
                if (chunk[0].equals(targetName)) {
                    break;
                }
                
                line = bufreader.readLine(); // Move to the next line
                chunk = null; // Reset chunk if the name doesn't match
            }
    
            return chunk != null ? chunk : new String[0]; // Return empty array if no match is found
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return new String[0]; // Handle exception by returning an empty array
            }
    }

    public static String[] getDirectorDeetsBasedOnUName(String targetName) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("Director.txt"))) {
            String line = bufreader.readLine();
            String[] chunk = null; // Declare chunk outside the loop

            while (line != null) {
                chunk = line.split(":");
                if (chunk[0].equals(targetName)) {
                    break;
                }
                
                line = bufreader.readLine(); // Move to the next line
                chunk = null; // Reset chunk if the name doesn't match
            }
    
            return chunk != null ? chunk : new String[0]; // Return empty array if no match is found
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return new String[0]; // Handle exception by returning an empty array
            }
    }

    public static String[] getMngrDeetsBasedOnUName(String targetName) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("Managers.txt"))) {
            String line = bufreader.readLine();
            String[] chunk = null; // Declare chunk outside the loop

            while (line != null) {
                chunk = line.split(":");
                if (chunk[0].equals(targetName)) {
                    break;
                }
                
                line = bufreader.readLine(); // Move to the next line
                chunk = null; // Reset chunk if the name doesn't match
            }
    
            return chunk != null ? chunk : new String[0]; // Return empty array if no match is found
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return new String[0]; // Handle exception by returning an empty array
            }
    }

    public static String[] getEmpDeetsBasedOnUName(String targetName) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("Employees.txt"))) {
            String line = bufreader.readLine();
            String[] chunk = null; // Declare chunk outside the loop

            while (line != null) {
                chunk = line.split(":");
                if (chunk[0].equals(targetName)) {
                    break;
                }
                
                line = bufreader.readLine(); // Move to the next line
                chunk = null; // Reset chunk if the name doesn't match
            }
    
            return chunk != null ? chunk : new String[0]; // Return empty array if no match is found
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return new String[0]; // Handle exception by returning an empty array
            }
    }

}
