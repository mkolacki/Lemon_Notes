import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Tooltip;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class ProjectCombobox {

    private final static String DIRECTORY = "Projects/";

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

    public Project selectProject(String name){
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

    public void removeProject(String name)
    {
        if(name == null)
            throw new NullPointerException("No name given");
        Project saved = null;
        for (Project p: projects)
        {
            if (name.equals(p.name))
            {
                p.removeAllNotes();
                File delete_me = new File(DIRECTORY + p.name);
                if (delete_me.exists())
                    deleteDir(delete_me);
                else
                    System.out.println("Attempted to delete project but found no such project: " + DIRECTORY + p.name);
//                projects.remove(projects.indexOf(p));
                comboBox.getItems().remove(name);
                saved = p;
            }
        }
        if(saved != null)
            projects.remove(saved);
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    public void changeProjectName(String oldName, String newName)
    {
        for(Project pro: projects)
        {
            if(pro.name.equals(oldName))
            {
                pro.name = newName;
                File rename = new File(DIRECTORY + oldName);
                rename.renameTo(new File(DIRECTORY + newName));
                comboBox.getItems().set(comboBox.getItems().indexOf(oldName), newName);
            }
        }
    }
}
