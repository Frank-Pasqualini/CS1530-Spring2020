import jambox.media

public class JamBox
{
	private static JamBox single_instance;
	public Event[] event_list; // ArrayList
	public int num_event;

	// constructor
	private JamBox()
	{
		this.event_list = new Event[];
		this.num_event = event_list.size();
	}

	// static method to create instance of JamBox
	public static JamBox getInstance()
	{
		if(single_instance == null)
		{
			single_instance = new JamBox();
		}
		return single_instance;
	}
}
