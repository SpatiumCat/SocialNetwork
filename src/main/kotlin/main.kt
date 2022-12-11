data class Post(

    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int? = 0,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    val comments: Comments = Comments(),
    val copyright: Copyright = Copyright(),
    val likes: Likes = Likes(),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val postType: String? = "",
    val postSource: PostSource = PostSource(),
    var attachment: Array<Attachment> = arrayOf(
        AudioAttachment(),
        VideoAttachment(),
        NoteAttachment(),
        DocumentAttachment(),
        LinkAttachment()
    ),
    val geo: Geo = Geo(),
    val signerId: Int = 0,
    val copyHistory: Array<Post> = emptyArray(),
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Boolean = false,
    val markAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val donut: Donut = Donut(),
    val postPonedId: Int = 0
) {
}

data class Views(var count: Int = 0)
data class Reposts(var count: Int = 0, val userReposted: Boolean = false)

data class Likes(

    var count: Int = 0,
    var userLikes: Boolean = false,
    val canLike: Boolean = false,
    val canPublish: Boolean = false
)

data class Comments(

    val count: Int = 0,
    val canPost: Boolean = false,
    val groupCanPost: Boolean = false,
    val canClose: Boolean = false,
    val canOpen: Boolean = false
)

data class Copyright(val id: Int = 0, val link: String? = null, val name: String? = null, val tape: String? = null)

data class PostSource(

    val type: String? = null,
    val platform: String? = null,
    val data: String? = null,
    val url: String? = null
)

data class Geo(val type: String? = null, val coordinates: String? = null)

data class Donut(

    val isDonut: Boolean = false,
    val paidDuration: Int = 0,
    val placeholder: Placeholder = Placeholder(),
    val canPublishFreeCopy: Boolean = false,
    val editMode: String? = null,
)

class Placeholder()

sealed class Attachment(val type: String)
class AudioAttachment(val audio: Audio = Audio()) : Attachment("audio")
class VideoAttachment(val video: Video = Video()) : Attachment("video")
class DocumentAttachment(val document: Document = Document()) : Attachment("document")
class LinkAttachment(val link: Link = Link()) : Attachment("link")
class NoteAttachment(val note: Note = Note()) : Attachment("note")

data class Audio(

    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0,
    val url: String = "",
    val lyricsId: Int = 0,
    val albumId: Int = 0,
    val genreId: Int = 0,
    val date: Int = 0,
    val noSearch: Boolean = false,
    val isHq: Boolean = false
)

data class Video(

    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val description: String = "",
    val duration: Int = 0,
    val date: Int = 0,
    val addingDate: Int = 0,
    var views: Int = 0,
    var local_views: Int = 0,
    var comments: Int = 0,
    val player: String = "",
    val platform: String? = "",
    val canAdd: Boolean = false,
    val isPrivate: Boolean = false
)

data class Document(

    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val size: Int = 0,
    val ext: String = "",
    val url: String? = "",
    val date: Int = 0,
    val type: Int = 0,
)

data class Link(

    val url: String? = "",
    val title: String = "",
    val caption: String = "",
    val description: String = "",
    val previewPage: String = "",
    val previewUrl: String = "",
)

data class Note(

    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val date: Int = 0,
    val text: String = "",
    var comments: Int = 0,
    var readComments: Int = 0,
    val viewUrl: String? = "",
    val privacyView: String = "",
    val commentPrivacy: Privacy = UserOnly(),
    val privacy: Privacy = UserOnly(),
    val canComment: Boolean = false,
    val commentsList: MutableList<Comment> = mutableListOf(),
    val deletedCommentsList: MutableList<Comment> = mutableListOf()

) {


    companion object {

        var notes: MutableList<Note> = mutableListOf()
        var nextNoteId = 0

        fun clear() {
            notes = mutableListOf()
            nextNoteId = 0
        }

        fun addNote(title: String, text: String, privacy: Privacy, commentPrivacy: Privacy): Int {

            val note =
                Note(++nextNoteId, title = title, text = text, privacy = privacy, commentPrivacy = commentPrivacy)
            notes += note
            return note.id
        }

        fun deleteNote(noteId: Int): Boolean {

            for (note in notes) {
                if (note.id == noteId) {
                    notes.remove(note)
                    return true
                }
            }
            throw NoteNotFoundException("Note not found")
        }

        fun editNote(noteId: Int, title: String, text: String, privacy: Privacy, commentPrivacy: Privacy): Boolean {

            for ((index, note) in notes.withIndex()) {
                if (note.id == noteId) {
                    notes[index] = notes[index].copy(
                        id = noteId,
                        title = title,
                        text = text,
                        privacy = privacy,
                        commentPrivacy = commentPrivacy
                    )
                    return true
                }
            }
            throw NoteNotFoundException("Note was deleted or not exist")
        }

        fun getNote(vararg noteIds: Int): List<Note> {

            val noteList: MutableList<Note> = mutableListOf()

            loop@for (id in noteIds) {
                for (note in notes) {
                    if (note.id == id) {
                        noteList += note
                        continue@loop
                    }
                }
                throw NoteNotFoundException("Note with id $id not found")
            }
            return noteList
        }
    }

    private var nextCommentId = 0

    fun createComment(noteId: Int, message: String): Int {

        for (note in notes) {
            if (note.id == noteId) {
                note.commentsList += Comment(++nextCommentId, text = message)
                this.comments = commentsList.size
                return note.commentsList.last().id
            }
        }
        throw NoteNotFoundException("Note was deleted or not exist")
    }

    fun deleteComment(commentId: Int): Boolean {

        for ((index, comment) in commentsList.withIndex()) {
            if (comment.id == commentId) {
                deletedCommentsList.add(comment.copy(isDelete = true))
                commentsList.removeAt(index)
                this.comments = commentsList.size
                return true
            }
        }
        throw CommentNotFoundException("Comment was deleted or not exist")
    }

    fun editComment(commentId: Int, message: String): Boolean {

        for ((index, comment) in commentsList.withIndex()) {
            if (comment.id == commentId) {
                commentsList[index] = commentsList[index].copy(text = message)
                return true
            }
        }
        throw CommentNotFoundException("Comment was deleted or not exist")
    }

    fun getComment(noteId: Int): List<Comment> {
        for (note in notes) {
            if (note.id == noteId) {
                return note.commentsList
            }
        }
        throw NoteNotFoundException("Note not found")
    }

    fun restoreComment(commentId: Int): Boolean {

        for ((index, comment) in deletedCommentsList.withIndex()) {
            if (comment.id == commentId) {
                commentsList.add(comment.copy(isDelete = false))
                deletedCommentsList.removeAt(index)
                this.comments = commentsList.size
                return true
            }
        }
        throw CommentNotFoundException("Comment not found")
    }
}

