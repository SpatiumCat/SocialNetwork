import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteTest {

    @Before
    fun clearNoteBeforeTest() {
        Note.clear()
    }

    @Test
    fun noteAdd_ShouldReturnId() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        assertEquals(1, returnIdFirstNote)
        assertEquals(2, returnIdSecondNote)
    }

    @Test
    fun deleteNote_ShouldReturnTrue() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        val result = Note.deleteNote(returnIdSecondNote)

        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNote_ShouldThrowException() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        Note.deleteNote(3)

    }

    @Test
    fun editNote_ShouldReturnTrue() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        Note.addNote(Note.notes[1].title, Note.notes[1].text, Note.notes[1].privacy, Note.notes[1].commentPrivacy)

        val result = Note.editNote(
            returnIdSecondNote,
            "Обновленная вторая заметка",
            Note.notes[1].text,
            Note.notes[1].privacy,
            Note.notes[1].commentPrivacy
        )

        assertTrue(result)
        assertNotEquals(Note.notes[1].title, Note.notes[2].title)
    }

    @Test(expected = NoteNotFoundException::class)
    fun editNote_ShouldThrowException() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())

        Note.editNote(
            returnIdSecondNote + 1,
            "Обновленная вторая заметка",
            Note.notes[1].text,
            Note.notes[1].privacy,
            Note.notes[1].commentPrivacy
        )

    }

    @Test
    fun getNote_ShouldReturnList() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val noteList = Note.getNote(returnIdFirstNote, returnIdThirdNote)

        assertEquals(returnIdFirstNote, noteList[0].id)
        assertNotEquals(returnIdSecondNote, noteList[0].id)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getNote_ShouldThrowException() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val noteList = Note.getNote(returnIdFirstNote, returnIdThirdNote + 1)
    }

    @Test
    fun createComment_ShouldReturnId() {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val returnedIdComment = Note.notes[0].createComment(Note.notes[0].id, "Комментарий к первой заметке")

        assertEquals(1, returnedIdComment)
    }

    @Test (expected = NoteNotFoundException::class)
    fun createComment_ShouldTrowException () {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val returnedIdComment = Note.notes[0].createComment(Note.notes[0].id + 5, "Комментарий к первой заметке")
    }

    @Test
    fun deleteComment_ShouldReturnTrue () {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val returnedIdComment = Note.notes[0].createComment(Note.notes[0].id, "Комментарий к первой заметке")

        val isSuccessfulDeletedComment = Note.notes[0].deleteComment(1)

        assertTrue(isSuccessfulDeletedComment)
    }

    @Test
    fun editComment_ShouldReturnTrue () {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val returnedIdComment = Note.notes[0].createComment(Note.notes[0].id, "Комментарий к первой заметке")
        val textComment = Note.notes[0].commentsList[0].text

        val result =  Note.notes[0].editComment(1, "Обновленный комментарий к первой заметке")


        assertTrue(result)
        assertNotEquals(textComment, Note.notes[0].commentsList[0].text)

    }

    @Test (expected = CommentNotFoundException::class)
    fun editComment_ShouldThrowException () {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        val returnedIdComment = Note.notes[0].createComment(Note.notes[0].id, "Комментарий к первой заметке")
        val textComment = Note.notes[0].commentsList[0].text

        val result = Note.notes[0].editComment(3, "Обновленный комментарий к первой заметке")
    }

    @Test
    fun getComment_ShouldReturnList () {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        Note.notes[0].createComment(Note.notes[0].id, "Комментарий к первой заметке")
        Note.notes[0].createComment(Note.notes[0].id, "Второй комментарий к первой заметке")
        Note.notes[1].createComment(Note.notes[1].id, "Комментарий ко второй заметке")

        val textComment = Note.notes[0].commentsList[1].text

        val commentList = Note.notes[0].getComment(Note.notes[0].id)

        assertEquals(2, commentList.size)
        assertEquals(textComment, commentList[1].text)
    }

    @Test (expected = NoteNotFoundException::class)
    fun getComment_ShouldThrowException () {

        Note.clear()

        val returnIdFirstNote =
            Note.addNote("Первая заметка", "Однажды в студеную зимнюю пору...", FriendsOnly(), FriendsOnly())
        val returnIdSecondNote = Note.addNote("Вторая заметка", "..Я из лесу вышел...", FriendsOnly(), FriendsOnly())
        val returnIdThirdNote = Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())

        Note.notes[0].createComment(Note.notes[0].id, "Комментарий к первой заметке")
        Note.notes[0].createComment(Note.notes[0].id, "Второй комментарий к первой заметке")
        Note.notes[1].createComment(Note.notes[1].id, "Комментарий ко второй заметке")

        val textComment = Note.notes[0].commentsList[1].text

        val commentList = Note.notes[0].getComment(Note.notes[0].id + 5)
    }
}