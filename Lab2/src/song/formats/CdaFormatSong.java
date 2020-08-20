package song.formats;

import disc.Disc;
import song.Song;

public class CdaFormatSong extends Song {
    public CdaFormatSong(String artist, String name, String genre, int duration) throws IllegalArgumentException {
        super(artist, name, genre, duration);
    }

    @Override
    public double getUsableMemory(Disc disc) {
        int remDuration = disc.getRemainingDurationInSeconds();
        double remMemory= disc.getRemainingMemory();

        return secondDuration * remMemory / remDuration;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o.getClass().equals(this.getClass());
    }

    @Override
    public String toString() {
        return super.toString() + ", format: .cda";
    }
}
