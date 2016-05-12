import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.fxml.MigPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by Michael K. on 3/8/2016.
 */
public class VisualSettings extends Coggie {
    /*Should allow the user to select the color for the Background, the text box, the subject box, and potentially an image of their choosing.
    * The ability to have the above components be themed according to the current date (applying certain themes if it is a holiday, etc.)
    * should also be implemented.
    * */
    private static final int DIALOG_WIDTH = 295;
    private static final int DIALOG_HEIGHT = 540;


    private final Stage visualSettingsDialog;
    private final Stage primaryStage;
    private final Pane primaryPane;

    private Label background;
    private JFXColorPicker background_color_chooser;
    private Label subjectBox;
    private JFXColorPicker subject_box_color_chooser;
    private Label textBox;
    private JFXColorPicker text_box_color_chooser;

    private Label project_box;
    private JFXColorPicker project_box_color_chooser;
    private Label note_box;
    private JFXColorPicker note_box_color_chooser;
    private Label cog_wheel;
    private JFXColorPicker cog_wheel_background_color_chooser;

    private Label all_options;
    private RadioButton themed;

    private JFXButton cancelButton;
    private JFXButton applyButton;

    private JFXTextArea main_text_area;
    private javafx.scene.control.TextField subject_box;
    private ProjectCombobox project_combo_box;
    private NoteComboBox note_combo_box;
    private CogWheel cogwheel;

    public VisualSettings(Stage primaryStage, Pane mainPane, JFXTextArea bigBox, javafx.scene.control.TextField subjBox, ProjectCombobox comboBox, NoteComboBox noteComboBox, CogWheel cogWheel){
        if(primaryStage != null)
            this.primaryStage = primaryStage;
        else
            throw new NullPointerException("Primary Stage is null");
        if(mainPane != null)
            this.primaryPane = mainPane;
        else
            throw new NullPointerException("Main Pane is null");
        if(bigBox != null)
            this.main_text_area = bigBox;
        else
            throw new NullPointerException("TextBox is null");
        if(subjBox != null)
            this.subject_box = subjBox;
        else
            throw new NullPointerException("SubjectBox is null");
        if(comboBox != null)
            this.project_combo_box = comboBox;
        else
            throw new NullPointerException("ProjectComboBox is null");
        if(noteComboBox != null)
            this.note_combo_box = noteComboBox;
        else
            throw new NullPointerException("NoteComboBox is null");
        if(cogWheel != null)
            this.cogwheel = cogWheel;
        else
            throw new NullPointerException("CogWheel is null");

        visualSettingsDialog = new Stage();
        visualSettingsDialog.setTitle("Visual Settings");
        visualSettingsDialog.initStyle(StageStyle.UTILITY);
        visualSettingsDialog.initModality(Modality.APPLICATION_MODAL);
        visualSettingsDialog.initOwner(primaryStage);

        MigPane pane = createLayout();
        pane.setId("MainVisualPane");
        Scene visual_settings_scene = new Scene(pane, DIALOG_WIDTH, DIALOG_HEIGHT);
        visualSettingsDialog.setScene(visual_settings_scene);
    }

    public void show(){

        visualSettingsDialog.setX(primaryStage.getX());
        visualSettingsDialog.setY(primaryStage.getY());
        visualSettingsDialog.show();
    }

