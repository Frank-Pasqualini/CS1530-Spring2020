package media.jambox.model;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class JamBox
{
    private static JamBox singleInstance;
    private final transient ArrayList<Event> eventList;

    private JamBox()
    {
        this.eventList = new ArrayList<>();
    }

    /**
     * Checks to see if an instance of JamBox exists, and if not, creates one.
     *
     * @return singleInstance
     */
    public static JamBox getInstance()
    {
        if (singleInstance == null)
        {
            singleInstance = new JamBox();
        }
        return singleInstance;
    }

    /**
     * Generates a random that is not the same as another running event.
     * If there are 10000 current events, it spin-locks until enough close that it can randomly find an open event.
     *
     * @param playlistId id for playlist
     * @param accessToken access token
     * @param hostId id for host
     * @param eventCode Override for the randomly generated event code.
     * @param event Override for the generated event.
     *
     * @throws IllegalArgumentException If the specified event code is not in the valid range of event codes
     * @throws InvalidKeyException If the User is not a Spotify Premium member.
     * @throws IOException If wrong code/ID input
     * @throws SpotifyWebApiException if wrong access token
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static int addEvent(String playlistId, String accessToken, String hostId, int eventCode, Event event)
        throws IllegalArgumentException, InvalidKeyException, IOException, SpotifyWebApiException
    {
        Random rand = new java.util.Random();

        if (eventCode == -1)
        {
            eventCode = rand.nextInt(10000);
        }
        else if (eventCode < -1 || eventCode > 9999)
        {
            throw new IllegalArgumentException("Event codes must be in the range 0000-9999. -1 is the special case for non-mocked events to generate a code.");
        }

        for (int i = 0; i < singleInstance.eventList.size(); i++)
        {
            if (singleInstance.eventList.get(i).getEventCode() == eventCode)
            {
                eventCode = rand.nextInt(10000);
                i = 0;
            }
        }

        if (event == null)
        {
            event = new Event(eventCode, playlistId, accessToken, hostId, null, null);
        }
        singleInstance.eventList.add(event);
        return eventCode;
    }

    /**
     * Gets the information of an event by it's ID.
     *
     * @param eventCode The ID of the event to check.
     *
     * @return The event specified by the ID.
     *
     * @throws IllegalArgumentException If the specified event code is not in the valid range of event codes.
     * @throws NoSuchElementException If the specified event code is not a valid event.
     */
    public static Event getEvent(int eventCode)
        throws IllegalArgumentException, NoSuchElementException
    {
        if (eventCode < 0 || eventCode > 9999)
        {
            throw new IllegalArgumentException("Event codes must be in the range 0000-9999.");
        }

        for (int i = 0; i < singleInstance.eventList.size(); i++)
        {
            if (singleInstance.eventList.get(i).getEventCode() == eventCode)
            {
                return singleInstance.eventList.get(i);
            }
        }

        throw new NoSuchElementException("The event with code " + eventCode + " does not exist.");
    }

    /**
     * Remove an event by it's ID.
     *
     * @param eventCode The ID of the event to remove.
     *
     * @throws IllegalArgumentException If the specified event code is not in the valid range of event codes.
     * @throws NoSuchElementException If the specified event code is not a valid event.
     */
    public static void removeEvent(int eventCode)
        throws IllegalArgumentException, NoSuchElementException
    {
        if (eventCode < 0 || eventCode > 9999)
        {
            throw new IllegalArgumentException("Event codes must be in the range 0000-9999.");
        }

        for (int i = 0; i < singleInstance.eventList.size(); i++)
        {
            if (singleInstance.eventList.get(i).getEventCode() == eventCode)
            {
                singleInstance.eventList.remove(i);
                return;
            }
        }
        throw new NoSuchElementException("The event with code " + eventCode + " does not exist.");
    }
}
