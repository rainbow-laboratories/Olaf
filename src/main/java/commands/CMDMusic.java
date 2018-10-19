package commands;

import audiocore.AudioInfo;
import audiocore.PlayerSendHandler;
import audiocore.TrackManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
public class CMDMusic implements Command {

    private static final int PLAYLIST_LIMIT = 1000;
    private static Guild guild;
    private static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
    private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<>();

    public CMDMusic() {
        AudioSourceManagers.registerRemoteSources(MANAGER);
    }

    public AudioPlayer createPlayer(Guild guild) {
        AudioPlayer player = MANAGER.createPlayer();
        TrackManager trackManager = new TrackManager(player);
        player.addListener(trackManager);

        this.guild.getAudioManager().setSendingHandler(new PlayerSendHandler(player));

        this.PLAYERS.put(guild, new AbstractMap.SimpleEntry<>(player, trackManager));
        return player;
    }

    private boolean hasPlayer(Guild guild) {
        return this.PLAYERS.containsKey(guild);
    }

    private AudioPlayer getPlayer(Guild guild) {
        if (hasPlayer(guild)) {
            return this.PLAYERS.get(guild).getKey();
        } else {
            return createPlayer(guild);
        }
    }

    private TrackManager getManager(Guild guild) {
        return PLAYERS.get(guild).getValue();
    }

    private boolean isIdle(Guild guild) {
        return !hasPlayer(guild) || getPlayer(guild).getPlayingTrack() == null;
    }

    private void loadTrack(String identifier, Member author, Message msg) {
        Guild guild = author.getGuild();
        getPlayer(guild);

        MANAGER.setFrameBufferDuration(5000);
        MANAGER.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                getManager(guild).queue(audioTrack, author);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                for(int i=0; i < (audioPlaylist.getTracks().size() > PLAYLIST_LIMIT ? PLAYLIST_LIMIT : audioPlaylist.getTracks().size()); i++) {
                    getManager(guild).queue(audioPlaylist.getTracks().get(i), author);
                }
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }

    private void skip(Guild guild) {
        getPlayer(guild).stopTrack();
    }

    private String getTimestamp(long milis) {
        long seconds = milis/1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours*3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins*60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    private String buildQueueMessage(AudioInfo info) {
        AudioTrackInfo trackInfo = info.getTrack().getInfo();
        String title = trackInfo.title;
        long length = trackInfo.length;
        return "*[ " + getTimestamp(length) + " ]* " + title + '\n';
    }

    private void sendErrorMsg(MessageReceivedEvent event, String content) {
        event.getTextChannel().sendMessage(
                new EmbedBuilder().setColor(Color.red).setDescription(content).build()
        ).queue();
    }

    @Override
    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        this.guild = event.getGuild();

        if(args.size() < 1) {
            sendErrorMsg(event, help());
            return;
        }

        switch (args.get(0).toLowerCase()) {
            case "play": case "p": case "doit":
                if (args.size() < 2) {
                    sendErrorMsg(event, "Please enter a Source");
                    return;
                }
                String input = args.stream().skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);

                if(!(input.startsWith("http://") || input.startsWith("https://"))) {
                    input = "ytsearch: " + input;
                }

                loadTrack(input, event.getMember(), event.getMessage());
                break;
            case "skip":
                if(isIdle(guild)) return;
                for(int i = (args.size() > 1 ? Integer.parseInt(args.get(1)) : 1); i==1; i--) {
                    skip(guild);
                }
                break;
            case "stop":
                if(isIdle(guild)) return;
                getManager(guild).purgeQueue();
                skip(guild);
                guild.getAudioManager().closeAudioConnection();
                break;
            case "shuffle":
                if(isIdle(guild)) return;

                getManager(guild).shuffleQueue();
                break;
            case "now": case "info":
                if(isIdle(guild)) return;
                AudioTrack track = getPlayer(guild).getPlayingTrack();
                AudioTrackInfo info = track.getInfo();
                event.getTextChannel().sendMessage(
                        new EmbedBuilder()
                                .setColor(Color.green)
                                .setDescription("**CURRENT TRACK INFO:**")
                                .addField("Title", info.title, false)
                                .addField("Duration", "*[ " + getTimestamp(track.getPosition()) + "/ " + getTimestamp(track.getDuration()) + " ]*", false)
                                .addField("Author", info.author, false)
                                .build()
                ).queue();
                break;
            case "queue":
                if(isIdle(guild)) return;

                int sideNumb = args.size() > 1 ? Integer.valueOf(args.get(1)) : 1;
                List<String> tracks = new ArrayList<>();
                List<String> trackSublist;

                getManager(guild).getQueue().forEach(audioInfo -> tracks.add(buildQueueMessage(audioInfo)));
                if (tracks.size() > 20) {
                    trackSublist = tracks.subList((sideNumb-1)*20, (sideNumb-1)*20+20);
                } else {
                    trackSublist = tracks;
                }

                String out = trackSublist.stream().collect(Collectors.joining("\n"));
                int sideNumbAll = tracks.size() >= 20 ? tracks.size()/20 : 1;

                event.getTextChannel().sendMessage(
                        new EmbedBuilder()
                                .setColor(Color.blue)
                                .setDescription(
                                        "**CURRENT QUEUE:**\n\n" +
                                                "*[" + getManager(guild).getQueue().stream() + " Tracks | Side " + sideNumb + " / " + sideNumbAll + "]*" +
                                                out
                                )
                                .build()
                ).queue();
                break;
        }
    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "I know the commands play, info, shuffle, queue, stop, skip";
    }
}
