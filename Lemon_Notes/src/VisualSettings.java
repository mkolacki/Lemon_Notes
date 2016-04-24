import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.fxml.MigPane;

import javax.swing.*;
import java.awt.*;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.TextField;

import static javafx.geometry.Insets.EMPTY;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class VisualSettings extends Coggie {
    /*Should allow the user to select the color for the Background, the text box, the subject box, and potentially an image of their choosing.
    * The ability to have the above components be themed according to the current date (applying certain themes if it is a holiday, etc.)
    * should also be implemented.
    * */
    private static final int DIALOG_WIDTH = 325;
    private static final int DIALOG_HEIGHT = 360;

    private final Stage visualSettingsDialog;
    private final Stage primaryStage;
    private final Pane primaryPane;

    private Label background;
    private JFXTextField background_color;
    private Label subjectBox;
    private JFXTextField subject_box_color;
    private Label textBox;
    private JFXTextField text_box_color;
    private Label all_options;
    private JFXButton ellipses;
    private RadioButton themed;

    private JFXButton okButton;
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

        background = new Label("Main Background \nColor");
        background.setId("BackgroundLabel");
        background_color = new JFXTextField();
        background_color.setStyle(primaryPane.getStyle());
        background_color.setEditable(false);

        textBox = new Label("Main Text Box \nBackground Color");
        textBox.setId("MainTextBox");
        text_box_color = new JFXTextField();
        text_box_color.setDisable(true);
        text_box_color.setStyle(main_text_area.getStyle());

        subjectBox = new Label("Subject Box \nBackground Color");
        subjectBox.setId("SubjectBox");
        subject_box_color = new JFXTextField();
        subject_box_color.setDisable(true);
        subject_box_color.setStyle(subject_box.getStyle());

        all_options = new Label("Apply a Theme?");
        all_options.setId("AllOptionsLabel");

        themed = new JFXRadioButton("Apply a Theme?");

        ellipses = new JFXButton("...");

        MigPane background_options_pane = new MigPane();
        background_options_pane.setId("BackgroundOptionsPaneBuilder");
        background_options_pane.add(background);
        background_options_pane.add(background_color);
        background_options_pane.add(ellipses);
        background_options_pane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane background_pane = new TitledPane("Background Options", background_options_pane);
        background_pane.setId("BackgroundOptionsPane");

        MigPane text_box_options_pane = new MigPane();
        text_box_options_pane.setId("TextBoxOptionsPaneBuilder");
        text_box_options_pane.add(textBox);
        text_box_options_pane.add(text_box_color);
        text_box_options_pane.add(ellipses);
        text_box_options_pane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane text_box_pane = new TitledPane("Text Box Options", text_box_options_pane);
        text_box_pane.setId("BackgroundOptionsPane");

        MigPane subject_box_options_pane = new MigPane();
        subject_box_options_pane.setId("SubjectBoxOptionsPaneBuilder");
        subject_box_options_pane.add(subjectBox);
        subject_box_options_pane.add(subject_box_color);
        subject_box_options_pane.add(ellipses);
        subject_box_options_pane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane subject_box_pane = new TitledPane("Subject Box Options", subject_box_options_pane);
        subject_box_pane.setId("SubjectBoxOptionsPane");

        MigPane all_options_pane = new MigPane();
        all_options_pane.setId("BackgroundOptionsPaneBuilder");
        all_options_pane.add(all_options);
        all_options_pane.add(themed);
        all_options_pane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane pane_for_all = new TitledPane("Theme Options", all_options_pane);
        pane_for_all.setId("AllOptionsPane");

        okButton = new JFXButton("Ok");
        okButton.setId("OKButton");
        okButton.setButtonType(JFXButton.ButtonType.FLAT);
        okButton.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(0), new javafx.geometry.Insets(0))));
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
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);
        buttonPane.add(applyButton);

        setActionListeners();

        mainPane.add(background_pane, "wrap");
        mainPane.add(text_box_pane, "wrap");
        mainPane.add(subject_box_pane, "wrap");
        mainPane.add(pane_for_all, "wrap");
        mainPane.add(buttonPane, "align right");

        //mainPane.setStyle("-fx-background-color: rgba(255, 255, 100, 0.5);");
        return mainPane;
    }

    private void setActionListeners()
    {
     /*   subjFontSizeBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                subjLabel.setFont(new Font(subjLabel.getFont().getName(), subjFontSizeBox.getSelectionModel().getSelectedItem()));
            }
        });

        textFontSizeBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                textLabel.setFont(new Font(textLabel.getFont().getName(), textFontSizeBox.getSelectionModel().getSelectedItem()));
            }
        });

        subjFontStyleBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                subjLabel.setFont(new Font(subjFontStyleBox.getSelectionModel().getSelectedItem(), subjLabel.getFont().getSize()));
            }
        });

        textFontStyleBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                textLabel.setFont(new Font(textFontStyleBox.getSelectionModel().getSelectedItem(), textLabel.getFont().getSize()));
            }
        });

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                for(Node item: mainPane.getChildren())
                {
                    if(item.getId() != null && item.getId().equals("Big Box Area"))
                    {
                        JFXTextArea area = (JFXTextArea)item;
                        SelectionModel<String> style = textFontStyleBox.getSelectionModel();
                        SelectionModel<Integer> size = textFontSizeBox.getSelectionModel();
                        Font font;
                        if(size.getSelectedItem() != null && style.getSelectedItem() != null)
                            font = new Font(style.getSelectedItem(), size.getSelectedItem());
                        else if(size.getSelectedItem() == null)
                            font = new Font(style.getSelectedItem(), area.getFont().getSize());
                        else
                            font = new Font(area.getFont().getName(), size.getSelectedItem());
                        area.setFont(font);
                    }
                    else if(item.getId() != null && item.getId().equals("Subject Box"))
                    {
                        TextField field = (TextField)item;
                        SelectionModel<String> style = subjFontStyleBox.getSelectionModel();
                        SelectionModel<Integer> size = subjFontSizeBox.getSelectionModel();
                        Font font;
                        if(size.getSelectedItem() != null && style.getSelectedItem() != null)
                            font = new Font(style.getSelectedItem(), size.getSelectedItem());
                        else if(size.getSelectedItem() == null)
                            font = new Font(style.getSelectedItem(), field.getFont().getSize());
                        else
                            font = new Font(field.getFont().getName(), size.getSelectedItem());
                        field.setFont(font);
                    }
                }
                fontDialog.close();
            }
        });

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                fontDialog.close();
            }
        });*/
    }
}
