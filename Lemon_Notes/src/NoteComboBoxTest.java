import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael K. on 3/23/2016.
 */
public class NoteComboBoxTest {
    @Rule
    public ExpectedException exc = ExpectedException.none();

    JFXComboBox<String> comboTest = new JFXComboBox<String>();
    static Project project1;
    static Project project2;

    @Mock
    private NoteComboBox mockProject;

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @AfterClass
    public static void tearDown() { //remove created files after testing
        File delete_me = new File("Projects/" + project1.name);
        File delete_me_notes = new File("Projects/" + project1.name + "/saved_notes");
        File delete_me_too = new File("Projects/" + project2.name);
        File delete_me_too_notes = new File("Projects/" + project2.name + "/saved_notes");
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
        project1 = new Project("Test 01");
        project2 = new Project("Test 02");
    }

    /**
     * {@link NoteComboBox#NoteComboBox(Project, JFXComboBox)}
     * Attempt to build a null ProjectComboBox
     */
    @Test
    public void testNoteComboBoxNullOne() {
        exc.expect(NullPointerException.class);
        new NoteComboBox(null, comboTest);
    }

    /**
     * {@link NoteComboBox#NoteComboBox(Project, JFXComboBox)}
     * Attempt to build a null ProjectComboBox
     */
    @Test
    public void testNoteComboBoxNullTwo() {
        exc.expect(NullPointerException.class);
        new NoteComboBox(project1, null);
    }

     /**
     * {@link NoteComboBox#resize()}
     * Test the ability to resize the NoteComboBox after both adding and removing notes from the "current project".
     */
    @Test
    public void testResize() {
        NoteComboBox noteComboBox = new NoteComboBox(project1, comboTest);
        assertEquals(0, noteComboBox.notes.size());
        assertEquals(project1, noteComboBox.current_project);
        assertEquals(comboTest, noteComboBox.comboBox);
        project1.addNote("A note", "Note 1", true);
        project1.addNote("Another note", "Note 2", true);
        project1.addNote("And lastly...", "Note 3", true);
        noteComboBox.resize();
        assertEquals(3, noteComboBox.notes.size());
        assertEquals(project1, noteComboBox.current_project);
        assertEquals(project1.notes, noteComboBox.notes);
        assertEquals(comboTest, noteComboBox.comboBox);
        project1.removeNote(project1.notes.get(project1.notes.size() - 1));
        project1.removeNote(project1.notes.get(project1.notes.size() - 1));
        project1.removeNote(project1.notes.get(project1.notes.size() - 1));
        noteComboBox.resize();
        assertEquals(0, noteComboBox.notes.size());
        assertEquals(project1, noteComboBox.current_project);
        assertEquals(project1.notes, noteComboBox.notes);
        assertEquals(comboTest, noteComboBox.comboBox);
    }

    /**
     * {@link NoteComboBox#changeProjects(Project)}
     * Attempts to change the "current project" to a non-existent one.
     */
    @Test
    public void testChangeProjectsNullOne(){
        exc.expect(NullPointerException.class);
        NoteComboBox noteComboBox = new NoteComboBox(null, comboTest);
    }

    /**
     * {@link NoteComboBox#changeProjects(Project)}
     * Attempts to change the "current project" with no provided combo box.
     */
    @Test
    public void testChangeProjectsNullTwo(){
        exc.expect(NullPointerException.class);
        NoteComboBox noteComboBox = new NoteComboBox(project1, null);
    }

    /**
     * {@link NoteComboBox#changeProjects(Project)}
     * Tests the ability to change the "current project" for which notes are displayed.
     */
    @Test
    public void testChangeProjects(){
        NoteComboBox noteComboBox = new NoteComboBox(project1, comboTest);
        project1.addNote("Note in the first Project", "Note 1", true);
        project1.addNote("Second note in the first Project", "Note 2", true);
        project2.addNote("Note in the second Project", "Technically, Still Note 1", true);
        noteComboBox.resize();
        assertEquals(2, noteComboBox.notes.size());
        assertEquals(project1, noteComboBox.current_project);
        assertEquals(project1.notes, noteComboBox.notes);
        assertEquals(comboTest, noteComboBox.comboBox);
        noteComboBox.changeProjects(project2);
        assertEquals(1, noteComboBox.notes.size());
        assertEquals(project2, noteComboBox.current_project);
        assertEquals(project2.notes, noteComboBox.notes);
        assertEquals(comboTest, noteComboBox.comboBox);
        project1.removeNote(project1.notes.get(project1.notes.size() - 1));
        project1.removeNote(project1.notes.get(project1.notes.size() - 1));
        project2.removeNote(project2.notes.get(project2.notes.size() - 1));
    }


}
