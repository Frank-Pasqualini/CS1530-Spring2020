package media.jambox;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Event
{

    private final transient ArrayList<User> users;
    private final int eventCode;
    private transient Playlist eventPlaylist;
    private transient Queue eventQueue;
    private transient Track nowPlaying;
    private transient Track upNext;

    Event(int eventCode, String playlistId, String accessToken, String hostId)
        throws IOException, InputMismatchException, SpotifyWebApiException
    {
        users = new ArrayList<>();
        users.add(new Host(hostId, this));

        final SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        final LocalDateTime now = LocalDateTime.now();
        final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(hostId, "JamBox: " + dtf.format(now)).build();
        final com.wrapper.spotify.model_objects.specification.Playlist playlist = createPlaylistRequest.execute();
        eventPlaylist = new Playlist(playlist.getId());

        eventQueue = new Queue(playlistId, accessToken);
        try
        {
            nowPlaying = eventQueue.pop();
            upNext = eventQueue.pop();
            eventPlaylist.append(nowPlaying.getId(), accessToken);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new InputMismatchException();
        }

        this.eventCode = eventCode;
    }

    /**
     * Adds a new user to the Users list if it is not already in it.
     *
     * @param userId The ID for the user.
     *
     * @return The list of Users.
     *
     * @throws InputMismatchException Throws an exception if the User is already in the User list.
     */
    public ArrayList<User> addUser(String userId)
        throws InputMismatchException
    {
        User newUser = new User(userId, this);
        if (users.contains(newUser))
        {
            throw new java.util.InputMismatchException();
        }
        users.add(newUser);
        return users;
    }

    /**
     * Removes a User via a userID string input from the user ArrayList.
     *
     * @param userId A string input of a userId.
     *
     * @return The User that was removed.
     *
     * @throws InputMismatchException Throws an exception when the User ID is not a current user.
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

    public Host getHost()
    {
        return (Host)users.get(0);
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

    public Track getUpNext()
    {
        return upNext;
    }

    public int getEventCode()
    {
        return eventCode;
    }
}
