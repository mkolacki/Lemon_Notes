/**
 * Created by Michael K. on 4/3/2016.
 */
public abstract class Mode {
    String mode;
    String indicator;
    String format_and_parameters;
    String description;
    String example;

    public Mode(String new_mode, String initiator, String what_to_take, String what_it_does, String how_to_do_it){
        mode = new_mode;
        indicator = initiator;
        format_and_parameters = what_to_take;
        description = what_it_does;
        example = how_to_do_it;
    }

    public String getInfo(){
        return "Indicator: " + indicator + "\n" +
                "Description: " + description + "\n" +
                "Format and Parameters: " + format_and_parameters + "\n" +
                "Example: " + example + "\n";

    }
}
