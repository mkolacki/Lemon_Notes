import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Path;

import javax.security.auth.Subject;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class Project {

    final String name;
    final String location;
    final String saved_notes;
    //final String temporary_note;
    ArrayList<Note> notes;
    //final File all_notes;
    //final File temp_note;

    public Project(String project_name){
        if (project_name == null) {
            throw new NullPointerException("Project name is null.");
        }

        name = project_name;

        notes = new ArrayList<Note>();
        location = "Projects/" + name;
        saved_notes = location + "/saved_notes";
        //temporary_note = location + "/temp.txt";
        File all_notes = new File(saved_notes);
        //temp_note = new File(temporary_note);
        File dir = new File(location);
        Boolean made_dir = false;
        Boolean made_all_notes = false;
        //make directory for new project -- Mike C.

        try {
            made_dir = dir.mkdirs();

        } catch (SecurityException se) {
            System.out.println("Unsuccessful attempt to make Directory --- " + location + ":\n" + se.toString());
        }
        try {
            made_all_notes = all_notes.mkdirs();

            if(!made_all_notes){
                File[] note_list = all_notes.listFiles();
                if(note_list.length != 0){
                    for(File n : note_list){
                        String subject = n.getName();
                        subject = subject.replace(".txt", "");
                        String note = "";
                        Scanner sc = new Scanner(n);
                        while(sc.hasNextLine()){
                            note = note + "\n" + sc.nextLine();
                        }
                        notes.add(notes.size(), new Note(note, subject));
                    }
                }
            }
        } catch (SecurityException se) {
            System.out.println("Unsuccessful attempt to make Directory --- " + saved_notes + ":\n" + se.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addNote(String note_to_add, String subject, Boolean new_note){
        if(note_to_add == null) {
            throw new NullPointerException("No note content.");
        }
        if(subject == null) {
            throw new NullPointerException("No subject content.");
        }
        if(new_note == null) {
            throw new NullPointerException("Boolean new_note is null.");
        }

        if(new_note){
            Note thing_to_add = new Note(note_to_add, subject);
            notes.add(notes.size(), thing_to_add);
            //
            String full_note = notes.size() + ".    Subject: " + subject + "\n" +
                    "   Note: " + note_to_add + "\n\n";

            try {
                FileWriter fw = new FileWriter(saved_notes + "/" + subject + ".txt");
                fw.write(full_note);
                fw.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public Note selectNote(String subject) {
        if (subject == null) {
            throw new NullPointerException("No subject given");
        }

        Note pickedNote = null;

        for (Note n : notes) {
            if (subject.equals(n.subject)) {
                pickedNote = n;
            }
        }
        return pickedNote;
    }
}
