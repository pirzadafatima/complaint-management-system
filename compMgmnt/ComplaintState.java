
//class to hold complaint state information
public class ComplaintState 
{
    private int complaintID;
    private String state;
    private String date;
    private String dept;

    public ComplaintState(int complaintID, String state, String date, String dept)
     {
        this.complaintID = complaintID;
        this.state = state;
        this.date = date;
        this.dept = dept;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getDept()
    {
        return dept;
    }
}
