import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link FontSettings}
 * Created by alex on 3/29/2016.
 */
public final class FontSettingsTest
{
    /**
     * Rule that moves test to a separate thread allowing proper testing
     */
    @Rule
    public JavaFXThreadingRule threadingRule = new JavaFXThreadingRule();

    /**
     * For Expecting Exceptions
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test method for {@link FontSettings#FontSettings(Stage, Pane)}
     * Testing constructor when past null parameters
     */
    @Test
    public void testFontSettingsNull1()
    {
        exception.expect(NullPointerException.class);
        new FontSettings(null, new GridPane());
    }

    /**
     * Test method for {@link FontSettings#FontSettings(Stage, Pane)}
     * Testing constructor when past null parameters
     */
    @Test
    public void testFontSettingsNull2()
    {
        exception.expect(NullPointerException.class);
        new FontSettings(new Stage(), null);
    }

    /**
     *  Test method for {@link FontSettings#show()}
     *  Ensuring method displays the proper dialog screen.
     */
    @Test
    public void testShow() throws IllegalAccessException, NoSuchFieldException
    {
        GridPane pane = new GridPane();
        JFXTextArea bigBox = new JFXTextArea();
        bigBox.setId("Big Box Area");

        TextField subjBox = new TextField(); //needs to be restricted to alphanumeric characters
        subjBox.setId("Subject Box");
        subjBox.setPromptText("Subject");

        pane.add(bigBox, 0, 0);
        pane.add(subjBox, 0, 1);

        FontSettings fonts = new FontSettings(new Stage(), pane);
        fonts.show();
        Field[] fields = fonts.getClass().getDeclaredFields();
        int count = 14;
        Field subject = fonts.getClass().getDeclaredField("subjLabel");
        Field text = fonts.getClass().getDeclaredField("textLabel");
        subject.setAccessible(true);
        text.setAccessible(true);
        Label subjectLabel = (Label)subject.get(fonts);
        Label textLabel = (Label)text.get(fonts);

        for(Field field: fields)
        {
            System.out.println(field.getName());
            field.setAccessible(true);
            if(field.getName() != null && field.getName().contains("SizeBox"))
            {
                JFXComboBox<Integer> combo = (JFXComboBox<Integer>)field.get(fonts);
                combo.getSelectionModel().select(count++);
                if(field.getName().contains("subj"))
                    assertEquals(20, (int)subjectLabel.getFont().getSize());
                else
                    assertEquals(22, (int)textLabel.getFont().getSize());
            }
            else if(field.getName() != null && field.getName().contains("StyleBox"))
            {
                JFXComboBox<String> combo = (JFXComboBox<String>)field.get(fonts);
                combo.getSelectionModel().select(count++);
                if(field.getName().contains("subj"))
                    assertEquals("Arial Bold Italic", subjectLabel.getFont().getName());
                else
                    assertEquals("Arial Italic", textLabel.getFont().getName());
            }
            else if(field.getName() != null && field.getName().contains("Button"))
            {
                JFXButton button = (JFXButton)field.get(fonts);
                button.getOnMouseClicked().handle(null);
            }
            field.setAccessible(false);
        }

        subject.setAccessible(false);
        text.setAccessible(false);
    }
}