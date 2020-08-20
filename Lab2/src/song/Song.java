package song;

import disc.Disc;
import java.util.Objects;

public abstract class Song {
    public enum Genre {
        Rock, Pop, Classic, Rap, Jazz, Electronic
    }

    protected int secondDuration;
    protected Genre genre;
    protected String artist;
    protected String name;

    public Song(String artist, String name, String genre, int duration) throws IllegalArgumentException {
        this.artist = artist;
        this.name = name;

        try {
            this.genre = Genre.valueOf(genre);
        }
        catch (IllegalArgumentException | NullPointerException exception) {
            throw new IllegalArgumentException("Incorrect genre");
        }

        if (duration < 0) {
            throw new IllegalArgumentException("Incorrect duration");
        }

        secondDuration = duration;
    }

    public int getSecondDuration() {
        return secondDuration;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public abstract double getUsableMemory(Disc disc);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Song)) return false;
        Song song = (Song) o;
        return secondDuration == song.secondDuration &&
                genre == song.genre &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(name, song.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secondDuration, genre, artist, name);
    }

    @Override
    public String toString() {
        return "Song \"" + name + "\"" +
                ", artist: " + artist +
                ", genre: " + genre +
                ", duration: " + secondDuration / 60 + "m " + secondDuration % 60 + "s";
    }
}
