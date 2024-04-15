
public class DepartmentMngmtMain {   //Factory design pattern
    public static SupportingDept itDepat = new SupportingDept(DepartmentName.IT);
    public static SupportingDept adminDepat = new SupportingDept(DepartmentName.Admin);
    public static SupportingDept accountDepat = new SupportingDept(DepartmentName.Accounts);


    public static SupportingDept getDept(DepartmentName deptname)
    {
        if (deptname == DepartmentName.IT) {
            return itDepat;
        }

        else if (deptname == DepartmentName.Admin) {
            return adminDepat;
        }

        else 
            return accountDepat;
        
    }
    
}
