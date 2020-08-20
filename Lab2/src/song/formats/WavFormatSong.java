package song.formats;

import disc.Disc;
import song.Song;

import java.util.Objects;

public class WavFormatSong extends Song {
    final static double AVG_MEMORY_PER_MINUTE = 10;

    public WavFormatSong(String artist, String name, String genre, int duration) throws IllegalArgumentException {
        super(artist, name, genre, duration);
    }

    @Override
    public double getUsableMemory(Disc disc) {
        return secondDuration * 1. / 60 * AVG_MEMORY_PER_MINUTE;
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
        return super.toString() + ", format: .wav";
    }
}