    private MigPane createLayout(){
        MigPane mainPane = new MigPane();
        mainPane.setId("MainVisualPaneBuilder");
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        background = new Label("Main Background Color");
        background.setId("BackgroundLabel");
        /*String temp = primaryPane.getStyle();
        double r = Double.parseDouble(temp.substring(temp.indexOf("(") +1, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        double g = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        double b = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        double o = Double.parseDouble(temp.substring(0, temp.indexOf(")")));
        background_color_chooser = new JFXColorPicker(new Color(r, g, b, o));*/
        background_color_chooser = new JFXColorPicker();
        background_color_chooser.setBackground(primaryPane.getBackground());

        textBox = new Label("Main Text Box Background Color");
        textBox.setId("MainTextBox");
        /*temp = main_text_area.getStyle();
        r = Double.parseDouble(temp.substring(temp.indexOf("(") +1, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        g = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        b = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        o = Double.parseDouble(temp.substring(0, temp.indexOf(")")));
        text_box_color_chooser = new JFXColorPicker(new Color(r, g, b, o));*/
        text_box_color_chooser = new JFXColorPicker();
        text_box_color_chooser.setBackground(main_text_area.getBackground());


        subjectBox = new Label("Subject Box Background Color");
        subjectBox.setId("SubjectBox");
        /*temp = subject_box.getStyle();
        r = Double.parseDouble(temp.substring(temp.indexOf("(") +1, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        g = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        b = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        o = Double.parseDouble(temp.substring(0, temp.indexOf(")")));
        subject_box_color_chooser = new JFXColorPicker(new Color(r, g, b, o));*/
        subject_box_color_chooser = new JFXColorPicker();
        subject_box_color_chooser.setBackground(subject_box.getBackground());

        project_box = new Label("Project Box Background Color");
        project_box.setId("ProjectBox");
        /*temp = project_combo_box.comboBox.getStyle();
        r = Double.parseDouble(temp.substring(temp.indexOf("(") +1, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        g = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        b = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        o = Double.parseDouble(temp.substring(0, temp.indexOf(")")));
        project_box_color_chooser = new JFXColorPicker(new Color(r, g, b, o));*/
        project_box_color_chooser = new JFXColorPicker();
        project_box_color_chooser.setBackground(project_combo_box.comboBox.getBackground());

        note_box = new Label("Note Box Background Color");
        note_box.setId("NoteBox");
        /*temp = note_combo_box.comboBox.getStyle();
        r = Double.parseDouble(temp.substring(temp.indexOf("(") +1, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        g = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        b = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        o = Double.parseDouble(temp.substring(0, temp.indexOf(")")));
        note_box_color_chooser = new JFXColorPicker(new Color(r, g, b, o));*/
        note_box_color_chooser = new JFXColorPicker();
        note_box_color_chooser.setBackground(note_combo_box.comboBox.getBackground());

        cog_wheel = new Label("Menu Bar Background Color");
        cog_wheel.setId("MenuBar");
        /*temp = cogwheel.menuBar.getStyle();
        r = Double.parseDouble(temp.substring(temp.indexOf("(") +1, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        g = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        b = Double.parseDouble(temp.substring(0, temp.indexOf(",")))/255;
        temp = temp.substring(temp.indexOf(",") + 2);
        o = Double.parseDouble(temp.substring(0, temp.indexOf(")")));
        cog_wheel_background_color_chooser = new JFXColorPicker(new Color(r, g, b, o));*/
        cog_wheel_background_color_chooser = new JFXColorPicker();
        cog_wheel_background_color_chooser.setBackground(cogwheel.menuBar.getBackground());

        all_options = new Label("Apply a Theme?");
        all_options.setId("AllOptionsLabel");

        themed = new JFXRadioButton("Apply a Theme?");

        MigPane background_options_pane = new MigPane();
        background_options_pane.setId("BackgroundOptionsPaneBuilder");
        background_options_pane.add(background);
        background_options_pane.add(background_color_chooser);
        background_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane background_pane = new TitledPane("Background Options", background_options_pane);
        background_pane.setId("BackgroundOptionsPane");

        MigPane text_box_options_pane = new MigPane();
        text_box_options_pane.setId("TextBoxOptionsPaneBuilder");
        text_box_options_pane.add(textBox);
        text_box_options_pane.add(text_box_color_chooser);
        text_box_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane text_box_pane = new TitledPane("Text Box Options", text_box_options_pane);
        text_box_pane.setId("BackgroundOptionsPane");

        MigPane subject_box_options_pane = new MigPane();
        subject_box_options_pane.setId("SubjectBoxOptionsPaneBuilder");
        subject_box_options_pane.add(subjectBox);
        subject_box_options_pane.add(subject_box_color_chooser);
        subject_box_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane subject_box_pane = new TitledPane("Subject Box Options", subject_box_options_pane);
        subject_box_pane.setId("SubjectBoxOptionsPane");

        MigPane project_box_options_pane = new MigPane();
        project_box_options_pane.setId("ProjecttBoxOptionsPaneBuilder");
        project_box_options_pane.add(project_box);
        project_box_options_pane.add(project_box_color_chooser);
        project_box_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane project_box_pane = new TitledPane("Project Box Options", project_box_options_pane);
        project_box_pane.setId("ProjectBoxOptionsPane");

        MigPane note_box_options_pane = new MigPane();
        note_box_options_pane.setId("NoteBoxOptionsPaneBuilder");
        note_box_options_pane.add(note_box);
        note_box_options_pane.add(note_box_color_chooser);
        note_box_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane note_box_pane = new TitledPane("Note Box Options", note_box_options_pane);
        note_box_pane.setId("NoteBoxOptionsPane");

        MigPane cog_wheel_options_pane = new MigPane();
        cog_wheel_options_pane.setId("CogWheelOptionsPaneBuilder");
        cog_wheel_options_pane.add(cog_wheel);
        cog_wheel_options_pane.add(cog_wheel_background_color_chooser);
        cog_wheel_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane cog_wheel_pane = new TitledPane("Menu Box Options", cog_wheel_options_pane);
        cog_wheel_pane.setId("CogWheelOptionsPane");


        MigPane all_options_pane = new MigPane();
        all_options_pane.setId("BackgroundOptionsPaneBuilder");
        all_options_pane.add(all_options);
        all_options_pane.add(themed);
        all_options_pane.setPrefSize(DIALOG_WIDTH, mainPane.getPrefHeight()/2);
        TitledPane pane_for_all = new TitledPane("Theme Options", all_options_pane);
        pane_for_all.setId("AllOptionsPane");

        cancelButton = new JFXButton("Cancel");
        cancelButton.setId("CancelButton");
        cancelButton.setButtonType(JFXButton.ButtonType.FLAT);
        cancelButton.setBackground(new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(0), new javafx.geometry.Insets(0))));
        applyButton = new JFXButton("Apply");
        applyButton.setId("ApplyButton");
        applyButton.setButtonType(JFXButton.ButtonType.FLAT);
        applyButton.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), new javafx.geometry.Insets(0))));

        MigPane buttonPane = new MigPane();
        buttonPane.setId("ButtonFontPane");
        buttonPane.add(cancelButton);
        buttonPane.add(applyButton);

        setActionListeners();

        mainPane.add(background_pane, "wrap");
        mainPane.add(text_box_pane, "wrap");
        mainPane.add(subject_box_pane, "wrap");
        mainPane.add(project_box_pane, "wrap");
        mainPane.add(note_box_pane, "wrap");
        mainPane.add(cog_wheel_pane, "wrap");
        mainPane.add(pane_for_all, "wrap");
        mainPane.add(buttonPane, "align right");

        return mainPane;
    }

    private void setActionListeners(){
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color chosen_for_background = background_color_chooser.getValue();
                primaryPane.setBackground(new Background(new BackgroundFill(chosen_for_background, CornerRadii.EMPTY, Insets.EMPTY)));

                Color chosen_for_subject_box = subject_box_color_chooser.getValue();
                cogwheel.subject_box.setBackground(new Background(new BackgroundFill(chosen_for_subject_box, CornerRadii.EMPTY, Insets.EMPTY)));

                Color chosen_for_text_box = text_box_color_chooser.getValue();
                cogwheel.main_text_area.setBackground(new Background(new BackgroundFill(chosen_for_text_box, CornerRadii.EMPTY, Insets.EMPTY)));

                Color chosen_for_project_box = project_box_color_chooser.getValue();
                cogwheel.project_combo_box.comboBox.setBackground(new Background(new BackgroundFill(chosen_for_project_box, CornerRadii.EMPTY, Insets.EMPTY)));

                Color chosen_for_note_box = note_box_color_chooser.getValue();
                cogwheel.note_combo_box.comboBox.setBackground(new Background(new BackgroundFill(chosen_for_note_box, CornerRadii.EMPTY, Insets.EMPTY)));

                Color chosen_for_cog_wheel = cog_wheel_background_color_chooser.getValue();
                cogwheel.menuBar.setBackground(new Background(new BackgroundFill(chosen_for_cog_wheel, CornerRadii.EMPTY, Insets.EMPTY)));

                try {
                    File f = new File("Settings/color_settings.txt");
                    f.delete();
                    FileWriter fw = new FileWriter("Settings/color_settings.txt");
                    fw.write("background: " + background_color_chooser.getValue().getRed() + ", " + background_color_chooser.getValue().getGreen() +
                             ", " + background_color_chooser.getValue().getBlue() + ", " + background_color_chooser.getValue().getOpacity() + "\r\n");
                    fw.write("subject box: " + subject_box_color_chooser.getValue().getRed() + ", " + subject_box_color_chooser.getValue().getGreen() +
                            ", " + subject_box_color_chooser.getValue().getBlue() + ", " + subject_box_color_chooser.getValue().getOpacity() + "\r\n");
                    fw.write("main text box: " + text_box_color_chooser.getValue().getRed() + ", " + text_box_color_chooser.getValue().getGreen() +
                            ", " + text_box_color_chooser.getValue().getBlue() + ", " + text_box_color_chooser.getValue().getOpacity() + "\r\n");
                    fw.write("project box: " +  project_box_color_chooser.getValue().getRed() + ", " + project_box_color_chooser.getValue().getGreen() +
                            ", " + project_box_color_chooser.getValue().getBlue() + ", " + project_box_color_chooser.getValue().getOpacity() + "\r\n");
                    fw.write("note box: " +  note_box_color_chooser.getValue().getRed() + ", " + note_box_color_chooser.getValue().getGreen() +
                            ", " + note_box_color_chooser.getValue().getBlue() + ", " + note_box_color_chooser.getValue().getOpacity() + "\r\n");
                    fw.write("cog wheel: " +  cog_wheel_background_color_chooser.getValue().getRed() + ", " + cog_wheel_background_color_chooser.getValue().getGreen() +
                            ", " + cog_wheel_background_color_chooser.getValue().getBlue() + ", " + cog_wheel_background_color_chooser.getValue().getOpacity() + "\r\n");
                    fw.write("theme applied: no\r\n");
                    fw.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                visualSettingsDialog.close();
            }
        });
    }
}
