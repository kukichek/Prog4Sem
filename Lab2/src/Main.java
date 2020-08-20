import disc.Disc;
import disc.OutOfDiscMemoryException;
import org.xml.sax.SAXException;
import song.Song;
import song.parser.SongHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        test1();
        System.out.println();
//        test2();
    }

    public static void test1() {
        try {
            List<Song> longCollection = new ArrayList<Song>();
            File file = new File("longOne.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SongHandler handler = new SongHandler(longCollection);
            parser.parse(file, handler);

            Disc cd = new Disc(800, (int) 79.8 * 60);
            try {
                cd.burn(longCollection);
            }
            catch (OutOfDiscMemoryException exception) {
                System.out.println(exception.toString());
            }

            System.out.println("Total duration: " + cd.getSongsDuration() / 60 + "m " + cd.getSongsDuration() % 60 + "s");

            List<Song> shortSongs = cd.findByRange(0, 5 * 60);
            if (shortSongs != null) {
                System.out.println("Song on cd with duration less than five minutes:");
                for (Song shortSong : shortSongs) {
                    System.out.println(shortSong);
                }
            } else {
                System.out.println("There's no song with duration less than 5 minutes");
            }
        }
        catch (ParserConfigurationException | SAXException exception) {
            System.out.println("SAX exception");
        }
        catch (IOException exception) {
            System.out.println("Unable to open the file");
        }
    }

    public static void test2() {
        try {
            List<Song> shortCollection = new ArrayList<Song>();
            File file = new File("shortOne.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SongHandler handler = new SongHandler(shortCollection);
            parser.parse(file, handler);

            Disc cd = new Disc(800, (int) 79.8 * 60);
            try {
                cd.burn(shortCollection);
            }
            catch (OutOfDiscMemoryException exception) {
                System.out.println(exception.toString());
            }

            System.out.println("Total duration: " + cd.getSongsDuration() / 60 + "m " + cd.getSongsDuration() % 60 + "s");

            cd.sortByGenre();
            System.out.println(cd);
        }
        catch (ParserConfigurationException | SAXException exception) {
            System.out.println("SAX exception");
        }
        catch (IOException exception) {
            System.out.println("Unable to open the file");
        }
    }
}
