import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class ProjectCombobox {

    Stage stage;
    GridPane pane;
    ArrayList<Project> projects;
    JFXComboBox<String> comboBox;

    public ProjectCombobox(Stage main_stage, GridPane main_pane, JFXComboBox<String> combo){
        stage = main_stage;
        pane = main_pane;
        projects = new ArrayList<Project>();

        // Creating Project selection combo box
        comboBox = combo;
        comboBox.setTooltip(new Tooltip("Hey you! Click me to pick an item."));
        comboBox.setPromptText("Projects");
        //Temporary things to list
       /* comboBox.getItems().add("Project 1");
        comboBox.getItems().add("Project 2");
        comboBox.getItems().add("Project 3");
        comboBox.getItems().add("Test");*/
    }

    public void addAProject(String name, String location){
        Boolean is_viable = true;

        for (Project p: projects){
            if(name.equals(p.name)){
                is_viable = false;
                break;
            }
        }

        if (is_viable){
            Project new_project = new Project(name, location);
            projects.add(projects.size(), new_project);
            comboBox.getItems().add(name);
        }else {
            System.out.println("A project with that name already exists!");
        }

    }
}
