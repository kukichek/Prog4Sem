package song.formats;

import disc.Disc;
import song.Song;

import java.util.Objects;

public class Mp3FormatSong extends Song {
    final static double AVG_MEMORY_PER_MINUTE = 2;
    final static double AVG_BITRATE = 160;
    private int bitrate;

    public Mp3FormatSong(String artist, String name, String genre, int duration, int bitrate) throws IllegalArgumentException {
        super(artist, name, genre, duration);

        if (bitrate < 32 || bitrate > 320) {
            throw new IllegalArgumentException("Incorrect bitrate");
        }
        this.bitrate = bitrate;
    }

    @Override
    public double getUsableMemory(Disc disc) {
        return secondDuration * 1. / 60 * AVG_MEMORY_PER_MINUTE * bitrate / AVG_BITRATE;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o.getClass().equals(this.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondDuration, genre, artist, name, AVG_MEMORY_PER_MINUTE);
    }

    @Override
    public String toString() {
        return super.toString() + ", format: .mp3";
    }
}
