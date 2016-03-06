import com.jfoenix.controls.*;
import com.jfoenix.responsive.JFXResponsiveHandler;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.embed.swing.JFXPanel;
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
        JFXButton button = new JFXButton("Btn");
        button.setTooltip(new Tooltip("It's a button... you know like something you press."));
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), new Insets(0,0,0,0))));
        JFXCheckBox check = new JFXCheckBox("Checkly");
        JFXBadge badge = new JFXBadge();
        JFXComboBox<String> combo = new JFXComboBox<String>();


        AnchorPane container = new AnchorPane();
        container.getChildren().add(button);
        container.getChildren().add(check);
        container.getChildren().add(badge);
        container.getChildren().add(combo);
        AnchorPane.setLeftAnchor(button, 20.0);
        AnchorPane.setLeftAnchor(check, 80.0);
        AnchorPane.setLeftAnchor(badge, 150.0);
        AnchorPane.setLeftAnchor(combo, 230.0);
        AnchorPane.setTopAnchor(button, 100.0);
        AnchorPane.setTopAnchor(check, 100.0);
        AnchorPane.setTopAnchor(badge, 200.0);
        AnchorPane.setTopAnchor(combo, 200.0);

        final Scene scene = new Scene(container, 300, 300);
        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
