package media.jambox.model;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
     * @throws IOException if wrong code/ID input
     * @throws SpotifyWebApiException if wrong access token
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static int addEvent(String playlistId, String accessToken, String hostId, int eventCode, Event event)
        throws IOException, SpotifyWebApiException
    {
        Random rand = new java.util.Random();

        if (eventCode == -1)
        {
            eventCode = rand.nextInt(10000);
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
     * @param eventId The ID of the event to check.
     *
     * @return The event specified by the ID.
     */
    public static Event getEvent(int eventId)
    {
        System.out.println("eventId: " + eventId);
        for (int i = 0; i < singleInstance.eventList.size(); i++)
        {
            if (singleInstance.eventList.get(i).getEventCode() == eventId)
            {
                return singleInstance.eventList.get(i);
            }
        }
        throw new InputMismatchException();
    }

    /**
     * Remove an event by it's ID.
     *
     * @param eventId The ID of the event to remove.
     *
     */
    public static void removeEvent(int eventId)
    {
        for (int i = 0; i < singleInstance.eventList.size(); i++)
        {
            if (singleInstance.eventList.get(i).getEventCode() == eventId)
            {
                singleInstance.eventList.remove(i);
                return;
            }
        }
        throw new InputMismatchException();
    }
}
