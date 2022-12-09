import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteTest {

    @Before
    fun clearNoteBeforeTest() {
    }

    @Test
    fun noteAdd_ShouldReturnId() {

        Note.clear()

        val returnIdFirstNote = Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        assertEquals(1, returnIdFirstNote)
        assertEquals(2, returnIdSecondNote)
    }

    @Test
    fun deleteNote_ShouldReturnTrue () {

        Note.clear()

        val returnIdFirstNote = Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

       val result =  Note.deleteNote(2)

        assertTrue(result)
    }

    @Test (expected = NoteNotFoundException::class)
    fun deleteNote_ShouldThrowException () {

        Note.clear()

        val returnIdFirstNote = Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        Note.deleteNote(3)

    }

    @Test
    fun editNote_ShouldReturnTrue () {

        Note.clear()

        val returnIdFirstNote = Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        Note.addNote(Note.notes[1].title, Note.notes[1].text, Note.notes[1].privacy, Note.notes[1].commentPrivacy )

        val result = Note.editNote(2,
            "Обновленная вторая заметка",
            Note.notes[1].text,
            Note.notes[1].privacy,
            Note.notes[1].commentPrivacy)

        assertTrue(result)
        assertNotEquals(Note.notes[1].title, Note.notes[2].title)
    }

    @Test (expected = NoteNotFoundException::class)
    fun editNote_ShouldThrowException () {

        Note.clear()

        val returnIdFirstNote = Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        Note.editNote(3,
            "Обновленная вторая заметка",
            Note.notes[1].text,
            Note.notes[1].privacy,
            Note.notes[1].commentPrivacy)

    }
}