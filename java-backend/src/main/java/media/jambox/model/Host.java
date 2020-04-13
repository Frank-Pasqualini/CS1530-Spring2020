package media.jambox.model;

import java.security.InvalidKeyException;

public class Host
    extends User
{
    public Host(String id, Event event)
    {
        super(id, event);
    }

    @Override
    public void disconnect()
        throws IllegalCallerException
    {
        throw new IllegalCallerException("The host cannot disconnect from its own event.");
    }

    public void endEvent(String accessToken)
        throws InvalidKeyException
    {
        event.deleteEvent(accessToken);
    }

    public void removeTrack(String trackId, String accessToken)
        throws InvalidKeyException
    {
        event.getQueue().removeTrack(trackId, accessToken);
    }
}
