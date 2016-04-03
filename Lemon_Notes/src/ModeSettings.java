import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;


/**
 * Created by Michael K. on 3/8/2016.
 */


public class ModeSettings extends Coggie {
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 380;

    private final Stage modeSettingsDialog;
    private final Stage primaryStage;
    private final Pane mainPane;

    //There should be one Label for each individual Mode
    //Format in mind for display:
    //Indicator: (whatever the indicator for the mode is)
    //Description: (brief but detailed description of whatever the mode is supposed to do/be able to do)
    //Format and Parameters: (what does the mode take and how does it take it)
    //Example: (an example of the mode with some input)

    //Note from Mike C: When I add more panels, the content gets cut off if we have too many open at once. I have to resize the window so that it becomes readable.
    //                  Should we make it so only one item stays opened at a time? Or I could try and figure out a scroll bar option.
    //                  Also, not a huge deal now but something we could keep in mind, once we have the separate modes figured out, we should see how we can
    //                  integrate them together in case someone wants bold and italicised text, or they want to make a calculation have red text in their note.

    private Label basicCalculator;
    private Label boldFormat;
    private Label italicFormat;

    //private Label otherModes;

    private JFXButton closeButton;

    public ModeSettings(Stage primaryStage, Pane mainPane){
        if(primaryStage != null)
            this.primaryStage = primaryStage;
        else
            throw new NullPointerException("No Primary Stage was provided");
        if(mainPane != null)
            this.mainPane = mainPane;
        else
            throw new NullPointerException("No Main Pane was provided");

        modeSettingsDialog = new Stage();
        modeSettingsDialog.setTitle("Mode Settings");
        modeSettingsDialog.initStyle(StageStyle.UTILITY);
        modeSettingsDialog.initModality(Modality.APPLICATION_MODAL);
        modeSettingsDialog.initOwner(primaryStage);

        MigPane pane = createLayout();
        pane.setId("MainModeSettingsPane");
        Scene modeSettingsDialogScene = new Scene(pane, DIALOG_WIDTH, DIALOG_HEIGHT);
        modeSettingsDialog.setScene(modeSettingsDialogScene);
    }

    /**
     * Displays an informational screen for the modes available
     *
     */
    public void show(){
        modeSettingsDialog.setX(primaryStage.getX());
        modeSettingsDialog.setY(primaryStage.getY());
        modeSettingsDialog.show();
    }

    private MigPane createLayout(){
        MigPane mainPane = new MigPane();
        mainPane.setId("MainModeSettingsPaneBuilder");
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        //String within should be obtained from the information for each Mode,
        //i.e.:     basicCalculator.getInfo();
        BasicCalculator mode1 = new BasicCalculator();
        basicCalculator = new Label(mode1.getInfo());
        basicCalculator.setId("BasicCalculatorLabel");

        MigPane basicCalculatorPane = new MigPane();
        basicCalculatorPane.setId("BasicCalculatorPaneBuilder");
        basicCalculatorPane.add(basicCalculator, "wrap, align left");
        basicCalculatorPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane calcPane = new TitledPane("Basic Calculator", basicCalculatorPane);
        calcPane.setId("BasicCalculatorPane");

        BoldFormat mode2 = new BoldFormat();
        boldFormat = new Label(mode2.getInfo());
        boldFormat.setId("BoldFormatLabel");

        MigPane boldFormatPane = new MigPane();
        boldFormatPane.setId("BoldFormatPaneBuilder");
        boldFormatPane.add(boldFormat, "wrap, align left");
        boldFormatPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane boldPane = new TitledPane("Bold", boldFormatPane);
        boldPane.setId("BoldFormatPane");

        ItalicFormat mode3 =  new ItalicFormat();
        italicFormat = new Label(mode3.getInfo());
        italicFormat.setId("ItalicFormatLabel");

        MigPane italicFormatPane = new MigPane();
        italicFormatPane.setId("ItalicFormatPaneBuilder");
        italicFormatPane.add(italicFormat, "wrap, align left");
        italicFormatPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane italicPane = new TitledPane("Italic", italicFormatPane);
        italicPane.setId("ItalicFormatPane");

        closeButton = new JFXButton("Close");
        closeButton.setId("CloseButton");
        closeButton.setButtonType(JFXButton.ButtonType.FLAT);
        closeButton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));

        MigPane buttonPane = new MigPane();
        buttonPane.setId("ButtonFontPane");
        buttonPane.add(closeButton);

        setActionListeners();

        mainPane.add(calcPane, "wrap");
        mainPane.add(boldPane,"wrap");
        mainPane.add(italicPane,"wrap");
        mainPane.add(buttonPane, "align right");


        mainPane.setStyle("-fx-background-color: rgba(255, 255, 100, 0.5);");
        return mainPane;
    }

    private void setActionListeners(){

        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){

                modeSettingsDialog.close();
            }
        });
    }
}

