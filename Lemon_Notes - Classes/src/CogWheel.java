import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class CogWheel {
    Coggie[] options = new Coggie[6];
    VisualSettings visual_settings;
    FontSettings font_settings;
    ModeSettings mode_settings;
    ProjectSettings project_settings;
    AccountManagement account_management;
    Help help;
    About about;

    public CogWheel(Stage main_stage, GridPane main_pane){
        options[0] = visual_settings = new VisualSettings();
        options[1] = font_settings = new FontSettings();
        options[2] = mode_settings = new ModeSettings();
        options[3] = project_settings = new ProjectSettings();
        options[4] = account_management = new AccountManagement();
        options[5] = help = new Help();
        options[6] = about = new About();
    }

}
