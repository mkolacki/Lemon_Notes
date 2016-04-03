import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created by Mike on 4/3/2016.
 */
public class PreviewBox {
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 380;

    private final Stage previewBox;
    private final Stage primaryStage;
    private final Pane mainPane;

    private JFXButton closeButton;

    public PreviewBox(Stage primaryStage, Pane mainPane) {
        if(primaryStage != null)
            this.primaryStage = primaryStage;
        else
            throw new NullPointerException("No Primary Stage was provided");
        if(mainPane != null)
            this.mainPane = mainPane;
        else
            throw new NullPointerException("No Main Pane was provided");

        previewBox = new Stage();
        previewBox.setTitle("Preview");
        previewBox.initOwner(primaryStage);

        MigPane pane  = createLayout();
        pane.setId("MainPreviewPane");
        Scene previewBoxScene = new Scene(pane, DIALOG_WIDTH, DIALOG_HEIGHT);
        previewBox.setScene(previewBoxScene);

    }

    public void show() {
        previewBox.setX(primaryStage.getX());
        previewBox.setY(primaryStage.getY());
        previewBox.show();
    }

    private MigPane createLayout() {
        MigPane mainPane = new MigPane();
        mainPane.setId("PreviewPaneBuilder");
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        MigPane previewPane = new MigPane();
        previewPane.setId("PreviewPane");

        closeButton= new JFXButton("Close");
        closeButton.setId("CloseButton");
        closeButton.setButtonType(JFXButton.ButtonType.FLAT);
        closeButton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));

        MigPane buttonPane = new MigPane();
        buttonPane.setId("ButtonPreviewPane");
        buttonPane.add(closeButton);

        setActionListeners();

        mainPane.add(previewPane, "align left");
        mainPane.add(buttonPane, "align right");

        return mainPane;
    }

    private void setActionListeners(){

        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){

                previewBox.close();
            }
        });
    }
}
