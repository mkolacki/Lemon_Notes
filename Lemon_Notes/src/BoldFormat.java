import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Mike on 4/3/2016.
 */
public class BoldFormat extends Mode {

    public BoldFormat() {
        super("Bold", "<b>", "Text formatting setting", "Makes the specified text bold when previewed", "<b>This text will become bold<b>");
    }

    //format given text within bold tags, return the formatted text to the preview stage.
    public Text formatter (Text to_be_formatted, String family, double size) {
        to_be_formatted.setFont(Font.font(family, FontWeight.BOLD, size));
        return to_be_formatted;
    }
}
