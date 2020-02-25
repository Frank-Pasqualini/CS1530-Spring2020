package media.jambox;
import java.util.*;

public class Event {

	private ArrayList<User> users;
	private Playlist event_playlist;
	private Queue event_queue;
	private Track now_playing;
	private int event_code;

	Event(int event_code, String playlist_id)
	{
		this.users = new ArrayList<User>();
		this.event_playlist = new Playlist(playlist_id);
		this.event_queue = new Queue();
		this.now_playing = event_queue.pop();
		this.event_code = event_code;
	}

	public ArrayList<User> add_user(String user_id)
	{
		users.add(new Guest(user_id));
		return users;
	}

	public String remove_user(String user_id)
	{
		for(int i = 0; i < users.size() - 1; i++)
		{
			if (users.get(i).getId() == user_id)
			{
				users.remove(i);
				return user_id;
			}
		}
		return "[ERROR] could not find user to removed";
	}

	public Playlist get_playlist()
	{
		return event_playlist;
	}

	public Queue get_queue()
	{
		return event_queue;
	}

	public Track get_now_playing()
	{
		return now_playing;
	}

	public Track set_now_playing(Track track)
	{
		now_playing = track;
		return now_playing;
	}

	public int get_event_code()
	{
		return event_code;
	}
}
