import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created by Michael K. on 3/8/2016.
 */
public class FontSettings extends Coggie
{
    private static final int DIALOG_WIDTH = 300;
    private static final int DIALOG_HEIGHT = 380;

    private final Stage fontDialog;
    private final Stage primaryStage;
    private final Pane mainPane;

    private Label subjLabel;
    private Label textLabel;
    private JFXComboBox<Integer> subjFontSizeBox;
    private JFXComboBox<Integer> textFontSizeBox;
    private JFXComboBox<String> subjFontStyleBox;
    private JFXComboBox<String> textFontStyleBox;
    private JFXButton okButton;
    private JFXButton cancelButton;

    /**
     *  Constructor.
     *
     * @param primaryStage The owner of the new stage
     */
    public FontSettings(Stage primaryStage, Pane mainPane)
    {
        this.mainPane = mainPane;
        this.primaryStage = primaryStage;
        fontDialog = new Stage();
        fontDialog.setTitle("Font Settings");
        fontDialog.initStyle(StageStyle.UTILITY);
        fontDialog.initModality(Modality.APPLICATION_MODAL);
        fontDialog.initOwner(primaryStage);

        MigPane pane = createLayout();
        Scene fontDialogScene = new Scene(pane, DIALOG_WIDTH, DIALOG_HEIGHT);
        fontDialog.setScene(fontDialogScene);
    }

    /**
     * Displays an option screen for font sizes and styles
     *
     */
    public void show()
    {
        fontDialog.setX(primaryStage.getX());
        fontDialog.setY(primaryStage.getY());
        fontDialog.show();
    }

    private MigPane createLayout()
    {
        MigPane mainPane = new MigPane();
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        subjLabel = new Label("This is the font selected.");
        textLabel = new Label("This is the font selected.");

        subjFontSizeBox = new JFXComboBox<Integer>();
        subjFontSizeBox.setPromptText("Select a size.");
        subjFontSizeBox.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50));
        subjFontSizeBox.setMaxSize(150, 50);

        textFontSizeBox = new JFXComboBox<Integer>();
        textFontSizeBox.setPromptText("Select a size.");
        textFontSizeBox.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50));
        textFontSizeBox.setMaxSize(150, 50);

        subjFontStyleBox = new JFXComboBox<String>();
        subjFontStyleBox.setPromptText("Select a font style.");
        subjFontStyleBox.setItems(FXCollections.observableArrayList(Font.getFontNames()));
        subjFontStyleBox.setMaxSize(300, 50);

        textFontStyleBox = new JFXComboBox<String>();
        textFontStyleBox.setPromptText("Select a font style.");
        textFontStyleBox.setItems(FXCollections.observableArrayList(Font.getFontNames()));
        textFontStyleBox.setMaxSize(300, 50);

        MigPane subjectPane = new MigPane();
        subjectPane.add(subjFontSizeBox, "wrap, align center");
        subjectPane.add(subjFontStyleBox, "wrap, align center");
        subjectPane.add(subjLabel, "span, align center, gapy 15px");
        subjectPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane subjPane = new TitledPane("Subject Fonts", subjectPane);

        MigPane textAreaPane = new MigPane();
        textAreaPane.add(textFontSizeBox, "wrap, align center");
        textAreaPane.add(textFontStyleBox, "wrap, align center");
        textAreaPane.add(textLabel, "span, align center, gapy 15px");
        textAreaPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight()/2);
        TitledPane textPane = new TitledPane("Text Area Fonts", textAreaPane);

        okButton = new JFXButton("Ok");
        okButton.setButtonType(JFXButton.ButtonType.FLAT);
        okButton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));
        cancelButton = new JFXButton("Cancel");
        cancelButton.setButtonType(JFXButton.ButtonType.FLAT);
        cancelButton.setBackground(new Background(new BackgroundFill(Color.PINK, new CornerRadii(0), new Insets(0))));

        MigPane buttonPane = new MigPane();
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        setActionListeners();

        mainPane.add(subjPane, "wrap");
        mainPane.add(textPane, "wrap");
        mainPane.add(buttonPane, "align right");


        mainPane.setStyle("-fx-background-color: rgba(255, 255, 100, 0.5);");
        return mainPane;
    }

    private void setActionListeners()
    {
        subjFontSizeBox.setOnAction(new EventHandler<ActionEvent>()
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
        });
    }
}
