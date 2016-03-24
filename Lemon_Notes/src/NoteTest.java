import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Michael K. on 3/24/2016.
 */
public class NoteTest {
    /**
     * {@link Note#Note(String, String)}
     * Attempts to create a Note with no provided "note".
     */
    @Test
    public void testNoteNullOne() {
        Note note = new Note(null, "subject");
        assertNull(note.note);
        assertEquals("subject", note.subject);
    }

    /**
     * {@link Note#Note(String, String)}
     * Attempts to create a Note with no provided "subject".
     */
    @Test
    public void testNoteNullTwo() {
        Note note = new Note("A note", null);
        assertNull(note.subject);
        assertEquals("A note", note.note);
    }

    /**
     * {@link Note#Note(String, String)}
     * Creates a proper note.
     */
    @Test
    public void testNoteConstructor() {
        Note note = new Note("A note", "subject");
        assertEquals("A note", note.note);
        assertEquals("subject", note.subject);
    }
}