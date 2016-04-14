import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

/**
 * Created by Mike on 4/3/2016.
 */
public class ItalicFormat extends Mode {

    public ItalicFormat() {
        super("Italic", "<i>", "Text formatting setting", "Makes the specified text italicised when previewed", "<i>This text will be italicised</i>", true);
    }

    //format given text within bold tags, return the formatted text to the preview stage.
    public Text formatter (Text to_be_formatted, String family, double size) {
        to_be_formatted.setFont(Font.font(family, FontPosture.ITALIC, size));
        return to_be_formatted;
    }

    @Override
    public Text preview(Text textAreaBoxData)
    {

        return new Text("");
    }
}