import org.hamcrest.Description;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Mike on 3/9/2016.
 */
public final class ProjectTest {

    @Rule
    public ExpectedException exc = ExpectedException.none();


    @AfterClass
    public static void tearDown() { //remove created files after testing
        File dir = new File("Projects/Test01");
        for (File file:dir.listFiles()) {
            file.delete();
        }
        dir.delete();
    }
    /**
     * Test method for addNote method in the Project class.
     * {@link Project#addNote}
     * Attempting to make a project with a null project_name param
     */
    @Test
    public void nullProj() {
        exc.expect(NullPointerException.class);
        new Project(null);
    }
    /**
     * Test method for addNote method in the Project class.
     * {@link Project#addNote}
     * The "correct" setup to construct a project and add a note to it
     */
    @Test
    public void testAddNote() {
        Project proj = new Project("Test01");
        proj.addNote("Note","Subject",true);
        assertEquals("Note", proj.notes.get(0).note);
        assertEquals(1, proj.notes.size());
    }
    /**
     * Test method for addNote method in the Project class.
     * {@link Project#addNote}
     * Attempt adding a note to the project without any note content
     */
    @Test
    public void testAddNoteNull1() {
        Project proj = new Project("Test01");
        exc.expect(NullPointerException.class);
        proj.addNote(null,"Subject",true);
    }

    /**
     * Test method for addNote method in the Project class.
     * {@link Project#addNote}
     * Attempt to make a note without a subject
     */
    @Test
    public void testAddNoteNull2() {
        Project proj = new Project("Test01");
        exc.expect(NullPointerException.class);
        proj.addNote("Note",null,true);
    }
    /**
     * Test method for addNote method in the Project class.
     * {@link Project#addNote}
     * Attempt to pass a null boolean in
     */
    @Test
    public void testAddNoteNull3() {
        Project proj = new Project("Test01");
        exc.expect(NullPointerException.class);
        proj.addNote("Note","Subject",null);
    }
}