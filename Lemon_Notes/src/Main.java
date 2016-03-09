import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePatternBuilder;
import javafx.stage.Stage;

/**
 * Created by alex on 3/6/2016.
 * Main class to start the program
 */
public class Main extends Application
{
    /**
     * Launches the main view
     * @param args
     *      The initial arguments passed a runtime.
     * @throws Exception
     *      Throws exception if there is a failure while launching the Application
     */
    public static void main(String[] args) throws Exception
    {
        launch(args);
    }

    /**
     * The method called by Application for starting up the display
     * @param primaryStage
     *      The stage in which the main view will be presented
     *
     */
    @Override
    public void start(final Stage primaryStage)
    {
        // Creating a random button
        final JFXButton button = new JFXButton("Button");
        button.setTooltip(new Tooltip("It's a button!"));
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), new Insets(0,0,0,0))));
        button.setButtonType(JFXButton.ButtonType.RAISED);

        // Creating Project selection combo box
        final JFXComboBox<String> combo = new JFXComboBox<String>();

        //combo.setTooltip(new Tooltip("Hey you! Click me to pick a listed item."));
        //combo.setPromptText("Look at a project.");
        /*combo.getItems().add("Project 1");
        combo.getItems().add("Project 2");
        combo.getItems().add("Project 3");
        combo.getItems().add("Test");*/


        // Making a dropdown menu
        final MenuBar menu = new MenuBar();
        //Image cog_wheel = new Image(getClass().getResourceAsStream("cogicon.png"));
        final Menu menu1 = new Menu();

        /*menu1.setGraphic(new ImageView(cog_wheel));
        final MenuItem thing1 = new MenuItem("Thing 1");
        final MenuItem thing2 = new MenuItem("Thing 2");
        final MenuItem thing3 = new MenuItem("Thing 3");
        menu1.getItems().addAll(thing1, thing2, thing3);*/
        //menu.getMenus().add(menu1);


        // Creating note control buttons
        final JFXButton newnote = new JFXButton();
        Image new_note_graphic = new Image(getClass().getResourceAsStream("newpageicon.png"));
        newnote.setGraphic(new ImageView(new_note_graphic));
        newnote.setTooltip(new Tooltip("Clears the current screen"));

        final JFXButton preview = new JFXButton();
        Image  preview_graphic = new Image(getClass().getResourceAsStream("eyeicon.png"));
        preview.setGraphic(new ImageView(preview_graphic));
        preview.setTooltip(new Tooltip("Previews the current note in the indicated mode."));

        final JFXButton submit = new JFXButton();
        Image submit_graphic = new Image(getClass().getResourceAsStream("plusicon.png"));
        submit.setGraphic(new ImageView(submit_graphic));
        submit.setTooltip(new Tooltip("Adds the current note to the current project."));

        final JFXButton delete = new JFXButton();
        Image delete_graphic = new Image(getClass().getResourceAsStream("trashicon.png"));
        delete.setGraphic(new ImageView(delete_graphic));
        delete.setTooltip(new Tooltip("Deletes the currently displayed note."));

        final JFXTextArea bigbox = new JFXTextArea();

        // Setting up grid pane
        GridPane pane = new GridPane();
        ProjectCombobox combobox = new ProjectCombobox(primaryStage, pane, combo);
        combobox.addAProject("Project 1");
        combobox.addAProject("Project 2");
        combobox.addAProject("Project 3");
        combobox.addAProject("Test");
        combobox.addAProject("Rest");

        final CogWheel cogWheel = new CogWheel(primaryStage, pane, menu, menu1);
        menu.getMenus().add(cogWheel.menu);

        pane.add(combobox.comboBox, 0, 0);
        pane.add(menu, 1, 0);
        pane.add(newnote, 3, 1);
        pane.add(preview, 3, 2);
        pane.add(submit, 3, 3);
        pane.add(delete, 3, 4);
        pane.add(bigbox, 0, 1, 2, 5);
        pane.setHgap(10);
        pane.setVgap(10);

        // Setting the general padding for the grid pane
        ColumnConstraints cons1 = new ColumnConstraints();
        pane.getColumnConstraints().add(cons1);

        //Testing how JFoenix events work. --Mike C.
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setTitle("Hey, you clicked a button!");
            }
        });

        combo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle(combo.getSelectionModel().getSelectedItem().toString());
                combo.setTooltip(new Tooltip("Click to pick another project."));
            }
        });

        //Creating experimental buttons
        JFXButton btn1 = new JFXButton("Button1");
        JFXButton btn2 = new JFXButton("Button2");
        JFXButton btn3 = new JFXButton("Button3");
        JFXButton btn4 = new JFXButton("Button4");


        // Adding pane the the scene
        final Scene scene = new Scene(pane, 600, 250);
        primaryStage.setTitle("Lemon Notes");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
