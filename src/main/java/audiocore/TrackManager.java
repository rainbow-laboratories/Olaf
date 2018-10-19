package audiocore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Class for Managing track operations
 * @author Christoph Pelzer
 */
public class TrackManager extends AudioEventAdapter {
    private final AudioPlayer PLAYER;
    private final Queue<AudioInfo> QUEUE;

    public TrackManager(AudioPlayer player) {
        this.PLAYER = player;
        this.QUEUE = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        this.QUEUE.add(info);
        if(this.PLAYER.getPlayingTrack() == null) {
            this.PLAYER.playTrack(track);
        }
    }

    public Set<AudioInfo> getQueue() {
        return new LinkedHashSet<>(this.QUEUE);
    }

    public AudioInfo getInfo(AudioTrack track) {
        return this.QUEUE.stream().filter(info -> info.getTrack().equals(track)).findFirst().orElse(null);
    }

    public void purgeQueue() {
        this.QUEUE.clear();
    }

    public void shuffleQueue() {
        List<AudioInfo> currentQueue = new ArrayList<>(getQueue());
        AudioInfo current = currentQueue.get(0);
        currentQueue.remove(0);
        Collections.shuffle(currentQueue);
        currentQueue.add(0, current);
        purgeQueue();
        this.QUEUE.addAll(currentQueue);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = this.QUEUE.element();
        VoiceChannel voiceChannel = info.getAuthor().getVoiceState().getChannel();

        if(voiceChannel == null) {
            player.stopTrack();
        } else {
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(voiceChannel);
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild g = this.QUEUE.poll().getAuthor().getGuild();
        if (this.QUEUE.isEmpty()) {
            g.getAudioManager().closeAudioConnection();
        } else {
            player.playTrack(this.QUEUE.element().getTrack());
        }
    }
}
