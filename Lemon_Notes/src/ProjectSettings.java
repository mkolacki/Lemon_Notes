import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;

/**
 * ProjectSettings allows user project management
 */
public class ProjectSettings extends Coggie
{
    private static final int DIALOG_WIDTH = 220;
    private static final int DIALOG_HEIGHT = 390;

    private final ProjectCombobox projectComboBox;
    private final Stage projectsDialog;
    private final Stage primaryStage;

    private JFXListView<String> listView;
    private JFXButton okButton;
    private JFXButton cancelButton;
    private JFXButton deleteButton;

    private ArrayList<String> projectsToRemove = new ArrayList<String>();
    private ArrayList<String> changed = new ArrayList<String>();

    /**
     * Constructor.
     *
     * @param primaryStage    The owner of the new stage
     * @param projectComboBox The class containing all current projects
     */
    public ProjectSettings(Stage primaryStage, ProjectCombobox projectComboBox)
    {
        if (primaryStage != null)
            this.primaryStage = primaryStage;
        else
            throw new NullPointerException("Primary Stage is null");
        if (projectComboBox != null)
            this.projectComboBox = projectComboBox;
        else
            throw new NullPointerException("Project ComboBox is null");

        projectsDialog = new Stage();
        projectsDialog.setTitle("Project Settings");
        projectsDialog.initStyle(StageStyle.UTILITY);
        projectsDialog.initModality(Modality.APPLICATION_MODAL);
        projectsDialog.initOwner(primaryStage);

        MigPane pane = createLayout();
        pane.setId("MainFontPane");
        Scene projectsDialogScene = new Scene(pane, DIALOG_WIDTH, DIALOG_HEIGHT);
        projectsDialog.setScene(projectsDialogScene);
    }

    /**
     * Displays an option screen for font sizes and styles
     */
    public void show()
    {
        for (String text : projectComboBox.comboBox.getItems())
        {
            if (!listView.getItems().contains(text))
                listView.getItems().add(text);
            changed.add(text);
        }
        listView.autosize();

        projectsDialog.setX(primaryStage.getX());
        projectsDialog.setY(primaryStage.getY());
        projectsDialog.show();
    }

    private MigPane createLayout()
    {
        MigPane mainPane = new MigPane();
        mainPane.setId("MainFontPaneBuilder");
        mainPane.setPrefSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        listView = new JFXListView<String>();
        for (String text : projectComboBox.comboBox.getItems())
            listView.getItems().add(text);
        listView.autosize();
        listView.setPrefSize(DIALOG_WIDTH, 350);
        listView.setEditable(true);
        listView.setCellFactory(TextFieldListCell.forListView());

        deleteButton = new JFXButton();
        Image delete_graphic = new Image(getClass().getResourceAsStream("trashicon.png"));
        deleteButton.setGraphic(new ImageView(delete_graphic));
        deleteButton.setTooltip(new Tooltip("Deletes the currently selected project."));

        okButton = new JFXButton("Ok");
        okButton.setId("OKButton");
        okButton.setButtonType(JFXButton.ButtonType.FLAT);
        okButton.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));
        cancelButton = new JFXButton("Cancel");
        cancelButton.setId("CancelButton");
        cancelButton.setButtonType(JFXButton.ButtonType.FLAT);
        cancelButton.setBackground(new Background(new BackgroundFill(Color.PINK, new CornerRadii(0), new Insets(0))));

        MigPane buttonPane = new MigPane();
        buttonPane.setId("ButtonFontPane");
        buttonPane.add(deleteButton, "align left");
        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        setActionListeners();
        mainPane.add(listView, "span, wrap");
        mainPane.add(buttonPane, "align right");


        mainPane.setStyle("-fx-background-color: rgba(255, 255, 100, 0.5);");
        return mainPane;
    }

    private void setActionListeners()
    {
        listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>()
        {
            @Override
            public void handle(ListView.EditEvent<String> event)
            {
                listView.getItems().set(event.getIndex(), event.getNewValue());
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (listView.getSelectionModel().getSelectedIndex() != -1)
                {
                    projectsToRemove.add(listView.getSelectionModel().getSelectedItem());
                    listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
                }
            }
        });

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                for (String remove : projectsToRemove)
                    projectComboBox.removeProject(remove);
                for (int i = 0; i < changed.size(); i++)
                {
                    if (!changed.get(i).equals(listView.getItems().get(i)))
                        projectComboBox.changeProjectName(changed.get(i), listView.getItems().get(i));
                }
                projectsToRemove.clear();
                changed.clear();
                //projectComboBox.removeProject("Eggplants");
                projectsDialog.close();
            }
        });

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                listView.getItems().addAll(projectsToRemove.toArray(new String[]{}));
                listView.getItems().setAll(changed);
                changed.clear();
                projectsDialog.close();
            }
        });
    }
}
