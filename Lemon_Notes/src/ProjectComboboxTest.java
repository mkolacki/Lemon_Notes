import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Mike on 3/9/2016.
 */
public final class ProjectComboboxTest {


    @Rule
    public ExpectedException exc = ExpectedException.none();


    //@Mock private Stage stgTest = new Stage();
    //@Mock private GridPane gTest;
    JFXComboBox<String> comboTest = new JFXComboBox<String>();

    @Mock private ProjectCombobox mockProject;

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Before
    public void setup()
    {
        //This is required
        MockitoAnnotations.initMocks(this);
    }

    /**
     * {@link ProjectComboboxTest}
     * Attempt to build a null ProjectComboBox
     */
    @Test
    public void nullPCB() {
        exc.expect(NullPointerException.class);
        new ProjectCombobox(null);
    }

    /**
     * {@link ProjectComboboxTest#testAddAProject()}
     * The "right" way
     */
    @Test
    public void testAddAProject() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Name");
        assertEquals("Name",pcbTest.projects.get(0).name);
        assertEquals("Name",pcbTest.current_project.name);
        assertEquals(1,pcbTest.projects.size());
    }

    /**
     * {@link ProjectComboboxTest#testAddAProject()}
     * Attempting to add the same thing twice
     */
    @Test
    public void testAddAProjectDupe() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Name");
        pcbTest.addAProject("Name");
        assertEquals("Name",pcbTest.projects.get(0).name);
        assertEquals("Name",pcbTest.current_project.name);
        assertEquals(1,pcbTest.projects.size());
    }

    /**
     * {@link ProjectComboboxTest#testAddAProject()}
     * No given name
     */
    @Test
    public void testAddAProjectNull1() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        exc.expect(NullPointerException.class);
        pcbTest.addAProject(null);
    }

    /**
     * {@link ProjectComboboxTest#testSelectProject()}
     * The "right" way
     */
    @Test
    public void testSelectProject() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Name1");
        pcbTest.addAProject("Name2");
        pcbTest.selectProject("Name1");
        assertEquals("Name1",pcbTest.current_project.name);
        assertEquals(2,pcbTest.projects.size());
    }
    /**
     * {@link ProjectComboboxTest#testSelectProject()}
     * Pass in null for a project selection.
     */
    @Test
    public void testSelectProject2() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Name1");
        pcbTest.addAProject("Name2");
        exc.expect(NullPointerException.class);
        pcbTest.selectProject(null);
    }
}