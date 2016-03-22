import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;

/**
 * Created by Mike on 3/21/2016.
 */
public class NoteComboBox {

    ArrayList<Note> notes;
    JFXComboBox<String> comboBox;
    Project current_project;

    public NoteComboBox(Project project, JFXComboBox<String> combo){
        if(combo == null) {
            throw new NullPointerException("The combobox provided is null");
        }
        if(project == null){
            throw new NullPointerException("No Project was provided");
        }

        notes = new ArrayList<Note>();
        current_project = project;

        // Creating Project selection combo box
        comboBox = combo;
        comboBox.setTooltip(new Tooltip("Click to pick a previously made note."));
        comboBox.setPromptText("Notes");

        notes = current_project.notes;

        for(Note n : notes){
            comboBox.getItems().add(n.subject);
        }

    }

    public void resize(){
        notes = current_project.notes;
        comboBox.getItems().clear();
        for(Note n : notes){
            comboBox.getItems().add(n.subject);
        }
    }

    public void changeProjects(Project new_current){
        current_project = new_current;
        resize();
    }
}
