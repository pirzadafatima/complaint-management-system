///import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService 
{
    private final Map<Event, List<Observer>> observers;

    public NotificationService() 
    {
        observers = new HashMap<>();
        Arrays.stream(Event.values()).forEach(event -> observers.put(event, new ArrayList<>()));
    }

    public void subscribe(Event eventType, Observer o) 
    {
        observers.get(eventType).add(o);
    }

    public void unsubscribe(Event eventType, Observer o) 
    {
        observers.get(eventType).remove(o);
    }

    public void notifyObservers(Event eventType, Complaint c) 
    {
        observers.get(eventType).forEach(observer -> observer.update(eventType, c));
    }
    
}
