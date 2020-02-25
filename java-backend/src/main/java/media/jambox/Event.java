package media.jambox;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Event
{

    private final ArrayList<User> users;
    private final Playlist eventPlaylist;
    private final Queue eventQueue;
    private Track nowPlaying;
    private final int eventCode;

    Event(int eventCode, String playlistId, String accessToken, String hostId)
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        this.users = new ArrayList<>();
        users.add(new Host(hostId));

        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(hostId, "JamBox: " + dtf.format(now)).build();
        final com.wrapper.spotify.model_objects.specification.Playlist playlist = createPlaylistRequest.execute();
        this.eventPlaylist = new Playlist(playlist.getId());

        //TODO initialize Queue with playlistId
        this.eventQueue = new Queue();
        this.nowPlaying = eventQueue.pop();
        this.eventPlaylist.append(nowPlaying.getId(), accessToken);
        this.eventCode = eventCode;
    }

    public ArrayList<User> addGuest(String userId)
    {
        users.add(new Guest(userId));
        return users;
    }

    /**
     * Removes a User via a userID string input from the user ArrayList.
     *
     * @param userId A string input of a userId.
     *
     * @return The User that was removed.
     */
    public User removeUser(String userId)
        throws InputMismatchException
    {
        for (int i = 0; i < users.size(); i++)
        {
            if (users.get(i).getId().equals(userId))
            {
                return users.remove(i);
            }
        }

        throw new InputMismatchException();
    }

    public Playlist getPlaylist()
    {
        return eventPlaylist;
    }

    public Queue getQueue()
    {
        return eventQueue;
    }

    public Track getNowPlaying()
    {
        return nowPlaying;
    }

    public Track setNowPlaying(Track track)
    {
        nowPlaying = track;
        //TODO add to playlist.
        return nowPlaying;
    }

    public int getEventCode()
    {
        return eventCode;
    }
}
