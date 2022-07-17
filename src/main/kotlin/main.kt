package ru.netology

open class Item(val id: Int)

class Note(
    id: Int,
    val title: String = "Заголовок",
    val text: String = "Тело текста",
    privacyAccess: Int = 1,
    commentOfPrivacy: Int = 1,
    val privacyView: String = "Закрыт",
    val comments: MutableList<Comment> = mutableListOf(),
) : Item(id) {

    var privacy = privacyAccess
        set(value) {
            if (value >= 0 && value <= 3) field = value
        }

    var commentPrivacy = commentOfPrivacy
        set(value) {
            if (value >= 0 && value <= 3) field = value
        }

    override fun toString(): String {
        return "Note id $id with comments: ${comments.filter { !it.deleted }}"
    }
}

data class Comment(
    val commentId: Int,
    val text: String,
    var deleted: Boolean = false,
)

abstract class CrudService<T : Item> {
    var elements = mutableListOf<T>()

    fun add(elem: T): T {
        elements += elem
        return elements.last()
    }


    fun delete(elem: Item) {
        elements.remove(elem)
    }

    fun update(newelem: T): Boolean {
        for ((index, element) in elements.withIndex()) {
            if (newelem.id == element.id) {
                elements[index] = newelem
                return true
            }
        }
        return false
    }

    fun count() = elements.size

    fun print() {
        for (elem in elements) {
            println(elem)
        }
    }

}

class NotFoundException(message: String) : RuntimeException(message)

class NoteService : CrudService<Note>() {

    fun createComment(noteId: Int, comment: Comment): Int {
        var createTrue = false
        var numIndex: Int = -1
        for ((index, element) in elements.withIndex()) {
            if (noteId == element.id) {
                elements[index].comments.add(comment)
                createTrue = true
                numIndex = elements[index].comments.last().commentId
            }

            if (createTrue == false) {
                throw NotFoundException("Comment was not created, because there is no note with id number $noteId")
            }
        }
        return numIndex
    }

    fun deleteComment(noteId: Int, commentId: Int) :Boolean {
        var noNote = false
        var noComment = false
        for ((index, element) in elements.withIndex()) {
            if (noteId == element.id) {
                for ((index1, doubleElement) in elements[index].comments.withIndex()) {
                    if (commentId == doubleElement.commentId) {
                        elements[index].comments[index1].deleted = true
                        noComment = true
                        noNote = true
                    }
                }
                if (noComment == false) {
                    throw NotFoundException("No comment with id $commentId was found in note #id $noteId")
                }
            }
        }
        if (noNote == false) {
            throw NotFoundException("No note with id $noteId was found")
        }
        return true
    }
}

fun main() {
    val noteService = NoteService()
    val note2 = Note(5)
    noteService.add(Note(1))
    noteService.add(Note(2))
    noteService.add(Note(3))
    noteService.add(note2)
    noteService.print()
    noteService.createComment(1, comment = Comment(1, "1111"))
    noteService.createComment(1, comment = Comment(2, "2222"))
    // noteService.createComment(2, comment = Comment(1,"2222"))

    noteService.delete(note2)
    noteService.print()
    // noteService.deleteComment(2,1)
    noteService.deleteComment(1, 1)
    noteService.print()
}