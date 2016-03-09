import java.io.File;
import java.util.ArrayList;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class Project {

    final String name;
    final String location;
    ArrayList<Note> notes;

    public Project(String project_name, String project_location){
        name = project_name;
        location = project_location;
        notes = new ArrayList<Note>();

        File dir = new File(name);
        Boolean madedir = false;
        //do something here to set up a directory with the specified location. If location can't be a string, then the String identifier will also need to be changed in ProjectCombobox
        //make directory for new project -- Mike C.
        try {
            dir.mkdir();
            madedir = true;
        } catch (SecurityException se) {
            System.out.println("Unsuccessful: " + se.toString());
        }
    }

    public void addNote(String note_to_add, Boolean new_note){
        if(new_note){
            Note thing_to_add = new Note(note_to_add);
            notes.add(notes.size(), thing_to_add);
        }
    }
}
