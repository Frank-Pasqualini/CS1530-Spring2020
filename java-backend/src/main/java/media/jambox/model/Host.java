package media.jambox.model;

public class Host
    extends User
{
    public Host(String id, Event event)
    {
        super(id, event);
    }

    @Override
    public void disconnect()
    {
        //The host cannot disconnect.
    }

    public void endEvent(String accessToken)
    {
        event.deleteEvent(accessToken);
    }
}
