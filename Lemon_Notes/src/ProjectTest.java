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
        File delete_me = new File("Projects/Test01");
        File delete_me_notes = new File("Projects/Test01/saved_notes");
        if(delete_me.exists()){
            delete_me_notes.delete();
            delete_me.delete();
        }
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
        proj.removeNote(proj.notes.get(proj.notes.size() - 1));
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

    /**
     * Test method for removeNote method in the Project class.
     * {@link Project#removeNote(Note)}
     * Remove an added note
     */
    @Test
    public void testRemoveNote(){
        Project project = new Project("Test01");
        project.addNote("Second Note", "Note 2", true);
        Boolean pass = project.removeNote(project.notes.get(project.notes.size() - 1));
        assertTrue(pass);
    }

    /**
     * Test method for removeNote method in the Project class.
     * {@link Project#removeNote(Note)}
     * Attempt to pass a null note
     */
    @Test
    public void testRemoveNoteNull(){
        Project project = new Project("Test01");
        exc.expect(NullPointerException.class);
        Boolean irrelevant = project.removeNote(null);
    }

    /**
     * Test method for selectNote method in the Project class.
     * {@link Project#selectNote(String)}
     * Select a created note.
     */
    @Test
    public void testSelectNote(){
        Project project = new Project("Test01");
        project.addNote("Another Test Note", "Note 3", true);
        Note actual = project.selectNote("Note 3");
        assertEquals("Note 3", actual.subject);
        assertEquals("Another Test Note", actual.note);
        project.removeNote(project.notes.get(project.notes.size() - 1));
    }

    /**
     * Test method for selectNote method in the Project class.
     * {@link Project#selectNote(String)}
     * Attempt to select a null note.
     */
    @Test
    public void testSelectNoteNull(){
        Project project = new Project("Test01");
        exc.expect(NullPointerException.class);
        Note irrelevant = project.selectNote(null);
    }
}