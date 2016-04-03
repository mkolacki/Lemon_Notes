import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Mike on 4/3/2016.
 */
public class ItalicFormat extends Mode {

    public ItalicFormat(String new_mode, String initiator, String what_to_take, String what_it_does, String how_to_do_it) {
        super(new_mode, initiator, what_to_take, what_it_does, how_to_do_it);
    }

    //format given text within bold tags, return the formatted text to the preview stage.
    public Text formatter (Text to_be_formatted, String family, double size) {
        to_be_formatted.setFont(Font.font(family, FontPosture.ITALIC, size));
        return to_be_formatted;
    }
}