import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Michael K. on 4/3/2016.
 */
public abstract class Mode {

    static ArrayList<Mode> allModes = new ArrayList<Mode>();
    String mode;
    String indicator;
    String format_and_parameters;
    String description;
    String example;
    boolean isActive;

    public static void updateAllModes(String textAreaBoxData)
    {
        Text text = new Text(textAreaBoxData);
        for(Mode m: allModes)
            if(m.isActive)
                m.preview(text);
    }

    public Mode(String new_mode, String initiator, String what_to_take, String what_it_does, String how_to_do_it){
        this(new_mode, initiator, what_to_take, what_it_does, how_to_do_it, false);
    }

    public Mode(String new_mode, String initiator, String what_to_take, String what_it_does, String how_to_do_it, boolean activate){
        mode = new_mode;
        indicator = initiator;
        format_and_parameters = what_to_take;
        description = what_it_does;
        example = how_to_do_it;
        isActive = activate;
        allModes.add(this);
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public abstract Text preview(Text textAreaBoxData);

    public String getInfo(){
        return "Indicator: " + indicator + "\n" +
                "Description: " + description + "\n" +
                "Format and Parameters: " + format_and_parameters + "\n" +
                "Example: " + example + "\n";

    }
}
