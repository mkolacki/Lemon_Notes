import javafx.scene.text.Text;

/**
 * Created by Michael K. on 4/3/2016.
 */
public class BasicCalculator extends Mode {

    public BasicCalculator() {
        super("Basic Calculator", "<calc>", "Numbers and basic operations", "Performs the input basic calculation when previewed or prompted", "<calc>5 + 3</calc>");
    }

    @Override
    public Text preview(Text textAreaBoxData)
    {
        return new Text("");
    }
}