data class Comment(

    val id: Int = 0,
    val fromId: Int? = 0,
    val date: Int = 0,
    var text: String = "",
    val donut: Donut = Donut(),
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    val attachment: Array<Attachment> = emptyArray(),
    val parentsStack: Array<Any> = emptyArray<Any>(),
    val thread: Thread = Thread(),
    val isDelete: Boolean = false

)

data class Thread(
    val count: Int = 0,
    val items: Array<Any> = emptyArray(),
    val canPost: Boolean = false,
    val showReplyButton: Boolean = false,
    val groupCanPost: Boolean = false
)

data class ReportComment(val ownerId: Int = 0, val commentId: Int = 0, val reason: ReasonOfReport = Spam())

sealed class ReasonOfReport(var message: String = "")
class Spam(message: String = "") : ReasonOfReport(message)
class PornWithKids(message: String = "") : ReasonOfReport(message)
class Extremism(message: String = "") : ReasonOfReport(message)
class Violent(message: String = "") : ReasonOfReport(message)
class DrugPropaganda(message: String = "") : ReasonOfReport(message)
class AdultContent(message: String = "") : ReasonOfReport(message)
class Insult(message: String = "") : ReasonOfReport(message)
class CallForSuicide(message: String = "") : ReasonOfReport(message)

sealed class Privacy()
class AllUsers() : Privacy()
class FriendsOnly() : Privacy()
class FriendsAndFriendsOfFriends() : Privacy()
class UserOnly() : Privacy()

class PostNotFoundException(message: String) : Exception(message)
class CommentNotFoundException(message: String) : Exception(message)
class NoteNotFoundException(message: String) : Exception(message)


object WallService {

    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reportComments = emptyArray<ReportComment>()
    private var nextIdPost = 0
    private var nextIdComment = 0

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                comments += comment.copy(id = ++nextIdComment)
                return comments.last()
            }
        }
        throw PostNotFoundException("No such posts with this ID")
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray<Comment>()
        reportComments = emptyArray<ReportComment>()
    }

    fun add(post: Post): Post {
        val post1 = post.copy(id = ++nextIdPost)
        posts += post1
        return posts.last()
    }

    fun update(newPost: Post): Boolean {

        for ((index, post) in posts.withIndex()) {
            if (newPost.id == post.id) {
                posts[index] = newPost.copy(ownerId = post.ownerId, date = post.date)
                return true
            }
        }
        return false
    }

    fun createReportAComment(commentId: Int, report: ReportComment): ReportComment {

        for (comment in comments) {
            if (comment.id == commentId) {
                reportComments += report.copy(commentId = commentId)
                return reportComments.last()
            }
        }
        throw CommentNotFoundException("No such comment with this ID")
    }
}


fun main() {



    Note.addNote("Третья заметка", "..был сильный мороз", FriendsOnly(), FriendsOnly())






    Note.notes[0].restoreComment(1)

    Note.notes[0].editComment(1, "Обновленный комментарий к первой заметке")

    Note.editNote(
        Note.notes[1].id,
        "Обновленная вторая заметка",
        "${Note.notes[1].text} Обновление",
        Note.notes[1].privacy, Note.notes[1].commentPrivacy
    )

    val noteList = Note.getNote(1, 3)

    Note.deleteNote(1)


    println(Note.notes.toString())
    println(Note.notes[0].commentsList.toString())
    println(noteList.toString())
    println("Hello, Kotlin")
}