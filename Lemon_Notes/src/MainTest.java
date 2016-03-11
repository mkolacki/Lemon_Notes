import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.stage.Stage;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;

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
                    public void run() {
                        Main main = new Main();
                        main.start(new Stage());
                        Field[] fields = main.getClass().getDeclaredFields();
                        for(Field field: fields)
                        {
                            if(field.getName().equals("button"))
                            {
                                field.setAccessible(true);
                                try
                                {
                                    EventHandler<MouseEvent> event = (EventHandler<MouseEvent>) ((JFXButton)field.get(main)).getOnMouseClicked();
                                    event.handle(null);
                                } catch (IllegalAccessException e)
                                {
                                    e.printStackTrace();
                                }
                                field.setAccessible(false);
                            }
                            else if(field.getName().equals("combo"))
                            {
                                field.setAccessible(true);
                                try
                                {
                                    EventHandler<ActionEvent> event = ((JFXComboBox<String>)field.get(main)).getOnAction();
                                } catch (IllegalAccessException e)
                                {
                                    e.printStackTrace();
                                }
                                field.setAccessible(false);
                            }
                        }
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(2000);
    }
}