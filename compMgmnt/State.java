import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class State 
{
    public Complaint complaint;
    public String date;

    public State(Complaint complaint)
    {
        this.complaint = complaint;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = now.format(formatter);
        //System.out.println("I am in state.");
    }

    public String getDate() 
    {
        return date;
    }

    public void saveToFile(String fileName) 
    {
        File file = new File(fileName);
        if (!file.exists()) 
        {
            try {
                file.createNewFile();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Save state details to the file
            writer.write(String.format("%s:%s:%s:%s", complaint.getID(), this.getClass().getSimpleName(), date, complaint.getdept()));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static State convertToState(String stateName, Complaint complaint) 
    {
        switch (stateName) 
        {
            case "NewState":
                return new NewState(complaint, false);  
            case "AssignedState":
                return new AssignedState(complaint, false);  
            case "ResolvedState":
                return new ResolvedState(complaint, false);  
            case "ClosedState":
                return new ClosedState(complaint, false);  
            
            default:
                throw new IllegalArgumentException("Invalid state name: " + stateName);
        }
    }

    public abstract boolean assign(Complaint c);

    public abstract boolean resolve(Complaint c);

    public abstract boolean close(Complaint c);

    public abstract boolean newState(Complaint c);
}
