import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

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
        comboBox.setTooltip(new Tooltip("Hey you! Click me to pick an item."));
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

    public  String selectProject(String name){
        if(name == null) {
            throw new NullPointerException("No name given");
        }

        for (Project p: projects){
            if (name.equals(p.name)){
                current_project = p;
            }
        }
        return current_project.name;
    }
}
