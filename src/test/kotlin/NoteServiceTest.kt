import org.junit.Assert.*
import org.junit.Test
import ru.netology.Comment
import ru.netology.NotFoundException
import ru.netology.Note
import ru.netology.NoteService

class NoteServiceTest{

    @Test
    fun addNote() {
        val noteService = NoteService()
        noteService.add(Note(1))
        assertEquals(1, noteService.count())
    }

    @Test
    fun deleteNote() {
        val noteService = NoteService()
        noteService.add(Note(1))
        val note2 = Note(5)
        noteService.add(note2)
        noteService.delete(note2)
        assertEquals(1, noteService.count())
        noteService.print()
    }

    @Test
    fun updateTrue() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)
        val test = Note(1, "Замена")
        val result = noteService.update(test)
        assertEquals(true, result)

    }

    @Test
    fun updateFalse() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)
        val test = Note(2, "Замена")
        val result = noteService.update(test)
        assertEquals(false, result)
    }

    @Test
    fun countTrue() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)
        val test = Note(2, "Замена")
        noteService.add(test)
        val result = noteService.count()
        assertEquals(2, result)

    }

    @Test
    fun addCommentTrue() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)

        val result = noteService.createComment(1, comment = Comment(1, "1111"))
        assertEquals(1, result)
    }

    @Test(expected = NotFoundException::class)
    fun addCommentFalse() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)
        noteService.createComment(5, comment = Comment(1, "1111"))
    }

    @Test
    fun deleteCommentTrue() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)

        noteService.createComment(1, comment = Comment(1, "1111"))
        noteService.createComment(1, comment = Comment(2, "1111"))
        val result = noteService.deleteComment(1, 1)
        assertEquals(true,result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentFirstException() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)

        noteService.createComment(1, comment = Comment(1, "1111"))
        noteService.createComment(1, comment = Comment(2, "1111"))
        val result = noteService.deleteComment(1, 5)

    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentSecondException() {
        val noteService = NoteService()
        val note1 = Note(1)
        noteService.add(note1)

        noteService.createComment(1, comment = Comment(1, "1111"))
        noteService.createComment(1, comment = Comment(2, "1111"))
        val result = noteService.deleteComment(3, 1)

    }
}