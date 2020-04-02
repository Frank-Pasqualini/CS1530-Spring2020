package media.jambox;

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
}
