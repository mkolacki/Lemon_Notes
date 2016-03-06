import com.jfoenix.controls.*;
import com.jfoenix.responsive.JFXResponsiveHandler;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;

/**
 * Created by alex on 3/5/2016.
 * Main class to start the program
 */
public class Main extends Application
{
    public static void main(String[] args) throws Exception
    {
        //Manager.run();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        JFXButton button = new JFXButton("Button");
        button.setTooltip(new Tooltip("It's a button!"));
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), new Insets(0,0,0,0))));

        button.setButtonType(JFXButton.ButtonType.RAISED);
        //JFXCheckBox check = new JFXCheckBox("Tickbox");
        JFXBadge badge = new JFXBadge();
        JFXComboBox<String> combo = new JFXComboBox<String>();
        combo.setTooltip(new Tooltip("Hey you! Click me to pick a listed item."));
        combo.setPromptText("Look at a project.");
        combo.getItems().add("Project 1");
        combo.getItems().add("Project 2");
        combo.getItems().add("Project 3");
        combo.getItems().add("Test");

        JFXButton newnote = new JFXButton("New");
        JFXButton preview = new JFXButton("Preview");
        JFXButton submit = new JFXButton("Submit");
        JFXButton delete = new JFXButton("Delete");


        GridPane pane = new GridPane();
        pane.add(button, 0, 0);
        //pane.add(check, 1, 0);
        pane.add(combo, 1, 0);
        pane.add(newnote, 3, 1);
        pane.add(preview, 3, 2);
        pane.add(submit, 3, 3);
        pane.add(delete, 3, 4);
        pane.setHgap(10);
        pane.setVgap(10);

        ColumnConstraints cons1 = new ColumnConstraints();
        pane.getColumnConstraints().add(cons1);
        JFXTextArea bigbox = new JFXTextArea();
        pane.add(bigbox, 0, 1, 2, 5);

        //Testing how JFoenix events work. --Mike C.
        button.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                primaryStage.setTitle("Hey, you clicked a button!");
            }
        });

        combo.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle(combo.getSelectionModel().getSelectedItem().toString());
                combo.setTooltip(new Tooltip("Click to pick another project."));
            }
        });

        /*
        JFXButton btn1 = new JFXButton("Button1");
        JFXButton btn2 = new JFXButton("Button2");
        JFXButton btn3 = new JFXButton("Button3");
        JFXButton btn4 = new JFXButton("Button4");
        */
        //big menus >> submenus
        Menu mn1 = new Menu("Menu 1");
        MenuItem item1 = new MenuItem("Item 1");
        MenuItem item2 = new MenuItem("Item 2");
        mn1.getItems().addAll(item1,item2);
        Menu mn2 = new Menu("Menu 2");
        MenuItem item3 = new MenuItem("Item 3");
        MenuItem item4 = new MenuItem("Item 4");
        mn2.getItems().addAll(item3,item4);


        //JFXToolbar tools = new JFXToolbar();
        MenuBar tools = new MenuBar();
        //tools.getLeftItems().addAll(btn1, btn2, btn3, btn4);
        tools.getMenus().addAll(mn1,mn2);
        BorderPane toolpane = new BorderPane();
        toolpane.setTop(tools);

        GridPane biggrid = new GridPane();
        biggrid.add(toolpane, 0, 0);
        biggrid.add(pane,0,1);

        final Scene scene = new Scene(biggrid, 300, 300);
        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
