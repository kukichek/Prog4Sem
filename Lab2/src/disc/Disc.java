package disc;

import song.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Disc {
    final double totalMemory;
    final int totalDuration;

    protected double remainingMemory;
    protected int remainingDurationInSeconds;
    List<Song> songs;

    public Disc(double memory, int duration) {
        totalMemory = memory;
        totalDuration = duration;

        remainingMemory = memory;
        remainingDurationInSeconds = duration;

        songs = new ArrayList<Song>();
    }

    public void burn(List<Song> songsToBurnOn) throws OutOfDiscMemoryException{
        for (Song song : songsToBurnOn) {
            if (song.getUsableMemory(this) > remainingMemory) {
                throw new OutOfDiscMemoryException("Exceeded memory limit. " + this.toString());
            } else {
                songs.add(song);
                remainingMemory -= song.getUsableMemory(this);
                remainingDurationInSeconds = (int) (remainingMemory / totalMemory * totalDuration);
            }
        }
    }

    public double getRemainingMemory() {
        return remainingMemory;
    }

    public int getRemainingDurationInSeconds() {
        return remainingDurationInSeconds;
    }

    public int getSongsDuration() {
        int duration = 0;
        for (Song song : songs) {
            duration += song.getSecondDuration();
        }

        return duration;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Song> findByRange(int dur1, int dur2) {
        List<Song> shortSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getSecondDuration() >= dur1 && song.getSecondDuration() < dur2) {
                shortSongs.add(song);
            }
        }

        return shortSongs.isEmpty()? null : shortSongs;
    }

    public void sortByGenre() {
        songs.sort(Comparator.comparing(Song::getGenre));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disc disc = (Disc) o;
        return Double.compare(disc.remainingMemory, remainingMemory) == 0 &&
                remainingDurationInSeconds == disc.remainingDurationInSeconds &&
                Objects.equals(songs, disc.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remainingMemory, remainingDurationInSeconds, songs);
    }

    @Override
    public String toString() {
        return "Disc{" +
                "songs=\n" + songs.stream().map(Song::toString).map((String s)-> s + ",\n").collect(Collectors.joining()) +
                '}';
    }
}
