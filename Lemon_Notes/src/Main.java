import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private JFXButton npbutton;
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
    private NoteComboBox note_combo_box;
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
        npbutton = new JFXButton("New Project");
        npbutton.setTooltip(new Tooltip("Click to make a new project directory."));
        npbutton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), new Insets(0,0,0,0))));
        npbutton.setButtonType(JFXButton.ButtonType.RAISED);

        // Creating Project selection combo box
        combo = new JFXComboBox<String>();
        combo.setTooltip(new Tooltip("Click to pick another project."));

        //Note selection comboBox
        noteCombo = new JFXComboBox<String>();

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
        /*comboBox.addAProject("Project 1");
        comboBox.addAProject("Project 2");
        comboBox.addAProject("Project 3");
        comboBox.addAProject("Test");
        comboBox.addAProject("Rest");*/

        //create project directory or load directory
        File projDir = new File("Projects/");
        if(!projDir.exists() || projDir.list().length == 0) {
            Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
            TextInputDialog firstTime = new TextInputDialog();
            firstTime.setTitle("Lemon Notes");
            firstTime.setHeaderText("Welcome to Lemon Notes");
            firstTime.setContentText("Enter a project name?");
            Optional<String> result = firstTime.showAndWait();
            if (result.isPresent() && result.get().trim().length() > 0 && !p.matcher(result.get()).find()){
                System.out.println("Project name: " + result.get());
                comboBox.addAProject(result.get());
            } else {
                System.out.println("Make default directory.");
                comboBox.addAProject("default");
            }
        } else {
            //read in directory names in Projects folder.
            String[] projList = projDir.list();
            for (String n : projList){
                if (new File("Projects/" + n).isDirectory()) {
                    System.out.println("recognized directory " + n);
                    comboBox.addAProject(n);

                    //
                    // we will need to adjust which project is the 'current project' here
                    //- Michael K.
                    //
                }
            }

        }

        note_combo_box = new NoteComboBox(comboBox.current_project, noteCombo);

        pane.add(comboBox.comboBox, 0, 0);
        pane.add(note_combo_box.comboBox, 1, 0);
        pane.add(menu, 2, 0);
        pane.add(npbutton, 4, 0);
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
        npbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
                //primaryStage.setTitle("Hey, you clicked a button!");
                TextInputDialog newProject = new TextInputDialog();
                newProject.setTitle("Lemon Notes");
                newProject.setHeaderText("New Project");
                newProject.setContentText("Please enter a new project name.");
                Optional<String> result = newProject.showAndWait();
                if (result.isPresent()){
                    if (result.get().trim().length() > 0 && !p.matcher(subjBox.getText()).find()){
                        System.out.println("New project name: " + result.get());
                        comboBox.addAProject(result.get());
                    }
                    else {
                        Alert ngname = new Alert(Alert.AlertType.ERROR);
                        ngname.setTitle("Lemon Notes");
                        ngname.setHeaderText(null);
                        ngname.setContentText("You didn't enter a valid name.");
                        ngname.showAndWait();
                        event.consume();
                        return;
                    }
                    //comboBox.addAProject(result.get());
                } else {
                    System.out.println("Cancelled.");
                }
            }
        });

        combo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(combo.getSelectionModel().getSelectedItem() != null) {
                    //primaryStage.setTitle(comboBox.selectProject(combo.getSelectionModel().getSelectedItem().toString()).name);
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
                                Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
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
                            subjBox.clear();
                        } else if (choice.get() == no) {
                            bigBox.clear();
                            subjBox.clear();
                        }
                    } else {
                        bigBox.clear();
                        subjBox.clear();
                    }
                    note_combo_box.changeProjects(comboBox.selectProject(combo.getSelectionModel().getSelectedItem().toString()));
                    //allows you to save the written note to another project
                    noteModified = false;
                }
            }
        });

        note_combo_box.comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (note_combo_box.comboBox.getSelectionModel().getSelectedItem() != null) {
                    Note n = comboBox.current_project.selectNote(note_combo_box.comboBox.getSelectionModel().getSelectedItem());
                    if (n != null) {
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
                                    Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
                                    boolean noteFound = false;
                                    if (subjBox.getText().trim().length() != 0 && !p.matcher(subjBox.getText()).find()) {
                                        subj = subjBox.getText(); //need to restrict to alphanumeric characters only.
                                    } else {
                                        System.out.println("Must enter alphanumeric subject.");
                                        return;
                                    }
                                    String content = bigBox.getText();
                                    content.replaceAll("(?!\\r)\\n", "\r\n");
                                    for (Note note : comboBox.current_project.notes) {
                                        if (subj.equals(note.subject)) {
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
                                subjBox.clear();
                                bigBox.clear();
                            } else if (choice.get() == no) {
                                subjBox.clear();
                                bigBox.clear();
                            }
                        } else {
                            subjBox.clear();
                            bigBox.clear();
                        }
                        subjBox.setText(n.subject);
                        bigBox.setText(n.note);
                        noteModified = false;
                    }
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
                            Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
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
                    Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
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
                        note_combo_box.resize();
                        noteModified = false;
                    }
                    bigBox.clear();
                    subjBox.clear();
                    noteModified = false;
                } else {
                    System.out.println("No content to save.");
                    bigBox.clear();
                    subjBox.clear();
                    noteModified = false;
                }
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(bigBox.getText().trim().length() != 0){
                    if(subjBox.getText().trim().length() != 0){
                        Boolean found_note = false;
                        Note note_to_remove = null;
                        for(Note n : comboBox.current_project.notes){
                            if(subjBox.getText().trim().equals(n.subject)){
                                note_to_remove = n;
                                found_note = true;
                            }
                        }
                        if(found_note && (note_to_remove != null)){
                            //delete note?
                            Alert delete_note = new Alert(Alert.AlertType.CONFIRMATION);
                            delete_note.setTitle("Lemon Notes");
                            delete_note.setHeaderText("Deleting previous note...");
                            delete_note.setContentText("Are you sure you'd like to continue?");
                            ButtonType yes = new ButtonType("Yes");
                            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            delete_note.getButtonTypes().setAll(yes, cancel);

                            Optional<ButtonType> choice = delete_note.showAndWait();
                            if (choice.get() == yes) {
                                comboBox.current_project.removeNote(note_to_remove);
                                note_combo_box.resize();
                                subjBox.clear();
                                bigBox.clear();
                                noteModified = false;
                            }
                        }else {
                            //not recognized as a previous note (currently). clear screen?
                            Alert clear_screen = new Alert(Alert.AlertType.CONFIRMATION);
                            clear_screen.setTitle("Lemon Notes");
                            clear_screen.setHeaderText("Clearing screen...");
                            clear_screen.setContentText("Are you sure you'd like to continue?");
                            ButtonType yes = new ButtonType("Yes");
                            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            clear_screen.getButtonTypes().setAll(yes, cancel);

                            Optional<ButtonType> choice = clear_screen.showAndWait();
                            if (choice.get() == yes) {
                                subjBox.clear();
                                bigBox.clear();
                                noteModified = false;
                            }
                        }
                    }else {
                        //not recognized as a previous note (currently). clear screen?
                        Alert clear_screen = new Alert(Alert.AlertType.CONFIRMATION);
                        clear_screen.setTitle("Lemon Notes");
                        clear_screen.setHeaderText("Clearing screen...");
                        clear_screen.setContentText("Are you sure you'd like to continue?");
                        ButtonType yes = new ButtonType("Yes");
                        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                        clear_screen.getButtonTypes().setAll(yes, cancel);

                        Optional<ButtonType> choice = clear_screen.showAndWait();
                        if (choice.get() == yes) {
                            subjBox.clear();
                            bigBox.clear();
                            noteModified = false;
                        }
                    }
                }else {
                    if(subjBox.getText().trim().length() != 0){
                        Boolean found_note = false;
                        Note note_to_remove = null;
                        for(Note n : comboBox.current_project.notes){
                            if(subjBox.getText().trim().equals(n.subject)){
                                note_to_remove = n;
                                found_note = true;
                            }
                        }
                        if(found_note && (note_to_remove != null)){
                            //delete note?
                            Alert delete_note = new Alert(Alert.AlertType.CONFIRMATION);
                            delete_note.setTitle("Lemon Notes");
                            delete_note.setHeaderText("Deleting previous note...");
                            delete_note.setContentText("Are you sure you'd like to continue?");
                            ButtonType yes = new ButtonType("Yes");
                            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            delete_note.getButtonTypes().setAll(yes, cancel);

                            Optional<ButtonType> choice = delete_note.showAndWait();
                            if (choice.get() == yes) {
                                comboBox.current_project.removeNote(note_to_remove);
                                note_combo_box.resize();
                                subjBox.clear();
                                bigBox.clear();
                                noteModified = false;
                            }
                        }else {
                            //not recognized as a previous note (currently). clear screen?
                            Alert clear_screen = new Alert(Alert.AlertType.CONFIRMATION);
                            clear_screen.setTitle("Lemon Notes");
                            clear_screen.setHeaderText("Clearing screen...");
                            clear_screen.setContentText("Are you sure you'd like to continue?");
                            ButtonType yes = new ButtonType("Yes");
                            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            clear_screen.getButtonTypes().setAll(yes, cancel);

                            Optional<ButtonType> choice = clear_screen.showAndWait();
                            if (choice.get() == yes) {
                                subjBox.clear();
                                bigBox.clear();
                                noteModified = false;
                            }
                        }
                    }else {
                        //not recognized as a previous note (currently). for now, just "clear screen" (its already clear, but this sets note modified to false)
                        subjBox.clear();
                        bigBox.clear();
                        noteModified = false;
                    }
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

        //check if the subject has been modified since the last save
        subjBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("User has modified the subject!");
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
                            Pattern p = Pattern.compile("[^a-zA-Z0-9_ ]");
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
                                ngname.setContentText("A note with this subject heading already exists.");
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
