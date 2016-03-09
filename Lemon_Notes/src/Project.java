import javafx.scene.shape.Path;

import javax.security.auth.Subject;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class Project {

    final String name;
    final String location;
    final String saved_notes;
    final String temporary_note;
    ArrayList<Note> notes;
    final File all_notes;
    final File temp_note;

    public Project(String projcet_name){
        name = projcet_name;
        notes = new ArrayList<Note>();
        location = "Projects/" + name;
        saved_notes = location + "/saved_notes.txt";
        temporary_note = location + "/temp.txt";
        all_notes = new File(saved_notes);
        temp_note = new File(temporary_note);
        File dir = new File(location);
        Boolean made_dir = false;
        //make directory for new project -- Mike C.

        try {
            made_dir = dir.mkdirs();
        } catch (SecurityException se) {
            System.out.println("Unsuccessful: " + se.toString());
        }
    }

    public void addNote(String note_to_add, String subject, Boolean new_note){
        if(new_note){
            Note thing_to_add = new Note(note_to_add);
            notes.add(notes.size(), thing_to_add);
            //
            String full_note = notes.size() + ".    Subject: " + subject + "\n" +
                    "   Note: " + note_to_add + "\n\n";

        }
    }
}
