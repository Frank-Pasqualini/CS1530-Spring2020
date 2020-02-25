package media.jambox;
import java.util.*;

public class Queue {
	private ArrayList<Track> track_list; // ArrayList

	Queue() {
		this.track_list = new ArrayList<Track>();
	}

	public ArrayList<Track> sort_and_display() {
		return track_list;
	}

	public Track pop() {
		return track_list.remove(0);
	}

	public ArrayList<Track> append(Track track) {
		track_list.add(track);
		return track_list;
	}

	public String remove(String track_id) {
		track_list.remove(track_id);
		return track_id;
	}
}