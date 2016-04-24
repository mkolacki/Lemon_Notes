import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.MigPane;


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

    private MigPane modePane;
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

        modePane = createLayout();
        modePane.setId("MainModeSettingsPane");
        Scene modeSettingsDialogScene = new Scene(modePane, DIALOG_WIDTH, DIALOG_HEIGHT);
        modeSettingsDialog.setScene(modeSettingsDialogScene);
    }

    /**
     * Displays an informational screen for the modes available
     *
     */
    public void show(){

        modePane = createLayout();

        modeSettingsDialog.setX(primaryStage.getX());
        modeSettingsDialog.setY(primaryStage.getY());
        modeSettingsDialog.show();
    }

    private MigPane createLayout(){
        MigPane mainPane = new MigPane();
        mainPane.setId("MainModeSettingsPaneBuilder");
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);


        for (Mode m : Mode.allModes)
        {
            //String within should be obtained from the information for each Mode,
            //i.e.:     basicCalculator.getInfo();
            basicCalculator = new Label(m.getInfo());
            basicCalculator.setPrefWidth(DIALOG_WIDTH);//?
            basicCalculator.setWrapText(true);//?
            basicCalculator.setId(m.mode + "Label");

            MigPane basicCalculatorPane = new MigPane();
            basicCalculatorPane.setId(m.mode + "BuilderPane");
            basicCalculatorPane.add(basicCalculator, "wrap, align left");
            basicCalculatorPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight() / 2);
            TitledPane calcPane = new TitledPane(m.mode, basicCalculatorPane);
            calcPane.setId(m.mode + "Pane");
            mainPane.add(calcPane, "wrap");
        }
        closeButton = new JFXButton("Close");
        closeButton.setId("CloseButton");
        closeButton.setButtonType(JFXButton.ButtonType.FLAT);
        closeButton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));

        MigPane buttonPane = new MigPane();
        buttonPane.setId("ButtonFontPane");
        buttonPane.add(closeButton);

        setActionListeners();

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

