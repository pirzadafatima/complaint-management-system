import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

public class Teacher extends User implements Observer
{
    private String name;
    private String subject;
    private String dept;
    private List<Complaint> complaints;

    public Teacher(String username, String password, String name, String subject, String dept) 
    {

        super(username, password, "Teacher");
        this.complaints = new ArrayList<>();
        this.name = name;
        this.subject = subject;
        this.dept = dept;
    }

    public String getname()
    {
        return name;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getDept()
    {
        return dept;
    }

    public List<Complaint> getFiledComplaints()
    {
        return complaints;
    }

    public void fileComplaint(String type, String details, String tchUsername, String dept) 
    {
        Complaint c = new Complaint(ComplaintMgmnt.getPrevCId() + 1, type, details, this.getUsername(), dept);
        ComplaintMgmnt.saveToFile(ComplaintMgmnt.getPrevCId() + 1, type, details, this.getUsername(), dept, "no solution", true);
        complaints.add(c);
        DepartmentName deptName = DepartmentName.valueOf(dept);
        SupportingDept s = DepartmentMngmtMain.getDept(deptName);
        s.getDeptComplaints().add(c);       //whatever dept teacher wants to forward to its instance will be used to add in the complaints list of that dept
        //s.sendNotificationToManager(c);

    }

    void LoadFiledComplaints() 
    {
        List <Complaint> allComps = ComplaintMgmnt.loadFromFile();
        for (int i = 0; i < allComps.size(); i++) 
        {
            Complaint c = allComps.get(i);
            if (c.gettchUsername().equals(this.getUsername())) 
            {
                complaints.add(c);
            }
        }
    }

    @Override
    public void update(Event eventType, Complaint c)
    {
        //save all the complaints ID, solution and teacherUsername in notification file 
        //when teacher logs in show from notification file 
        
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
        List<String> tchrNotifications = new ArrayList<>();
    
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
    
                    // Check if the complaint is in ResolvedState so that old notifications saved in file are not included everytime 
                    if (complaint != null && complaint.getState() instanceof ResolvedState) 
                    {
                        String teacherNotification = parts[1] + " " + parts[2] + " " + parts[3];
    
                        tchrNotifications.add(teacherNotification);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return tchrNotifications;
    }

    // Helper method to find a complaint by ID
    private Complaint findComplaintById(int complaintID)
     {
        for (Complaint complaint : complaints) 
        {
            if (complaint.getID() == complaintID) 
            {
                return complaint;
            }
        }
        return null;
    }

    public void reviewComplaintSolution(int Cid, boolean satisfied)
    {
        Complaint c = findComplaintById(Cid);

        if(satisfied)
        {
            //COMPLAINT WILL BE CLOSED. FIANLYYYYYY !!!!!!!! <Life cycle of complaint is complete>
            
            c.close(c);
        }

        else
        {
            //again mark is at a new complaint for another solution <Life cycle reactivated>   :'(
            c.newState(c);
            c.setSolution("no solution");
            ComplaintMgmnt.updateComplaintInFile(c);
        }
    }

}
