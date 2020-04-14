package media.jambox;

import media.jambox.model.Event;
import media.jambox.model.Host;
import media.jambox.model.JamBox;
import media.jambox.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Controller
{
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/add_event")
    public int newEvent(@RequestParam("playlistId") String playlistId, @RequestParam("accessToken") String accessToken, @RequestParam("hostId") String hostId)
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        return JamBox.addEvent(playlistId, accessToken, hostId, -1, null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/event")
    public Event event(@RequestParam("eventCode") int eventCode)
    {
        return JamBox.getEvent(eventCode);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/add_user")
    public void addUser(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        JamBox.getEvent(eventCode).addUser(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/user")
    public User user(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        return JamBox.getEvent(eventCode).getUser(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/upvote")
    public void upvote(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        JamBox.getEvent(eventCode).getUser(userId).changeVote(trackId, 1);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/downvote")
    public void downvote(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        JamBox.getEvent(eventCode).getUser(userId).changeVote(trackId, -1);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/request")
    public boolean request(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId, @RequestParam("trackId") String trackId)
    {
        return JamBox.getEvent(eventCode).getUser(userId).requestTrack(trackId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/disconnect")
    public void disconnect(@RequestParam("eventCode") int eventCode, @RequestParam("userId") String userId)
    {
        JamBox.getEvent(eventCode).getUser(userId).disconnect();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/end_event")
    public void endEvent(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("accessToken") String accessToken)
    {
        ((Host)JamBox.getEvent(eventCode).getUser(hostId)).endEvent(accessToken);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/remove_track")
    public void removeTrack(@RequestParam("eventCode") int eventCode, @RequestParam("hostId") String hostId, @RequestParam("trackId") String trackId, @RequestParam("accessToken") String accessToken)
    {
        ((Host)JamBox.getEvent(eventCode).getUser(hostId)).removeTrack(trackId, accessToken);
    }
}
