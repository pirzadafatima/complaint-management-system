import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Director extends User
{
    private String name;

    public Director(String username, String password, String name) 
    {
        super(username, password, "Director");
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public List<Complaint> viewComplaintSummary(Date startDate, Date endDate) 
    {
        List<Complaint> filteredComplaints = new ArrayList<>();
    
        try (BufferedReader bufreader = new BufferedReader(new FileReader("complaint_state_file.txt"))) 
        {
            String line = bufreader.readLine();
            while (line != null) 
            {
                String[] complaintInfo = line.split(":");
                int complaintID = Integer.parseInt(complaintInfo[0]);
                String status = complaintInfo[1];
                Date complaintDate = parseDate(complaintInfo[2]);
                //DepartmentName complaintDepartment = DepartmentName.valueOf(complaintInfo[3]);
    
                // Check if the status is "NewState" and the complaint date is within the specified interval
                if (status.equals("NewState") && isDateWithinInterval(complaintDate, startDate, endDate))
                 {
                    // Check if a complaint with the same ID already exists in the list
                    boolean complaintExists = false;
                    for (Complaint existingComplaint : filteredComplaints) {
                        if (existingComplaint.getID() == complaintID) 
                        {
                            // Replace the existing complaint with the new one
                            existingComplaint = ComplaintMgmnt.loadComplaintFromSystem(complaintID);
                            complaintExists = true;
                            break;
                        }
                    }
    
                    // If the complaint doesn't exist in the list, add it
                    if (!complaintExists) {
                        Complaint comp = ComplaintMgmnt.loadComplaintFromSystem(complaintID);
                        filteredComplaints.add(comp);
                    }
                }
    
                line = bufreader.readLine(); // Move to the next line
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        return filteredComplaints;
    }
    

    private boolean isDateWithinInterval(Date date, Date startDate, Date endDate) 
    {
        return date.after(startDate) && date.before(endDate);
    }

    public Date parseDate(String dateStr) 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            // Handle the exception (print or log an error, or take appropriate action)
            e.printStackTrace();
            // Return a default value or throw a runtime exception, depending on your requirements
            return null; // Replace this with your desired error handling strategy
        }
    }

    public String[] getNewAssignedDates(int complaintID) 
    {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("complaint_state_file.txt"))) 
        {
            String line = bufreader.readLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] dates = new String[2]; // [0]: NewState date, [1]: AssignedState date

            while (line != null) 
            {
                String[] complaintInfo = line.split(":");
                int currentID = Integer.parseInt(complaintInfo[0]);
                String status = complaintInfo[1];
                Date complaintDate = parseDate(complaintInfo[2]);

                if (currentID == complaintID) 
                {
                    if (status.equals("NewState")) 
                    {
                        dates[0] = dateFormat.format(complaintDate);
                    } 
                    else if (status.equals("AssignedState")) 
                    {
                        dates[1] = dateFormat.format(complaintDate);
                    }
                }

                line = bufreader.readLine();
            }

            if (dates[0] == null) {
                System.out.println("Complaint ID " + complaintID + " has no 'NewState' entry.");
            }

            if (dates[1] == null) {
                System.out.println("Complaint ID " + complaintID + " has no 'AssignedState' entry.");
            }

            return dates;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new String[2]; // Handle exceptions by returning an empty array
        }
    }

    public List<String> getAssignedEmployees(int complaintID) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("AssignedComplaints.txt"))) 
        {
            String line = bufreader.readLine();
            List<String> assignedEmployees = new ArrayList<>();

            while (line != null) 
            {
                String[] assignmentInfo = line.split(":");
                int currentID = Integer.parseInt(assignmentInfo[0]);

                if (currentID == complaintID && assignmentInfo.length > 1) 
                {
                    // Add all assigned employee usernames to the list
                    assignedEmployees.addAll(Arrays.asList(assignmentInfo).subList(1, assignmentInfo.length));   //converts it into a list, extracts a sub-list starting from index 2 to the end of the array, and then adds all elements from that sub-list to the assignedEmployees list.
                    //break; // No need to continue reading if the complaint ID is found
                }

                line = bufreader.readLine();
            }

            if (assignedEmployees.isEmpty()) 
            {
                System.out.println("Complaint ID " + complaintID + " has no assigned employees.");
            }

            return assignedEmployees;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>(); // Handle exceptions by returning an empty list
        }
    }

  
}
