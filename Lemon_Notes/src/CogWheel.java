import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    ProjectCombobox projectCombobox;
    Coggie[] options = new Coggie[7];
    VisualSettings visual_settings;
    FontSettings font_settings;
    ModeSettings mode_settings;
    ProjectSettings project_settings;
    AccountManagement account_management;
    Help help;
    About about;

    MenuItem visualSettings;
    MenuItem fontSettings;
    MenuItem modeSettings;
    MenuItem projectSettings;
    MenuItem accountManagement;
    MenuItem helpItem;
    MenuItem aboutItem;

    public CogWheel(final Stage main_stage, final GridPane main_pane, final MenuBar mb, final Menu m, final ProjectCombobox projectCombobox)
    {
        if(main_stage != null)
            stage = main_stage;
        else
            throw new NullPointerException("The main stage is null.");
        if(main_pane != null)
            pane = main_pane;
        else
            throw new NullPointerException("The main pane is null.");
        if(mb != null)
            menuBar = mb;
        else
            throw new NullPointerException("The menu bar is null.");
        if (m != null)
            menu = m;
        else
            throw new NullPointerException("The menu is null.");
        if (projectCombobox != null)
            this.projectCombobox = projectCombobox;
        else
            throw new NullPointerException("The project combo-box is null.");
        options[0] = visual_settings = new VisualSettings();
        options[1] = font_settings = new FontSettings(main_stage, main_pane);
        options[2] = mode_settings = new ModeSettings(main_stage, main_pane);
        options[3] = project_settings = new ProjectSettings(main_stage, projectCombobox);
        options[4] = account_management = new AccountManagement();
        options[5] = help = new Help();
        options[6] = about = new About();

        Image cog_wheel = new Image(getClass().getResourceAsStream("cogicon.png"));
        menu.setGraphic(new ImageView(cog_wheel));
        visualSettings = new MenuItem("Visual Settings...");
        fontSettings = new MenuItem("Font Settings...");
        modeSettings = new MenuItem("Mode Settings...");
        projectSettings = new MenuItem("Project Settings...");
        accountManagement = new MenuItem("Account Management...");
        helpItem = new MenuItem("Help");
        aboutItem = new MenuItem("About");

        fontSettings.setOnAction(new EventHandler<ActionEvent>()
        {
            /**
             * {@inheritDoc}
             */
            @Override
            public void handle(ActionEvent event)
            {
                font_settings.show();
            }
        });
        projectSettings.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                project_settings.show();
            }
        });
        modeSettings.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * {@inheritDoc}
             */
            @Override
            public void handle(ActionEvent event)
            {
                mode_settings.show();
            }
        });

        menu.getItems().addAll(visualSettings, fontSettings, modeSettings, projectSettings, accountManagement, helpItem, aboutItem);
    }

}
