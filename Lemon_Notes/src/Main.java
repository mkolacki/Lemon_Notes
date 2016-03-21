import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    private Stage primaryStage;
    private Scene scene;
    private GridPane pane;

    private JFXButton submit;
    private JFXButton button;
    private JFXButton preview;
    private JFXButton newNote;
    private JFXButton delete;

    private JFXTextArea bigBox;
    private JFXComboBox<String> combo;
    private JFXComboBox<String> noteCombo;

    private MenuBar menu;
    private Menu menu1;

    private TextField subjBox;
    private ProjectCombobox comboBox;
    private CogWheel cogWheel;

    boolean noteModified;

    /**
     * The method called by Application for starting up the display
     * @param primaryStage
     *      The stage in which the main view will be presented
     *
     */
    @Override
    public void start(final Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        createComponents();
        setupGrid();
        setEventHandlers();
        setupScene(pane);
    }

    /**
     * Creates the JFX components that will be added to the stage
     */
    private void createComponents()
    {
        // Creating a random button
        button = new JFXButton("Button");
        button.setTooltip(new Tooltip("It's a button!"));
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), new Insets(0,0,0,0))));
        button.setButtonType(JFXButton.ButtonType.RAISED);

        // Creating Project selection combo box
        combo = new JFXComboBox<String>();
        combo.setTooltip(new Tooltip("Click to pick another project."));

        //Note selection comboBox
        noteCombo = new JFXComboBox<String>();
        noteCombo.setTooltip(new Tooltip("Click to view a note in this project."));
        noteCombo.setPromptText("Notes");

        // Making a dropdown menu
        menu = new MenuBar();

        //Image cog_wheel = new Image(getClass().getResourceAsStream("cogicon.png"));
        menu1 = new Menu();

        // Creating note control buttons
        newNote = new JFXButton();
        Image new_note_graphic = new Image(getClass().getResourceAsStream("newpageicon.png"));
        newNote.setGraphic(new ImageView(new_note_graphic));
        newNote.setTooltip(new Tooltip("Clears the current screen"));

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

        bigBox = new JFXTextArea();

        subjBox = new TextField(); //needs to be restricted to alphanumeric characters
        subjBox.setPromptText("Subject");

    }

    /**
     * Setting up the format of the components on a grid
     */
    private void setupGrid()
    {
        // Setting up grid pane
        pane = new GridPane();
        comboBox = new ProjectCombobox(combo);
        comboBox.addAProject("Project 1");
        comboBox.addAProject("Project 2");
        comboBox.addAProject("Project 3");
        comboBox.addAProject("Test");
        comboBox.addAProject("Rest");

        pane.add(comboBox.comboBox, 0, 0);
        pane.add(noteCombo, 1, 0);
        pane.add(menu, 2, 0);
        pane.add(newNote, 4, 1);
        pane.add(preview, 4, 3);
        pane.add(submit, 4, 4);
        pane.add(delete, 4, 5);
        pane.add(bigBox, 0, 2, 3, 5);
        pane.add(subjBox, 0, 1, 3, 1);

        pane.setHgap(2);
        pane.setVgap(2);

        // Setting the general padding for the grid pane
        ColumnConstraints cons1 = new ColumnConstraints();
        pane.getColumnConstraints().add(cons1);

        cogWheel = new CogWheel(primaryStage, pane, menu, menu1);
        menu.getMenus().add(cogWheel.menu);
    }

    /**
     * Sets the event handlers for the components
     */
    private void setEventHandlers()
    {
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
                if(combo.getSelectionModel().getSelectedItem() != null) {
                    primaryStage.setTitle(comboBox.selectProject(combo.getSelectionModel().getSelectedItem().toString()));
                    //allows you to save the written note to another project
                    noteModified = true;
                }
            }
        });

        //clear current note functionality -- clears the note if there's things written in the box. Prompts user to save
        newNote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //prompt user to save here
                if (noteModified && bigBox.getText().trim().length() != 0) {
                    Alert saveprompt = new Alert(Alert.AlertType.CONFIRMATION);
                    saveprompt.setTitle("Lemon Notes");
                    saveprompt.setHeaderText("Clearing editor field...");
                    saveprompt.setContentText("Would you like to save before clearing the field?");
                    ButtonType yes = new ButtonType("Yes");
                    ButtonType no = new ButtonType("No");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    saveprompt.getButtonTypes().setAll(yes, no, cancel);

                    Optional<ButtonType> choice = saveprompt.showAndWait();
                    if (choice.get() == yes) {
                        if (bigBox.getText().trim().length() != 0) {
                            String subj;
                            Pattern p = Pattern.compile("[^a-zA-Z0-9]");
                            boolean noteFound = false;
                            if (subjBox.getText().trim().length() != 0 && !p.matcher(subjBox.getText()).find()) {
                                subj = subjBox.getText(); //need to restrict to alphanumeric characters only.
                            } else {
                                System.out.println("Must enter alphanumeric subject.");
                                return;
                            }
                            String content = bigBox.getText();
                            content.replaceAll("(?!\\r)\\n", "\r\n");
                            for (Note n : comboBox.current_project.notes) {
                                if (subj.equals(n.subject)) {
                                    noteFound = true;
                                }
                            }
                            if (noteFound) {
                                System.out.println("note already exists");
                                //overwrite existing note?
                            } else {
                                comboBox.current_project.addNote(content, subj, true);
                                noteModified = false;
                            }
                        } else {
                            System.out.println("No content to save.");
                        }
                        bigBox.clear();
                    } else if (choice.get() == no) {
                        bigBox.clear();
                    }
                } else {
                    bigBox.clear();
                }
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (bigBox.getText().trim().length() != 0) {
                    String subj;
                    Pattern p = Pattern.compile("[^a-zA-Z0-9]");
                    boolean noteFound = false;
                    if (subjBox.getText().trim().length() != 0 && !p.matcher(subjBox.getText()).find()) {
                        subj = subjBox.getText(); //need to restrict to alphanumeric characters only.
                    } else {
                        System.out.println("Must enter alphanumeric subject.");
                        return;
                    }
                    String content = bigBox.getText();
                    content.replaceAll("(?!\\r)\\n", "\r\n");
                    for (Note n : comboBox.current_project.notes) {
                        if (subj.equals(n.subject)) {
                            noteFound = true;
                        }
                    }
                    if (noteFound) {
                        System.out.println("note already exists");
                        //overwrite existing note?
                    } else {
                        comboBox.current_project.addNote(content, subj, true);
                        noteModified = false;
                    }
                } else {
                    System.out.println("No content to save.");
                }
            }
        });

        //check if textbox was modified since last save
        bigBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("User has modified this item!");
                noteModified = true;
            }
        });

        //save prompt on closing program
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (noteModified) {
                    if (bigBox.getText().trim().length() != 0) {
                        Alert saveprompt = new Alert(Alert.AlertType.CONFIRMATION);
                        saveprompt.setTitle("Lemon Notes");
                        saveprompt.setContentText("Save your changes before closing?");
                        ButtonType yes = new ButtonType("Yes");
                        ButtonType no = new ButtonType("No");
                        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                        saveprompt.getButtonTypes().setAll(yes, no, cancel);
                        Optional<ButtonType> choice = saveprompt.showAndWait();

                        if (choice.get() == yes) {
                            String subj;
                            Pattern p = Pattern.compile("[^a-zA-Z0-9]");
                            boolean noteFound = false;
                            if (subjBox.getText().trim().length() != 0 && !p.matcher(subjBox.getText()).find()) {
                                subj = subjBox.getText(); //need to restrict to alphanumeric characters only.
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
                            String content = bigBox.getText();
                            content.replaceAll("(?!\\r)\\n", "\r\n");
                            for (Note n : comboBox.current_project.notes) {
                                if (subj.equals(n.subject)) {
                                    noteFound = true;
                                }
                            }
                            if (noteFound) {
                                System.out.println("note already exists");
                                Alert ngname = new Alert(Alert.AlertType.ERROR);
                                ngname.setTitle("Lemon Notes");
                                ngname.setHeaderText(null);
                                ngname.setContentText("A note with this name already exists.");
                                ngname.showAndWait();
                                event.consume();
                                return;
                                //overwrite existing note?
                            } else {
                                comboBox.current_project.addNote(content, subj, true);
                            }
                            primaryStage.close();
                        } else if (choice.get() == no) {
                            primaryStage.close();
                        } else {
                            event.consume();
                        }
                    }
                } else {
                    primaryStage.close();
                }
            }
        });
    }

    /**
     * Setting up the primary stage
     * @param pane
     *  The GridPane to be added to the scene.
     */
    private void setupScene(GridPane pane)
    {
        String titleText;
        if (comboBox.current_project != null) {
            titleText = comboBox.current_project.name;
        } else {
            titleText = "Lemon Notes";
        }

        // Adding pane the the scene
        scene = new Scene(pane, 600, 250);
        primaryStage.setTitle(titleText);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
