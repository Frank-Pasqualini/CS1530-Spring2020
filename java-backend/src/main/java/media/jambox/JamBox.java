package media.jambox;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.ArrayList;

public class JamBox
{
    private static JamBox singleInstance;
    public final transient ArrayList<Event> eventList;
    public final transient int numEvent;

    private JamBox()
    {
        this.eventList = new ArrayList<>();
        this.numEvent = eventList.size();
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

    public static JamBox addEvent(int eventCode, String playlistId, String accessToken, String hostId)
        throws IOException, SpotifyWebApiException
    {
        Event event = new Event(eventCode, playlistId, accessToken, hostId);
        singleInstance.eventList.add(event);
        return singleInstance;
    }
}
