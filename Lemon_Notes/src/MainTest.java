import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.Rule;

import java.lang.reflect.Field;

/**
 * Created by alex on 3/6/2016.
 */
public class MainTest
{

    /**
     * Tool for testing JFX
     */
    @Rule
    public JavaFXThreadingRule jfxTester = new JavaFXThreadingRule();

//    @org.junit.Test
//    public void testMain() throws Exception
//    {
//        String[] string = {"test"};
//        Main.main(string);
//        assertNotNull(string);
//    }

    @org.junit.Test
    public void testStart() throws Exception
    {
        Main main = new Main();
        Stage stage = new Stage();
        main.start(stage);
        try
        {
            Field button = main.getClass().getDeclaredField("npbutton");
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

        for(Field field: main.getClass().getDeclaredFields())
        {
            int count = 2;
            System.out.println(field.getName());
            field.setAccessible(true);
            if(field.getName() != null && field.getName().contains("note_combo_box"))
            {
                NoteComboBox comboClass = (NoteComboBox)field.get(main);
                JFXComboBox<String> combo = comboClass.comboBox;
                combo.getSelectionModel().select(count++);
//                if(field.getName().contains("subj"))
//                    assertEquals(20, (int)subjectLabel.getFont().getSize());
//                else
//                    assertEquals(22, (int)textLabel.getFont().getSize());
            }
//            else if(field.getName() != null && field.getName().contains("StyleBox"))
//            {
//                JFXComboBox<String> combo = (JFXComboBox<String>)field.get(main);
//                combo.getSelectionModel().select(count++);
////                if(field.getName().contains("subj"))
////                    assertEquals(subjectLabel.getFont().getName(), subjectLabel.getFont().getName());
////                else
////                    assertEquals(textLabel.getFont().getName(), textLabel.getFont().getName());
//            }
//            else if(field.getName() != null && field.getName().contains("Button"))
//            {
//                JFXButton button = (JFXButton)field.get(main);
//                button.getOnMouseClicked().handle(null);
//            }
            field.setAccessible(false);
        }
    }
}