import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
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

    /**************
     * MODES
     ********************/
    private final SpeechMode speech = new SpeechMode();
    /*private final BasicCalculator mode1 = new BasicCalculator();
    private final BoldFormat mode2 = new BoldFormat();
    private final ItalicFormat mode3 = new ItalicFormat();*/

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
    private ArrayList<Mode> modes;

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
        bigBox.setId("Big Box Area");

        subjBox = new TextField(); //needs to be restricted to alphanumeric characters
        subjBox.setId("Subject Box");
        subjBox.setPromptText("Subject");

        modes = new ArrayList<Mode>();
        //SpeechMode mode4 = new SpeechMode();
        BasicCalculator mode1 = new BasicCalculator();
        BoldFormat mode2 = new BoldFormat();
        ItalicFormat mode3 = new ItalicFormat();
        modes.add(0, mode1);
        modes.add(1, mode2);
        modes.add(2, mode3);

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

                File settings_directory = new File("Settings");
                Boolean made_Settings_directory = false;
                try {
                    made_Settings_directory = settings_directory.mkdirs();

                } catch (SecurityException se) {
                    System.out.println("Unsuccessful attempt to make Directory --- Settings:\n" + se.toString());
                }

                try {
                    FileWriter fw = new FileWriter("Settings/color_settings.txt");
                    fw.write("background: 0, 0, 0, 0\n");
                    fw.write("subject box: 0, 0, 0, 0\n");
                    fw.write("main text box: 0, 0, 0, 0\n");
                    fw.write("project box: 0, 0, 0, 0\n");
                    fw.write("note box: 0, 0, 0, 0\n");
                    fw.write("cog wheel: 0, 0, 0, 0\n");
                    fw.write("theme applied: no\n");
                    fw.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }

                try{
                    FileWriter fw = new FileWriter("Settings/last_open_project.txt");
                    fw.write(result.get());
                    fw.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }


            } else {
                System.out.println("Make default directory.");
                comboBox.addAProject("default");

                File settings_directory = new File("Settings");
                Boolean made_Settings_directory = false;
                try {
                    made_Settings_directory = settings_directory.mkdirs();

                } catch (SecurityException se) {
                    System.out.println("Unsuccessful attempt to make Directory --- Settings:\n" + se.toString());
                }

                try {
                    FileWriter fw = new FileWriter("Settings/color_settings.txt");
                    fw.write("background: 0, 0, 0, 0\n");
                    fw.write("subject box: 0, 0, 0, 0\n");
                    fw.write("main text box: 0, 0, 0, 0\n");
                    fw.write("project box: 0, 0, 0, 0\n");
                    fw.write("note box: 0, 0, 0, 0\n");
                    fw.write("cog wheel: 0, 0, 0, 0\n");
                    fw.write("theme applied: no\n");
                    fw.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }

                try{
                    FileWriter fw = new FileWriter("Settings/last_open_project.txt");
                    fw.write("default");
                    fw.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }

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

            File settings = new File("Settings/last_open_project.txt");

            try{
                Scanner sc = new Scanner(settings);
                String last_project = "";
                while(sc.hasNextLine()){
                    last_project = last_project + sc.nextLine();
                    if(sc.hasNextLine()){
                        last_project = last_project + "\n";
                    }
                }

                for(Project p : comboBox.projects){
                    if(p.name.equals(last_project)){
                        comboBox.selectProject(last_project);
                    }
                }

            }catch (FileNotFoundException fnfe){
                fnfe.printStackTrace();
            }
            comboBox.comboBox.setPromptText(comboBox.current_project.name);
            note_combo_box = new NoteComboBox(comboBox.current_project, noteCombo);
        }

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
        bigBox.setBackground(new Background(new BackgroundFill(new Color(1, 1, .5, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        subjBox.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        comboBox.comboBox.setBackground(new Background(new BackgroundFill(new Color(.6, .6, .75, .5), CornerRadii.EMPTY, Insets.EMPTY)));
        note_combo_box.comboBox.setBackground(new Background(new BackgroundFill(new Color(1, .4, .4, 1.0), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setBackground(new Background(new BackgroundFill(new Color(.8, .8, .8, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        /*bigBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        subjBox.setStyle("-fx-background-color: rgba(150, 150, 200, .5);");
        combo.setStyle("-fx-background-color: rgba(54, 255, 65, 1.0);");
        noteCombo.setStyle("-fx-background-color: rgba(255, 90, 100, 1.0);");
        menu.setStyle("-fx-background-color: rgba(200, 200, 200, 0.5);");*/
        cogWheel = new CogWheel(primaryStage, pane, menu, menu1, bigBox, subjBox, comboBox, note_combo_box);

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

                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Lemon Notes");
                                    alert.setContentText("A file already exists with this name. If you proceed it will be overwritten. Is this OK?");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK){
                                        // ... user chose OK
                                        comboBox.current_project.addNote(content, subj, false);
                                        noteModified = false;
                                    } else {
                                        // ... user chose CANCEL or closed the dialog
                                        return;
                                    }


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
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Lemon Notes");
                                        alert.setContentText("A file already exists with this name. If you proceed it will be overwritten. Is this OK?");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK){
                                            // ... user chose OK
                                            comboBox.current_project.addNote(content, subj, false);
                                            noteModified = false;
                                        } else {
                                            // ... user chose CANCEL or closed the dialog
                                            return;
                                        }
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
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Lemon Notes");
                                alert.setContentText("A file already exists with this name. If you proceed it will be overwritten. Is this OK?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK){
                                    // ... user chose OK
                                    comboBox.current_project.addNote(content, subj, false);
                                    noteModified = false;
                                } else {
                                    // ... user chose CANCEL or closed the dialog
                                    return;
                                }
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
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Lemon Notes");
                        alert.setContentText("A file already exists with this name. If you proceed it will be overwritten. Is this OK?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            // ... user chose OK
                            comboBox.current_project.addNote(content, subj, false);
                            noteModified = false;
                        } else {
                            // ... user chose CANCEL or closed the dialog
                            return;
                        }
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

        //preview the current note
        //Possible soultions:
        // Read through the note, split into formatted/unformatted pieces, add to a list, read in via for loop
        preview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                String family = "Helvetica";
                double size = 12;

                TextFlow textFlow = new TextFlow();
                textFlow.setLayoutX(12);
                textFlow.setLayoutY(12);

                /*Text text1 = new Text("Hello ");
                text1.setFont(Font.font(family, size));
                text1.setFill(Color.RED);
                Text text2 = new Text("Bold");
                text2.setFill(Color.ORANGE);
                text2.setFont(Font.font(family, FontWeight.BOLD, size));
                Text text3 = new Text(" World");
                text3.setFill(Color.GREEN);
                text3.setFont(Font.font(family, FontPosture.ITALIC, size));
                textFlow.getChildren().addAll(text1, text2, text3);*/


                ArrayList<Text> noteBits = new ArrayList<Text>();
                String fullNote = Mode.updateAllModes(bigBox.getText());

                //substring is from current spot until next tag.
                //save this substring, format if needed and add to the list.
                //cut the substring from the main string
                //rinse, repeat

                //potential solution for nested tags: stack

                while (fullNote != null && fullNote.length() != 0) {
                    if (fullNote.indexOf("<") != -1) {
                        if (fullNote.indexOf("<b>") == fullNote.indexOf("<")) {
                            if (fullNote.indexOf("<b>") == 0) {
                                if (fullNote.indexOf("</b>") != -1) {
                                    Text t = new Text(fullNote.substring(3,fullNote.indexOf("</b>")));
                                    t.setFont(Font.font(family, FontWeight.BOLD, size));
                                    noteBits.add(t);
                                    //System.out.println("a");
                                    fullNote = fullNote.substring(fullNote.indexOf("</b>")+4);
                                    //System.out.println("b");
                                } else {
                                    Text t = new Text(fullNote);
                                    noteBits.add(t);
                                    break;
                                }
                            } else {
                                Text t = new Text(fullNote.substring(0,fullNote.indexOf("<b>")));
                                noteBits.add(t);
                                fullNote = fullNote.substring(fullNote.indexOf("<b>"));
                            }
                        } else if (fullNote.indexOf("<i>") == fullNote.indexOf("<")) {
                            if (fullNote.indexOf("<i>") == 0) {
                                if (fullNote.indexOf("</i>") != -1) {
                                    Text t = new Text(fullNote.substring(3, fullNote.indexOf("</i>")));
                                    t.setFont(Font.font(family, FontPosture.ITALIC, size));
                                    noteBits.add(t);
                                    //System.out.println("a");
                                    fullNote = fullNote.substring(fullNote.indexOf("</i>") + 4);
                                    //System.out.println("b");
                                } else {
                                    Text t = new Text(fullNote);
                                    noteBits.add(t);
                                    break;
                                }
                            } else {
                                Text t = new Text(fullNote.substring(0, fullNote.indexOf("<i>")));
                                noteBits.add(t);
                                fullNote = fullNote.substring(fullNote.indexOf("<i>"));
                            }
                        } else if (fullNote.indexOf("<c>")==fullNote.indexOf("<")) {
                            if (fullNote.indexOf("<c>") == 0) {
                                if (fullNote.indexOf("</c>") != 1) {
                                    if (fullNote.indexOf("{color=0x") == 3 && fullNote.indexOf("}") == 18) {
                                        Text t = new Text(fullNote.substring(19, fullNote.indexOf("</c>")));
                                        t.setFill(Color.web(fullNote.substring(fullNote.indexOf("{color=0x") + 7 , fullNote.indexOf("}"))));
                                        noteBits.add(t);
                                        fullNote = fullNote.substring(fullNote.indexOf("</c>") + 4);
                                    } else {
                                        Text t = new Text(fullNote.substring(0, fullNote.indexOf("</c>") + 4));
                                        noteBits.add(t);
                                        fullNote = fullNote.substring(fullNote.indexOf("</c>") + 4);
                                    }
                                } else {
                                    Text t = new Text(fullNote);
                                    noteBits.add(t);
                                    break;
                                }
                            }
                        }else if (fullNote.indexOf("<calc>") == fullNote.indexOf("<")){
                            if (fullNote.indexOf("<calc>") == 0){
                                if(fullNote.indexOf("</calc>") != -1){
                                    Text t = new Text(fullNote.substring(6, fullNote.indexOf("</calc>")));
                                    t = modes.get(0).preview(t);
                                    noteBits.add(t);
                                    System.out.println("a");
                                    fullNote = fullNote.substring(fullNote.indexOf("</calc>") + 7);
                                    System.out.println("b");
                                } else {
                                    Text t = new Text(fullNote);
                                    noteBits.add(t);
                                    break;
                                }
                            }else {
                                Text t = new Text(fullNote.substring(0, fullNote.indexOf("<calc>")));
                                noteBits.add(t);
                                fullNote = fullNote.substring(fullNote.indexOf("<calc>"));
                            }
                        }else { //temporary fix. this should be changed so people can use < at the start of a text if they wish to do so.
                            Text t = new Text(fullNote.substring(0,1));
                            noteBits.add(t);
                            fullNote = fullNote.substring(1);
                        }
                    } else {
                        Text t = new Text(fullNote);
                        noteBits.add(t);
                        break;
                    }
                }

                for (Text t : noteBits) {
                    textFlow.getChildren().add(t);
                }

                Group group = new Group(textFlow);
                Scene scene = new Scene(group, 500, 150, Color.WHITE);
                stage.setTitle("Testing Preview");
                stage.setScene(scene);
                stage.show();
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
                            File last_project = new File("Settings/last_open_project.txt");
                            if(last_project.exists()){
                                last_project.delete();
                            }

//                            last_project = new File("Settings/last_open_project.txt");

                            try{
                                FileWriter fw = new FileWriter("Settings/last_open_project.txt");
                                fw.write(comboBox.current_project.name);
                                fw.close();
                            }catch (IOException ex){
                                ex.printStackTrace();
                            }

                            primaryStage.close();
                        } else if (choice.get() == no) {
                            File last_project = new File("Settings/last_open_project.txt");
                            if(last_project.exists()){
                                last_project.delete();
                            }

//                            last_project = new File("Settings/last_open_project.txt");

                            try{
                                FileWriter fw = new FileWriter("Settings/last_open_project.txt");
                                fw.write(comboBox.current_project.name);
                                fw.close();
                            }catch (IOException ex){
                                ex.printStackTrace();
                            }
                            primaryStage.close();
                        } else {
                            event.consume();
                        }
                    }
                } else {
                    File last_project = new File("Settings/last_open_project.txt");
                    if(last_project.exists()){
                        last_project.delete();
                    }

                    //last_project = new File("Settings/last_open_project.txt");

                    try{
                        FileWriter fw = new FileWriter("Settings/last_open_project.txt");
                        fw.write(comboBox.current_project.name);
                        fw.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
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
        /*String titleText;
        if (comboBox.current_project != null) {
            titleText = comboBox.current_project.name;
        } else {
            titleText = "Lemon Notes";
        }*/

        // Adding pane the the scene
        scene = new Scene(pane, 600, 250);
        primaryStage.setTitle("Lemon Notes");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

        // Setup color
        File settings = new File("Settings/color_settings.txt");
        try{
            Scanner sc = new Scanner(settings);
            String temp = "";
            while(sc.hasNextLine()){
                temp = temp + ", " + sc.nextLine();
            }

            while(temp.length() != 0){
                if(temp.contains("background")){
                    temp = temp.substring(temp.indexOf(":") + 2);
                    double r = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double o = Double.parseDouble(temp.substring(0, temp.indexOf(", ")));
                    pane.setBackground(new Background(new BackgroundFill(new Color(r, g, b, o), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if(temp.contains("subject box")){
                    temp = temp.substring(temp.indexOf(":") + 2);
                    double r = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double o = Double.parseDouble(temp.substring(0, temp.indexOf(", ")));
                    subjBox.setBackground(new Background(new BackgroundFill(new Color(r, g, b, o), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if(temp.contains("main text box")){
                    temp = temp.substring(temp.indexOf(":") + 2);
                    double r = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double o = Double.parseDouble(temp.substring(0, temp.indexOf(", ")));
                    bigBox.setBackground(new Background(new BackgroundFill(new Color(r, g, b, o), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if(temp.contains("project box")){
                    temp = temp.substring(temp.indexOf(":") + 2);
                    double r = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double o = Double.parseDouble(temp.substring(0, temp.indexOf(", ")));
                    comboBox.comboBox.setBackground(new Background(new BackgroundFill(new Color(r, g, b, o), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if(temp.contains("note box")){
                    temp = temp.substring(temp.indexOf(":") + 2);
                    double r = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double o = Double.parseDouble(temp.substring(0, temp.indexOf(", ")));
                    note_combo_box.comboBox.setBackground(new Background(new BackgroundFill(new Color(r, g, b, o), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if(temp.contains("cog wheel")){
                    temp = temp.substring(temp.indexOf(":") + 2);
                    double r = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")));
                    temp = temp.substring(temp.indexOf(",") + 2);
                    double o = Double.parseDouble(temp.substring(0, temp.indexOf(", ")));
                    menu.setBackground(new Background(new BackgroundFill(new Color(r, g, b, o), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if(temp.contains("theme applied")){
                    //eventually handle this differently
                    temp = "";
                }
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        File fontSettings = new File("Settings/font_settings.txt");
        try
        {
            Scanner sc = new Scanner(fontSettings);
            String name;
            while(sc.hasNextLine())
            {
                name = sc.next();
                if(name.equals("subject_box"))
                {
                    double size = sc.nextDouble();
                    String style = sc.nextLine();
                    Font font = new Font(style.trim(), size);
                    subjBox.setFont(font);
                }
                else
                {
                    double size = sc.nextDouble();
                    String style = sc.nextLine();
                    Font font = new Font(style.trim(), size);
                    bigBox.setFont(font);
                }
            }
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }

    }
}
