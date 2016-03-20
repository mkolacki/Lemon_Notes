import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by alex on 3/9/2016.
 */
public final class ProjectTest
{
    /**
     * For expecting exceptions
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Mock
    private Project mockProject; // Project would be better off just being made but
                                // I figured it would be a useful example

    /**
     * For setting up variables for tests
     */
    @Before
    public void setup()
    {
        //This is required
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test method for {@link Project#Project(String)}
     *
     */
    @Test
    public void testProject()
    {
        exception.expect(NullPointerException.class);
        new Project(null);
    }

    /**
     * Test method for {@link Project#addNote}
     *
     */
    @Test
    public void testAddNote()
    {
        Project project = new Project("Test");
        project.addNote("Note", "Subject", true);
        assertEquals(1, project.notes.size());
        assertEquals("Note", project.notes.get(0).note);
    }
    /**
     * Test method for {@link Project#addNote}
     *
     */
    @Test
    public void testAddNoteNull1()
    {
        Project project = new Project("Test");
        exception.expect(NullPointerException.class);
        project.addNote(null, "Subject", true);
    }

    /**
     * Test method for {@link Project#addNote}
     *
     */
    @Test
    public void testAddNoteNull2()
    {
        Project project = new Project("Test");
        exception.expect(NullPointerException.class);
        project.addNote("Note", null, true);
    }

    /**
     * Test method for {@link Project#addNote}
     *
     */
    @Test
    public void testAddNoteNull3()
    {
        Project project = new Project("Test");
        exception.expect(NullPointerException.class);
        project.addNote("Note", "Subject", null);
    }

}