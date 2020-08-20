package song.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import song.Song;
import song.formats.CdaFormatSong;
import song.formats.Mp3FormatSong;
import song.formats.WavFormatSong;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SongHandler extends DefaultHandler {
    List<Song> songs;

    String artist;
    String name;
    String genre;
    String duration;
    String format;
    String bitrate;

    public SongHandler(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException,
                                                                                                IllegalArgumentException,
                                                                                                InputMismatchException,
                                                                                                NoSuchElementException {
        if (qName.equals("song")) {
            artist = attributes.getValue("artist");
            name = attributes.getValue("name");
            genre = attributes.getValue("genre");
            duration = attributes.getValue("duration");
            format = attributes.getValue("format");

            Scanner scanner = new Scanner(duration);
            scanner.useDelimiter(":");
            int m, s;
            m = scanner.nextInt();
            s = scanner.nextInt();

            switch (format) {
                case "cda":
                    songs.add(new CdaFormatSong(artist, name, genre, m * 60 + s));
                    break;
                case "wav":
                    songs.add(new WavFormatSong(artist, name, genre, m * 60 + s));
                    break;
                case "mp3":
                    bitrate = attributes.getValue("bitrate");
                    songs.add(new Mp3FormatSong(artist, name, genre, m * 60 + s, Integer.valueOf(bitrate)));
                    break;
                default:
                    throw new IllegalArgumentException("Not existing format");
            }
        }
    }
}
