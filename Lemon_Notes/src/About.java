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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.MigPane;

import javax.swing.*;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class About extends Coggie {
    /**
     * Displays the authors of the program, and a short blurb about the program itself.
     */
    private static final int DIALOG_HEIGHT = 200;
    private static final int DIALOG_WIDTH = 330;

    private JFXButton okButton;
    private final Stage aboutDialog;
    private final Stage primaryStage;
    private final Pane primaryPane;
    private MigPane aboutPane;
    private Label aboutText;

    public About(Stage primaryStage, Pane mainPane) {
        if(primaryStage != null)
            this.primaryStage = primaryStage;
        else
            throw new NullPointerException("Primary Stage is null");
        if(mainPane != null)
            this.primaryPane = mainPane;
        else
            throw new NullPointerException("Main Pane is null");
        aboutDialog = new Stage();
        aboutDialog.setTitle("About");
        aboutDialog.initStyle(StageStyle.UTILITY);
        aboutDialog.initModality(Modality.APPLICATION_MODAL);
        aboutDialog.initOwner(primaryStage);

        aboutPane = createLayout();
        aboutPane.setId("aboutPane");
        Scene aboutScene = new Scene(aboutPane,DIALOG_WIDTH,DIALOG_HEIGHT);
        aboutDialog.setScene(aboutScene);
    }

    public void show(){

        aboutDialog.setX(primaryStage.getX());
        aboutDialog.setY(primaryStage.getY());
        aboutDialog.show();
    }

    private MigPane createLayout() {
        MigPane mainPane = new MigPane();
        mainPane.setId("MainAboutPaneBuilder");
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        aboutText = new Label("Lemon Notes v1.0\n\nCreated by\nMichael Kolacki, Alex Kouthoofd, and Michael Cavataio\n\nProduced with JFoenix");
        aboutText.setPrefWidth(DIALOG_WIDTH);//?
        aboutText.setWrapText(true);//?
        aboutText.setTextAlignment(TextAlignment.CENTER);
        aboutText.setId("AboutLabel");

        MigPane aboutTextPane = new MigPane();
        aboutTextPane.setId("TextPane");
        aboutTextPane.add(aboutText,"wrap, align center");
        aboutTextPane.setPrefSize(mainPane.getPrefWidth(), 300);
        //TitledPane aboutTPane = new TitledPane("Lemon Notes", aboutTextPane);
       // aboutTPane.setId("TitledAboutPane");
        mainPane.add(aboutTextPane,"span, wrap");

        okButton = new JFXButton("OK");
        okButton.setId("OKButton");
        okButton.setButtonType(JFXButton.ButtonType.FLAT);
        okButton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));

        MigPane button = new MigPane();
        button.setId("BPane");
        button.add(okButton);

        setActionListeners();

        mainPane.add(button, "align center");
        //mainPane.setStyle("-fx-background-color: rgba(255, 255, 100, 0.5);");
        return mainPane;
    }


    private void setActionListeners(){

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                aboutDialog.close();
            }
        });
    }
}
