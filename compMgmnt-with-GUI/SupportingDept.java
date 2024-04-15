import java.util.ArrayList;
import java.util.List;

public class SupportingDept 
{
    private DepartmentName name;
    private Manager m;
    private List<Employee> emp; 
    private List<Complaint> filedComplaints;


    SupportingDept(DepartmentName name)
    {
        this.name = name;
        emp = new ArrayList<>();
        filedComplaints = new ArrayList<>();
        emp = new ArrayList<>();
    }

    public DepartmentName  getDeptName()
    {
        return name;
    }

    public void setManager(Manager m)
    {
        this.m = m;
    }

    List<Complaint> getDeptComplaints()
    {
        return filedComplaints;
    }

    List<Employee> getEmployees()
    {
        return emp;
    }

    // public void sendNotificationToManager(Complaint c)
    // {
    //     m.getNewComplaintNotification(c);
    // }

    public void setEmployees()
    {
        List<Employee> e = Administrator.loadEmployees();

        for(Employee employee: e)
        {
            if(employee.gettDept().equals(name))
            {
                emp.add(employee);
            }
        }
    }

    public void sManager()
    {
        List<Manager> m = Administrator.loadManagers();

        for(Manager manager: m)
        {
            if(manager.gettDept().equals(name))
            {
                setManager(manager);
            }
        }
    }

    public void setComplaints()
    {
        List<Complaint> c = ComplaintMgmnt.loadFromFile();

        for(Complaint complaint: c)
        {
            if(complaint.getdept().equals(String.valueOf(name)))
            {
                filedComplaints.add(complaint);
                //System.out.print("hey");
            }
        }
    }

    public Manager getManagerofDept()
    {
        return m;
    }


}

enum DepartmentName 
{
    IT,
    Accounts,
    Admin;
}