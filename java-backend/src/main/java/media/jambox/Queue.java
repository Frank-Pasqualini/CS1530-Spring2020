import jambox.media

public class Queue
{
	private Track[] track_list; // ArrayList

	Queue()
	{
		this.track_list = new Track[];
	}

	public Track[] sort_and_display()
	{
		return track_list;
	}

	public Track pop()
	{
		return track_list.remove(0);
	}

	public Track[] append(Track track)
	{
		return track_list.add(track);
	}

	public Track remove(String track_id)
	{
		return track_list.remove(track_id);
	}
