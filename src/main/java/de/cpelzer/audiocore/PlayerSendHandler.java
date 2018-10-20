package de.cpelzer.audiocore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Buffer Improvements
 * @author Christoph Pelzer
 */
public class PlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer AUDIOPLAYER;
    private AudioFrame lastFrame;

    public PlayerSendHandler(AudioPlayer audioPlayer) {
        this.AUDIOPLAYER = audioPlayer;
    }

    @Override
    public boolean canProvide() {
        if(lastFrame == null) {
            lastFrame = this.AUDIOPLAYER.provide();
        }
        return lastFrame!=null;
    }

    @Override
    public byte[] provide20MsAudio() {
        if(lastFrame == null) {
            lastFrame = this.AUDIOPLAYER.provide();
        }
        byte[] data = lastFrame != null ? lastFrame.getData() : null;
        lastFrame = null;
        return data;
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
