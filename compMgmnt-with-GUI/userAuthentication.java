import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class userAuthentication 
{
    private static final String USERS_FILE = "UA.txt";
    private List<User> userCredentials;

    public userAuthentication() //constructor 
    {
        File file = new File(USERS_FILE);
        if (!file.exists()) 
        {
            try {
                file.createNewFile();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.userCredentials = loadUserCredentials();
    }

    public static String getUsersFilePath() 
    {
        return USERS_FILE;
    }

    public static List<User> loadUserCredentials() 
    {
        List<User> credentials = new ArrayList<>();

        try(BufferedReader bufreader = new BufferedReader(new FileReader(USERS_FILE)))
        {
            String line = bufreader.readLine();

            while (line != null) 
            {
                String[] chunk = line.split(":");

                if(chunk.length == 3)
                {
                    credentials.add(new User(chunk[0], chunk[1], chunk[2]));
                }
                else
                {
                    System.out.println("Does not have enough parameters.");
                }
                
                line = bufreader.readLine();  // Move to the next line
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return credentials;
    }

    public String logIn(String username, String password) 
    {
        for (int i = 0; i < userCredentials.size(); i++) 
        {
            User cred = userCredentials.get(i);
            if (cred.getUsername().equals(username) && cred.getPassword().equals(password)) {
                return cred.getDesignation();
            }
        }
        return null;
    }
}
