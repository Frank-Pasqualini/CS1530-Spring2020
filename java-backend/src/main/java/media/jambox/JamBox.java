package media.jambox;

import java.util.ArrayList;

public class JamBox
{
    private static JamBox singleInstance;
    public ArrayList<Event> eventList;
    public int numEvent;

    // constructor
    private JamBox()
    {
        this.eventList = new ArrayList<Event>();
        this.numEvent = eventList.size();
    }

    // static method to create instance of JamBox

    /**
     * checks to see if an instance of JamBox exists, and if not, creates one.
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
