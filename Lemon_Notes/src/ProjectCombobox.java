import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class ProjectCombobox {

    ArrayList<Project> projects;
    JFXComboBox<String> comboBox;
    Project current_project;

    public ProjectCombobox(JFXComboBox<String> combo){
        if(combo == null) {
            throw new NullPointerException("combo box is null");
        }
        projects = new ArrayList<Project>();

        // Creating Project selection combo box
        comboBox = combo;
        comboBox.setTooltip(new Tooltip("Click to pick another project."));
        comboBox.setPromptText("Projects");

    }

    public void addAProject(String name){
        if(name == null) {
            throw new NullPointerException("No name given");
        }
        Boolean is_viable = true;

        for (Project p: projects){
            if(name.equals(p.name)){
                is_viable = false;
                break;
            }
        }

        if (is_viable){
            Project new_project = new Project(name);
            projects.add(projects.size(), new_project);
            comboBox.getItems().add(name);
            current_project = new_project;
        }else {
            System.out.println("A project with that name already exists!");
        }
    }

    public  Project selectProject(String name){
        if(name == null) {
            throw new NullPointerException("No name given");
        }
        for (Project p: projects){
            if (name.equals(p.name)){
                current_project = p;
                /*String saved_notes = "Projects/" + p.name + "/saved_notes";
                File all_notes = new File(saved_notes);
                if(all_notes.isDirectory()){
                    File[] note_list = all_notes.listFiles();
                    if(note_list.length != 0){
                        for(File n : note_list){
                            String subject = n.getName();
                            subject = subject.replace(".txt", "");
                            String note = "";
                            Scanner sc = null;
                            try {
                                sc = new Scanner(n);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            while(sc.hasNextLine()){
                                note = note + sc.nextLine();
                            }
                            current_project.notes.add(current_project.notes.size(), new Note(note, subject));
                        }
                    }
                }*/


            }
        }
        return current_project;
    }
}
