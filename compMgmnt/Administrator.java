import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends User
{
    private static final String TEACHERS_FILE = "Teachers.txt";
    private static final String MANAGERS_FILE = "Managers.txt";
    private static final String EMPLOYEES_FILE = "Employees.txt";
    private static final String DIRECTOR_FILE = "Director.txt";
    //private List<User> totalUsers;

    public Administrator(String username, String password)
    {
        super(username, password, "Administrator");
        //totalUsers = new ArrayList<>();
    }

    public boolean AddTeacher(String username, String password, String name, String dept, String subject)
    {
        File file = new File(TEACHERS_FILE);
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

        List<Teacher> tchs = loadTeachers();
        boolean check = checkusername(tchs, username);

        if(check == false)
        {
            Teacher t = new Teacher(username, password, name, subject, dept);
            try (BufferedWriter fw = new BufferedWriter(new FileWriter(TEACHERS_FILE, true))) 
            {
                fw.write(t.getUsername() + ":" + t.getPassword() + ":" + t.getname() + ":" + t.getSubject() + ":" + t.getDept());
                fw.newLine();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
            addForAuthentication(t.getUsername(), t.getPassword(), "Teacher", true);
            return true;
        }

        else
        {
            return false;
        }

    }

    public boolean AddDirector(String username, String password, String name)
    {
        File file = new File(DIRECTOR_FILE);
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

        List<Director> tchs = loadDirector();
        if(tchs.size() == 0)
        {
            boolean check = checkusername(tchs, username);

            if(check == false)
            {
                Director d = new Director(username, password, name);
                try (BufferedWriter fw = new BufferedWriter(new FileWriter(DIRECTOR_FILE))) 
                {
                    fw.write(d.getUsername() + ":" + d.getPassword() + ":" + d.getName());
                    fw.newLine();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
                
                addForAuthentication(d.getUsername(), d.getPassword(), "Director", true);
                return true;
            }

            else
            {
                return false;
            }
        }

        else
        {
            //System.out.println("There is already a director. You can not enter another director.");
            return false;
        }

    }

    public boolean addManager(String username, String password, String name, String deptname)
    {
        DepartmentName dept = DepartmentName.valueOf(deptname);
        File file = new File(MANAGERS_FILE);
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

        List<Manager> mngrs = loadManagers(); 

        if(mngrs.size() < 3)
        {
            boolean check = checkusername(mngrs, username);

            if(check == false)
            {
                Manager t =  new Manager(username, password, name, DepartmentMngmtMain.getDept(dept));
                DepartmentMngmtMain.getDept(dept).setManager(t);
                try (BufferedWriter fw = new BufferedWriter(new FileWriter(MANAGERS_FILE, true))) 
                {
                    fw.write(t.getUsername() + ":" + t.getPassword() + ":" + t.getName() + ":" + deptname);
                    fw.newLine();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
                
                addForAuthentication(t.getUsername(), t.getPassword(), "Manager", true);
                return true;
            }

            else
            {
                return false;
            }
        }

        else
        {
            return false;
        }

    }

    public boolean AddEmployee(String username, String password, String name, String deptname)
    {
        DepartmentName dept = DepartmentName.valueOf(deptname);
        File file = new File(EMPLOYEES_FILE);
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

        List<Employee> emp = loadEmployees(); 
        boolean check = checkusername(emp, username);

        if(check == false)
        {

            Employee t =  new Employee(username, password, name, DepartmentMngmtMain.getDept(dept));
            DepartmentMngmtMain.getDept(dept).getEmployees().add(t);
            try (BufferedWriter fw = new BufferedWriter(new FileWriter(EMPLOYEES_FILE, true))) 
            {
                fw.write(t.getUsername() + ":" + t.getPassword() + ":" + t.getName() + ":" + deptname);
                fw.newLine();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
            addForAuthentication(t.getUsername(), t.getPassword(), "Employee", true);
            return true;
        }

        else
        {
            return false;
        }

    }


    public static List<Teacher> loadTeachers() 
    {
        List<Teacher> tch = new ArrayList<>();
    
        try (BufferedReader bufreader = new BufferedReader(new FileReader(TEACHERS_FILE))) 
        {
            String data_line = bufreader.readLine();
    
            while (data_line != null) 
            {
                String[] chunk = data_line.split(":");
                if (chunk.length >= 5) 
                {
                    // System.out.println(chunk[0]);
                    // System.out.println(chunk[1]);
                    // System.out.println(chunk[2]);
                    // System.out.println(chunk[3]);
                    // System.out.println(chunk[4]);
    
                    Teacher t = new Teacher(chunk[0], chunk[1], chunk[2], chunk[3], chunk[4]);
                    tch.add(t);
                } 
                else 
                {
                   System.out.println("the array doesn't have enough elements");
                   System.exit(0);
                }
                data_line = bufreader.readLine();  // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tch;
    }

    public static List<Director> loadDirector() 
    {
        List<Director> tch = new ArrayList<>();
    
        try (BufferedReader bufreader = new BufferedReader(new FileReader(DIRECTOR_FILE))) 
        {
            String data_line = bufreader.readLine();
    
            while (data_line != null) 
            {
                String[] chunk = data_line.split(":");
                if (chunk.length >= 3) 
                {
                    // System.out.println(chunk[0]);
                    // System.out.println(chunk[1]);
                    // System.out.println(chunk[2]);
                    // System.out.println(chunk[3]);
                    // System.out.println(chunk[4]);
    
                    Director d = new Director(chunk[0], chunk[1], chunk[2]);
                    tch.add(d);
                } 
                else 
                {
                   System.out.println("the array doesn't have enough elements");
                   System.exit(0);
                }
                data_line = bufreader.readLine();  // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tch;
    }

    public static Teacher findTeacherByUsername(String u)
     {
        List<Teacher> t = loadTeachers();
        for (Teacher tch : t) 
        {
            if (tch.getUsername().equals(u)) 
            {
                return tch;
            }
        }
        return null;
    }

    public static Employee findEmployeeByUsername(String u)
     {
        List<Employee> t = loadEmployees();
        for (Employee tch : t) 
        {
            if (tch.getUsername().equals(u)) 
            {
                return tch;
            }
        }
        return null;
    }

    public static List<Manager> loadManagers() 
    {
        List<Manager> m = new ArrayList<>();
    
        try (BufferedReader bufreader = new BufferedReader(new FileReader(MANAGERS_FILE))) 
        {
            String data_line = bufreader.readLine();
    
            while (data_line != null) 
            {
                String[] chunk = data_line.split(":");
                if (chunk.length == 4) 
                {
                    // System.out.println(chunk[0]);
                    // System.out.println(chunk[1]);
                    // System.out.println(chunk[2]);
                    // System.out.println(chunk[3]);

                    DepartmentName dept = DepartmentName.valueOf(chunk[3]);
                    Manager mg = new Manager(chunk[0], chunk[1], chunk[2], DepartmentMngmtMain.getDept(dept));
                    m.add(mg);
                } 
                else 
                {
                   System.out.println("the array doesn't have enough elements");
                   System.exit(0);
                }
                data_line = bufreader.readLine();  // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return m;
    }

    public static List<Employee> loadEmployees() 
    {
        List<Employee> em = new ArrayList<>();
    
        try (BufferedReader bufreader = new BufferedReader(new FileReader(EMPLOYEES_FILE))) 
        {
            String data_line = bufreader.readLine();
    
            while (data_line != null) 
            {
                String[] chunk = data_line.split(":");
                if (chunk.length == 4) 
                {
                    // System.out.println(chunk[0]);
                    // System.out.println(chunk[1]);
                    // System.out.println(chunk[2]);
                    // System.out.println(chunk[3]);

                    DepartmentName dept = DepartmentName.valueOf(chunk[3]);
                    Employee emp = new Employee(chunk[0], chunk[1], chunk[2], DepartmentMngmtMain.getDept(dept));
                    em.add(emp);
                } 
                else 
                {
                   System.out.println("the array doesn't have enough elements");
                   System.exit(0);
                }
                data_line = bufreader.readLine();  // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return em;
    }

    private <T extends User> boolean checkusername(List<T> userList, String username) 
    {
        for (T user : userList) 
        {
            if (user.getUsername().equals(username)) 
            {
                return true;
            }
        }
        return false;
   }

    private void addForAuthentication(String username, String password, String Designation, boolean appendCheck)   //adding in UA.txt file for user Authentication
    {
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(userAuthentication.getUsersFilePath(), appendCheck))) 
            {
                fw.write(username + ":" + password + ":" + Designation);
                fw.newLine();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
    }

    public boolean RemoveTeacherfromAllfiles(String username)
    {
        List<Teacher> tch = loadTeachers();

        for (int i = 0; i < tch.size(); i++) 
        {
            Teacher teacher = tch.get(i);
            if (teacher.getUsername().equals(username)) 
            {
                tch.remove(teacher);
                writeAllTeachers(tch);
                removefromUser(username);
                return true;
            }
        }

        return false;    ///if the teacher does not exist 
    }

    public boolean RemoveDirectorfromAllfiles(String username)
    {
        List<Director> tch = loadDirector();

        for (int i = 0; i < tch.size(); i++) 
        {
            Director d = tch.get(i);
            if (d.getUsername().equals(username)) 
            {
                tch.remove(d);
                // Delete the file associated with the Director
                File fileToDelete = new File(DIRECTOR_FILE);
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }
                // Remove the user from the authentication file
                removefromUser(username);
                return true;
            }
        }
        return false;    ///if the director does not exist 
    }

    public boolean RemoveManagerfromAllfiles(String username)
    {
        List<Manager> mg = loadManagers();

        for (int i = 0; i < mg.size(); i++) 
        {
            Manager manager = mg.get(i);
            if (manager.getUsername().equals(username)) 
            {
                mg.remove(manager);
                writeAllManagers(mg);
                removefromUser(username);
                return true;
            }
        }

        return false;    ///if the teacher does not exist 
    }

    public boolean RemoveEmployeefromAllfiles(String username)
    {
        List<Employee> e = loadEmployees();

        for (int i = 0; i < e.size(); i++) 
        {
            Employee em = e.get(i);
            if (em.getUsername().equals(username)) 
            {
                e.remove(em);
                writeAllEmployees(e);
                removefromUser(username);
                return true;
            }
        }

        return false;    ///if the teacher does not exist 
    }


    private void removefromUser(String username)
    {
        List<User> usr = userAuthentication.loadUserCredentials();

        for(int i=0; i<usr.size(); i++)
        {
            User u = usr.get(i);
             if (u.getUsername().equals(username)) 
            {
                usr.remove(u);
                break;
            }
        }

        User u = usr.get(0);
        addForAuthentication(u.getUsername(), u.getPassword(), u.getDesignation(), false);

        for(int i = 1; i<usr.size(); i++)
        {
            u = usr.get(i);
            addForAuthentication(u.getUsername(), u.getPassword(), u.getDesignation(), true);
        }
    }

    //update the teachers file after removal
    private void writeAllTeachers(List<Teacher> t) 
    {
        try (BufferedWriter bufwriter = new BufferedWriter(new FileWriter(TEACHERS_FILE))) 
        {
            for (int i = 0; i < t.size(); i++) 
            {
                Teacher teacher = t.get(i);
                bufwriter.write(teacher.getUsername() + ":" + teacher.getPassword() + ":" + teacher.getname()+ ":" + teacher.getSubject() + ":" + teacher.getDept());
                bufwriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllManagers(List<Manager> mg) 
    {
        try (BufferedWriter bufwriter = new BufferedWriter(new FileWriter(MANAGERS_FILE))) 
        {
            for (int i = 0; i < mg.size(); i++) 
            {
                Manager manager = mg.get(i);
                String s = String.valueOf(manager.gettDept());
                bufwriter.write(manager.getUsername() + ":" + manager.getPassword() + ":" + manager.getName()+ ":" + s);
                bufwriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllEmployees(List<Employee> em) 
    {
        try (BufferedWriter bufwriter = new BufferedWriter(new FileWriter(EMPLOYEES_FILE))) 
        {
            for (int i = 0; i < em.size(); i++) 
            {
                Employee employee = em.get(i);
                String s = String.valueOf(employee.gettDept());
                bufwriter.write(employee.getUsername() + ":" + employee.getPassword() + ":" + employee.getName()+ ":" + s);
                bufwriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
