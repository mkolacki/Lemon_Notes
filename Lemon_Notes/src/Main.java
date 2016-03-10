import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePatternBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;
import java.util.regex.Pattern;

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

    private JFXButton submit;
    private  JFXButton button;
    private JFXButton preview;
    private JFXComboBox<String> combo;
    private MenuBar menu;
    private Menu menu1;
    private JFXButton newnote;
    private JFXButton delete;

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
        button = new JFXButton("Button");
        button.setTooltip(new Tooltip("It's a button!"));
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), new Insets(0,0,0,0))));
        button.setButtonType(JFXButton.ButtonType.RAISED);

        // Creating Project selection combo box
        combo = new JFXComboBox<String>();

        // Making a dropdown menu
        menu = new MenuBar();

        //Image cog_wheel = new Image(getClass().getResourceAsStream("cogicon.png"));
        Menu menu1 = new Menu();

        // Creating note control buttons
        JFXButton newnote = new JFXButton();
        Image new_note_graphic = new Image(getClass().getResourceAsStream("newpageicon.png"));
        newnote.setGraphic(new ImageView(new_note_graphic));
        newnote.setTooltip(new Tooltip("Clears the current screen"));

        preview = new JFXButton();
        Image  preview_graphic = new Image(getClass().getResourceAsStream("eyeicon.png"));
        preview.setGraphic(new ImageView(preview_graphic));
        preview.setTooltip(new Tooltip("Previews the current note in the indicated mode."));

        submit = new JFXButton();
        Image submit_graphic = new Image(getClass().getResourceAsStream("plusicon.png"));
        submit.setGraphic(new ImageView(submit_graphic));
        submit.setTooltip(new Tooltip("Adds the current note to the current project."));

        delete = new JFXButton();
        Image delete_graphic = new Image(getClass().getResourceAsStream("trashicon.png"));
        delete.setGraphic(new ImageView(delete_graphic));
        delete.setTooltip(new Tooltip("Deletes the currently displayed note."));

        final JFXTextArea bigbox = new JFXTextArea();

        final TextField subjbox = new TextField(); //needs to be restricted to alphanumeric characters
        subjbox.setPromptText("Subject");

        // Setting up grid pane
        GridPane pane = new GridPane();
        final ProjectCombobox combobox = new ProjectCombobox(primaryStage, pane, combo);
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
        pane.add(preview, 3, 3);
        pane.add(submit, 3, 4);
        pane.add(delete, 3, 5);
        pane.add(bigbox, 0, 2, 2, 5);
        pane.add(subjbox, 0, 1, 2, 1);
        pane.setHgap(2);
        pane.setVgap(2);

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
                primaryStage.setTitle(combobox.selectProject(combo.getSelectionModel().getSelectedItem().toString()));
                combo.setTooltip(new Tooltip("Click to pick another project."));
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (bigbox.getText().trim().length() != 0) {
                    String subj = null;
                    Pattern p = Pattern.compile("[^a-zA-Z0-9]");
                    boolean noteFound = false;
                    if (subjbox.getText().trim().length() != 0 && !p.matcher(subjbox.getText()).find()) {
                        subj = subjbox.getText(); //need to restrict to alphanumeric characters only.
                    } else {
                        System.out.println("Must enter alphanumeric subject.");
                        return;
                    }
                    String content = bigbox.getText();
                    content.replaceAll("(?!\\r)\\n", "\r\n");
                    for (Note n : combobox.current_project.notes) {
                        if (subj.equals(n.subject)) {
                            noteFound = true;
                        }
                    }
                    if (noteFound) {
                        System.out.println("note already exists");
                        //overwrite existing note?
                    } else {
                        combobox.current_project.addNote(content, subj, true);
                    }
                } else {
                    System.out.println("No content to save.");
                }
            }
        });

        String titleText;
        if (combobox.current_project != null) {
            titleText = combobox.current_project.name;
        } else {
            titleText = "Lemon Notes";
        }

        //save prompt on closing program (finally!)
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (bigbox.getText().trim().length() != 0) {
                    Alert saveprompt = new Alert(Alert.AlertType.CONFIRMATION);
                    saveprompt.setTitle("Lemon Notes");
                    saveprompt.setContentText("Save your changes before closing?");
                    ButtonType yes = new ButtonType("Yes");
                    ButtonType no = new ButtonType("No");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    saveprompt.getButtonTypes().setAll(yes, no, cancel);
                    Optional<ButtonType> choice = saveprompt.showAndWait();

                    if (choice.get() == yes) {
                        String subj = null;
                        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
                        boolean noteFound = false;
                        if (subjbox.getText().trim().length() != 0 && !p.matcher(subjbox.getText()).find()) {
                            subj = subjbox.getText(); //need to restrict to alphanumeric characters only.
                        } else {
                            System.out.println("Must enter alphanumeric subject.");
                            Alert ngname = new Alert(Alert.AlertType.ERROR);
                            ngname.setTitle("Lemon Notes");
                            ngname.setHeaderText(null);
                            ngname.setContentText("You must have an alphanumeric subject in the subject field to save.");
                            ngname.showAndWait();
                            event.consume();
                            return;
                        }
                        String content = bigbox.getText();
                        content.replaceAll("(?!\\r)\\n", "\r\n");
                        for (Note n : combobox.current_project.notes) {
                            if (subj.equals(n.subject)) {
                                noteFound = true;
                            }
                        }
                        if (noteFound) {
                            System.out.println("note already exists");
                            //overwrite existing note?
                        } else {
                            combobox.current_project.addNote(content, subj, true);
                        }
                        primaryStage.close();
                    } else if (choice.get() == no) {
                        primaryStage.close();
                    } else {
                        event.consume();
                        return;
                    }
                }
            }
        });

        // Adding pane the the scene
        final Scene scene = new Scene(pane, 600, 250);
        primaryStage.setTitle(titleText);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
