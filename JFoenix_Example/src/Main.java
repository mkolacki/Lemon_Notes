import com.jfoenix.controls.*;
import com.jfoenix.responsive.JFXResponsiveHandler;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
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
        JFXCheckBox check = new JFXCheckBox("Tickbox");
        JFXBadge badge = new JFXBadge();
        JFXComboBox<String> combo = new JFXComboBox<String>();
        combo.setTooltip(new Tooltip("Hey you! Click me to pick a listed item."));
        combo.setPromptText("Look at a project.");
        combo.getItems().add("Project 1");
        combo.getItems().add("Project 2");
        combo.getItems().add("Project 3");
        combo.getItems().add("Test");

        GridPane pane = new GridPane();
        pane.add(button, 0, 0);
        pane.add(check, 0, 1);
        pane.add(combo, 0, 2);


        button.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                primaryStage.setTitle("Hey, you clicked a button!");
                //System.out.println("* Clicking on this button...it fills you with determination.");
            }
        });

        combo.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle(combo.getSelectionModel().getSelectedItem().toString());
                combo.setTooltip(new Tooltip("Click to pick another project."));
            }
        });

        final Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
