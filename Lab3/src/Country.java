import javax.swing.*;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Country {
    private String name;
    private String capital;
    private ImageIcon flag;

    public Country(String name) {
        this.name = name;
    }

    public Country(Path filePath) {
        StringBuilder nameBuilder = new StringBuilder();
        String word;

        flag = new ImageIcon(filePath.toString());
        String regex = "_[a-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filePath.toString());

        while (matcher.find()) {
            word = matcher.group(0).substring(1);
            word = word.substring(0, 1).toUpperCase() + word.substring(1);
            nameBuilder.append(word);
            nameBuilder.append(' ');
        }

        nameBuilder.deleteCharAt(nameBuilder.length() - 1);
        name = nameBuilder.toString();
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public ImageIcon getFlag() {
        return flag;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setFlag(ImageIcon flag) {
        this.flag = flag;
    }
}
