import com.jfoenix.controls.*;
import com.jfoenix.responsive.JFXResponsiveHandler;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.css.PseudoClass;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.*;

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
        button.setTooltip(new Tooltip("It's a button... you know like something you press."));
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), new Insets(0,0,0,0))));
        button.setButtonType(JFXButton.ButtonType.RAISED);
        JFXCheckBox check = new JFXCheckBox("Optional");
        JFXBadge badge = new JFXBadge();
        badge.setText("This is the badge");
        JFXComboBox<String> combo = new JFXComboBox<String>();
        check.setCheckedColor(Color.GOLD);
        check.setUnCheckedColor(Color.GREEN);

        combo.getItems().add("1st");
        combo.setPromptText("Pick Something");
        combo.getItems().add("2nd");
        combo.getItems().add("3rd");
        combo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle(combo.getSelectionModel().getSelectedItem().toString());
            }
        });


        GridPane pane = new GridPane();
        pane.add(button, 0, 0);
        pane.add(check, 1, 1);
        pane.add(combo, 2, 2);
        pane.add(badge, 3, 3);



        final Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
