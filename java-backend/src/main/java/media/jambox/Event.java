package media.jambox

public class Event{

private User[] users; //List<String> users = new ArrayList<String>();
private Playlist event_playlist;
private Queue event_queue;
private Track now_playing;
private int event_code;

Event(int event_code, int playlist_id)
{
	this.users = new User[];
	this.event_playlist = new Playlist(playlist_id);
	this.event_queue = new Queue;
	this.now_playing = event_playlist[0];
	this.event_code = event_code;
}

public User[] add_user(string user_id)
{
	users.add(user_id);
	return users;
}

public User remove_user(string user_id)
{
	users.remove(user_id);
	return user_id;
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
