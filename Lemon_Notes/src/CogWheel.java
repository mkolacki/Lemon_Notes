import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class CogWheel {

    Stage stage;
    GridPane pane;
    MenuBar menuBar;
    Menu menu;
    Coggie[] options = new Coggie[7];
    VisualSettings visual_settings;
    FontSettings font_settings;
    ModeSettings mode_settings;
    ProjectSettings project_settings;
    AccountManagement account_management;
    Help help;
    About about;

    public CogWheel(Stage main_stage, GridPane main_pane, MenuBar mb, Menu m){
        stage = main_stage;
        pane = main_pane;
        menuBar = mb;
        menu = m;
        options[0] = visual_settings = new VisualSettings();
        options[1] = font_settings = new FontSettings();
        options[2] = mode_settings = new ModeSettings();
        options[3] = project_settings = new ProjectSettings();
        options[4] = account_management = new AccountManagement();
        options[5] = help = new Help();
        options[6] = about = new About();

        Image cog_wheel = new Image(getClass().getResourceAsStream("cogicon.png"));
        menu.setGraphic(new ImageView(cog_wheel));
        final MenuItem thing1 = new MenuItem("Visual Settings...");
        final MenuItem thing2 = new MenuItem("Font Settings...");
        final MenuItem thing3 = new MenuItem("Mode Settings...");
        final MenuItem thing4 = new MenuItem("Project Settings...");
        final MenuItem thing5 = new MenuItem("Account Management...");
        final MenuItem thing6 = new MenuItem("Help");
        final MenuItem thing7 = new MenuItem("About");

        menu.getItems().addAll(thing1, thing2, thing3, thing4, thing5, thing6, thing7);
    }

}
