public class User 
{
    private String username;
    private String password;
    private String designation; 

    public User (String username, String password, String designation) 
    {
        this.username = username;
        this.password = password;
        this.designation = designation;
    }

    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }

    public String getDesignation() 
    {
        return designation;
    }
}
