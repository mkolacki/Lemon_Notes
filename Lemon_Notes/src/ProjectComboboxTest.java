import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Mike on 3/9/2016.
 */
public final class ProjectComboboxTest {


    @Rule
    public ExpectedException exc = ExpectedException.none();

    JFXComboBox<String> comboTest = new JFXComboBox<String>();

    @Mock private ProjectCombobox mockProject;

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @AfterClass
    public static void tearDown() { //remove created files after testing
        File delete_me = new File("Projects/Test01");
        File delete_me_notes = new File("Projects/Test01/saved_notes");
        File delete_me_too = new File("Projects/Test02");
        File delete_me_too_notes = new File("Projects/Test02/saved_notes");
        if(delete_me.exists()){
            delete_me_notes.delete();
            delete_me.delete();
        }
        if(delete_me_too.exists()){
            delete_me_too_notes.delete();
            delete_me_too.delete();
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
     * {@link ProjectCombobox}
     * Attempt to build a null ProjectComboBox
     */
    @Test
    public void nullPCB() {
        exc.expect(NullPointerException.class);
        new ProjectCombobox(null);
    }

    /**
     * {@link ProjectCombobox#addAProject(String)}
     * The "right" way
     */
    @Test
    public void testAddAProject() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Test01");
        assertEquals("Test01",pcbTest.projects.get(0).name);
        assertEquals("Test01",pcbTest.current_project.name);
        assertEquals(1,pcbTest.projects.size());
    }

    /**
     * {@link ProjectCombobox#addAProject(String)}
     * Attempting to add the same thing twice
     */
    @Test
    public void testAddAProjectDupe() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Test01");
        pcbTest.addAProject("Test01");
        assertEquals("Test01",pcbTest.projects.get(0).name);
        assertEquals("Test01",pcbTest.current_project.name);
        assertEquals(1,pcbTest.projects.size());
    }

    /**
     * {@link ProjectCombobox#addAProject(String)}
     * No given name
     */
    @Test
    public void testAddAProjectNull1() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        exc.expect(NullPointerException.class);
        pcbTest.addAProject(null);
    }

    /**
     * {@link ProjectCombobox#selectProject(String)}
     * The "right" way
     */
    @Test
    public void testSelectProject() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Test01");
        pcbTest.addAProject("Test02");
        pcbTest.selectProject("Test01");
        assertEquals("Test01",pcbTest.current_project.name);
        assertEquals(2,pcbTest.projects.size());
    }
    /**
     * {@link ProjectCombobox#selectProject(String)}
     * Pass in null for a project selection.
     */
    @Test
    public void testSelectProjectNull() {
        ProjectCombobox pcbTest = new ProjectCombobox(comboTest);
        pcbTest.addAProject("Test01");
        pcbTest.addAProject("Test02");
        exc.expect(NullPointerException.class);
        pcbTest.selectProject(null);
    }
}