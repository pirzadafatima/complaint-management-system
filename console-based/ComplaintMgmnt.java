import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintMgmnt 
{
    private static final String COMPLAINTS_FILE = "Complaints.txt";

    static     //a static block is a set of instructions that is run only once when a class is loaded into memory.
    {
        File file = new File(COMPLAINTS_FILE);
        if (!file.exists()) 
        {
            try 
            {
                file.createNewFile();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }

    }

    public static void saveToFile(int cID, String type, String details, String tch, String dept, String solution, boolean check) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMPLAINTS_FILE, check))) 
        {
            writer.write(cID + ":" + type + ":" + details + ":" + tch + ":" + dept + ":" + solution);
            writer.newLine();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    

    // public static void saveToFile(int cID, String type, String details, String tch, String dept) 
    // {

    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMPLAINTS_FILE, true))) 
    //     {
            
    //         writer.write(cID + ":" + type + ":" + details + ":" + tch + ":" + dept);
    //         writer.newLine();
    //     } 
    //     catch (IOException e) 
    //     {
    //         e.printStackTrace();
    //     }
    // }

    public static List<Complaint> loadFromFile() 
    {
        List<ComplaintState> cs = loadNewStateFromFile();
        List<Complaint> c = new ArrayList<>();
    
        try (BufferedReader bufreader = new BufferedReader(new FileReader(COMPLAINTS_FILE))) 
        {
            String data_line = bufreader.readLine();
    
            while (data_line != null) 
            {
                String[] chunk = data_line.split(":");
                if (chunk.length >= 5) 
                {  // Make sure the array has enough elements for the solution
                    int number = Integer.parseInt(chunk[0]);
                    String state = findMostRecentStates(cs, number);
    
                    // Check if the solution is present
                    String solution = (chunk.length > 5) ? chunk[5] : "no solution";
                    //System.out.println("SOL" + solution);
    
                    Complaint comp = new Complaint(number, chunk[1], chunk[2], chunk[3], chunk[4], false);
                    comp.setSolution(solution);
                    State newS = State.convertToState(state, comp);
                    comp.setState(newS);
                    c.add(comp);
                } else {
                    System.out.println("the array doesn't have enough elements");
                    System.exit(0);
                }
                data_line = bufreader.readLine();  // Move to the next line
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return c;
    }
    

    // public static List<Complaint> loadFromFile() 
    // {
    //     List<ComplaintState> cs = loadNewStateFromFile();
    //     List<Complaint> c = new ArrayList<>();
    
    //     try (BufferedReader bufreader = new BufferedReader(new FileReader(COMPLAINTS_FILE))) 
    //     {
    //         String data_line = bufreader.readLine();
    
    //         while (data_line != null) 
    //         {
    //             String[] chunk = data_line.split(":");
    //             if (chunk.length >= 4) 
    //             {
    //                 int number = Integer.parseInt(chunk[0]);
    //                 System.out.println(chunk[0]);
    //                 System.out.println(chunk[1]);
    //                 System.out.println(chunk[2]);
    //                 System.out.println(chunk[3]);
    //                 System.out.println(chunk[4]);

    //                 String state = findMostRecentStates(cs, number);
    //                 Complaint comp = new Complaint(number, chunk[1], chunk[2], chunk[3], chunk[4], false);
    //                 State newS = State.convertToState(state, comp);
    //                 comp.setState(newS);
    //                 c.add(comp);
    //             } 
    //             else 
    //             {
    //                System.out.println("the array doesn't have enough elements");
    //                System.exit(0);
    //             }
    //             data_line = bufreader.readLine();  // Move to the next line
    //         }
    //     } catch (IOException | NumberFormatException e) {
    //         e.printStackTrace();
    //     }
    //     return c;
    // }

    public static int getPrevCId() 
    {

        try (BufferedReader reader = new BufferedReader(new FileReader("Complaints.txt"))) 
        {
            String line;
            int r_Id = 0;
            while ((line = reader.readLine()) != null) 
            {
                String[] values = line.split(":"); 
                int id = Integer.parseInt(values[0]);
                if (id > r_Id) 
                {
                    r_Id = id;
                }
            }
            return r_Id;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String findMostRecentStates(List<ComplaintState> stateInfoList, int cid) 
    {
        String state = " ";
        // Iterate through the list to find the most recent state for each complaint ID
        for (ComplaintState stateInfo : stateInfoList) 
        {
            if(stateInfo.getComplaintID() == cid)
            {
                state = stateInfo.getState();
            }

        }

        return state;
    }

    public static List<ComplaintState> loadNewStateFromFile() 
    {
        List<ComplaintState> newStateList = new ArrayList<>();

        try (BufferedReader bufreader = new BufferedReader(new FileReader("complaint_state_file.txt"))) 
        {
            String line = bufreader.readLine();

            while (line != null) 
            {
                String[] chunk = line.split(":");
                int complaintID = Integer.parseInt(chunk[0]);
                String stateName = chunk[1];
                String date = chunk[2];
                String dept = chunk[3];

                newStateList.add(new ComplaintState(complaintID, stateName, date, dept));
                line = bufreader.readLine();  // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newStateList;
    }

    public static Complaint loadComplaintFromSystem(int complaintID) 
    {
        List<Complaint> allComplaints = loadFromFile();

        for (Complaint complaint : allComplaints) 
        {
            if (complaint.getID() == complaintID) 
            {
                return complaint;
            }
        }
        return null; // Complaint with the specified ID not found
    }

    public static void updateComplaintInFile(Complaint updatedComplaint) 
    {
        List<Complaint> allComplaints = loadFromFile();
    
        // Find the index of the complaint with the matching ID
        int index = -1;
        for (int i = 0; i < allComplaints.size(); i++) 
        {
            if (allComplaints.get(i).getID() == updatedComplaint.getID()) 
            {
                index = i;
                break;
            }
        }
    
        // Update the complaint in the list
        if (index != -1) 
        {
            allComplaints.set(index, updatedComplaint);
        }

        saveToFile(allComplaints.get(0).getID(), allComplaints.get(0).gettype(), allComplaints.get(0).getdetails(), allComplaints.get(0).gettchUsername(), allComplaints.get(0).getdept(), allComplaints.get(0).getSolution(), false);
    
        // Write all complaints back to the file
        for(int i = 1; i < allComplaints.size(); i++)
        {
            Complaint c = allComplaints.get(i);
            saveToFile(c.getID(), c.gettype(), c.getdetails(), c.gettchUsername(), c.getdept(), c.getSolution(), true);
        }
    }
}