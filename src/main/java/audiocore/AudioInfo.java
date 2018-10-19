package audiocore;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Member;

/**
 * Wrapper class to store Track and Author together
 * @author Christoph Pelzer
 */
public class AudioInfo {

    private final AudioTrack TRACK;
    private final Member AUTHOR;

    public AudioInfo(AudioTrack track, Member author) {
        this.TRACK = track;
        this.AUTHOR = author;
    }

    public AudioTrack getTrack() {
        return this.TRACK;
    }

    public Member getAuthor() {
        return this.AUTHOR;
    }
}
