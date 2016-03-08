import java.util.ArrayList;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class Project {

    final String name;
    final String location;
    ArrayList<Note> notes;

    public Project(String projcet_name, String project_location){
        name = projcet_name;
        location = project_location;
        notes = new ArrayList<Note>();

        //do something here to set up a directory with the specified location. If location can't be a string, then the String identifier will also need to be changed in ProjectCombobox
    }

    public void addNote(String note_to_add, Boolean new_note){
        if(new_note){
            Note thing_to_add = new Note(note_to_add);
            notes.add(notes.size(), thing_to_add);
        }
    }
}
