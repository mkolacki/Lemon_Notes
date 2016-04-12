import javafx.scene.text.Text;

/**
 * Created by Mike on 4/12/2016.
 */
public class ColorFormat extends Mode {

    public ColorFormat() {
        super("Color", "<c>", "Text formatting setting", "Makes the text a color specified by a 6-digit hex value.", "<c>{color=0xFF0000}This text will become red.</c>", true);

    }

    public Text formatter (Text to_be_formatted) {

        return to_be_formatted;
    }

    @Override
    public void update(String textAreaBoxData)
    {

    }
}
