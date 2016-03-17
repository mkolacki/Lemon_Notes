import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by alex on 3/17/2016.
 */
public class CogWheelTest
{
    /**
     * Tool for testing JFX
     */
    @Rule
    public JavaFXThreadingRule jfxTester = new JavaFXThreadingRule();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test method for {@link CogWheel#CogWheel(Stage, GridPane, MenuBar, Menu)}
     * Testing the constructor when passed null parameters
     */
    @Test
    public void testCogWheelNull1()
    {
        exception.expect(NullPointerException.class);
        new CogWheel(null, new GridPane(), new MenuBar(), new Menu());
    }

    /**
     * Test method for {@link CogWheel#CogWheel(Stage, GridPane, MenuBar, Menu)}
     * Testing the constructor when passed null parameters
     */
    @Test
    public void testCogWheelNull2()
    {
        exception.expect(NullPointerException.class);
        new CogWheel(new Stage(), null, new MenuBar(), new Menu());
    }

    /**
     * Test method for {@link CogWheel#CogWheel(Stage, GridPane, MenuBar, Menu)}
     * Testing the constructor when passed null parameters
     */
    @Test
    public void testCogWheelNull3()
    {
        exception.expect(NullPointerException.class);
        new CogWheel(new Stage(), new GridPane(), null, new Menu());
    }

    /**
     * Test method for {@link CogWheel#CogWheel(Stage, GridPane, MenuBar, Menu)}
     * Testing the constructor when passed null parameters
     */
    @Test
    public void testCogWheelNull4()
    {
        exception.expect(NullPointerException.class);
        new CogWheel(new Stage(), new GridPane(), new MenuBar(), null);
    }

    /**
     * Test method for {@link CogWheel#CogWheel(Stage, GridPane, MenuBar, Menu)}
     * Ensuring constructor works as expected with correct parameters.
     */
    @Test
    public void testCogWheel()
    {
        CogWheel wheel = new CogWheel(new Stage(), new GridPane(), new MenuBar(), new Menu("Test"));
        assertEquals("Test", wheel.menu.getText());
    }

}