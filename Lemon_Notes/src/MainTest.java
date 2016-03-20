import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.lang.reflect.Field;

/**
 * Created by alex on 3/6/2016.
 */
public class MainTest
{
    @org.junit.Test
    public void testMain() throws Exception
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try
                        {
                            Main.main(new String[] {});
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(2000);
    }

    @org.junit.Test
    public void testStart() throws Exception
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run()
                    {
                        Main main = new Main();
                        Stage stage = new Stage();
                        main.start(stage);
                        try
                        {
                            Field button = main.getClass().getDeclaredField("button");
                            button.setAccessible(true);
                            EventHandler<MouseEvent> event = (EventHandler<MouseEvent>) ((JFXButton) button.get(main)).getOnMouseClicked();
                            event.handle(null);
                            button.setAccessible(false);
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e)
                        {
                            e.printStackTrace();
                        }

                        try
                        {
                            Field combo = main.getClass().getDeclaredField("combo");
                            combo.setAccessible(true);
                            EventHandler<ActionEvent> event = ((JFXComboBox<String>) combo.get(main)).getOnAction();
                            event.handle(new ActionEvent());
                            combo.setAccessible(false);
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e)
                        {
                            e.printStackTrace();
                        }

                        try
                        {
                            Field submit = main.getClass().getDeclaredField("submit");
                            submit.setAccessible(true);
                            EventHandler<ActionEvent> event = ((JFXButton) submit.get(main)).getOnAction();
                            event.handle(new ActionEvent());
                            Field subjBox = main.getClass().getDeclaredField("subjBox");
                            subjBox.setAccessible(true);
                            event.handle(new ActionEvent());
                            ((TextField) subjBox.get(main)).setText("Test");
                            subjBox.setAccessible(false);
                            Field bigBox = main.getClass().getDeclaredField("bigBox");
                            bigBox.setAccessible(true);
                            event.handle(new ActionEvent());
                            ((JFXTextArea) bigBox.get(main)).setText("Test");
                            bigBox.setAccessible(false);
                            event.handle(new ActionEvent());
                            event.handle(new ActionEvent());
                            subjBox.setAccessible(true);
                            ((TextField) subjBox.get(main)).setText("");
                            subjBox.setAccessible(false);
                            event.handle(new ActionEvent());
                            submit.setAccessible(false);
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e)
                        {
                            e.printStackTrace();
                        }

                        try
                        {
                            EventHandler<WindowEvent> event = stage.getOnCloseRequest();
                            Field bigBox = main.getClass().getDeclaredField("bigBox");
                            bigBox.setAccessible(true);
                            ((JFXTextArea) bigBox.get(main)).setText("Test");
                            bigBox.setAccessible(false);
                            event.handle(new WindowEvent(stage.getOwner(), EventType.ROOT));
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(10000);
    }
}