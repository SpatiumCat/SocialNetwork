import kotlin.random.Random

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

sealed class Attachment (val type: String)
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
    val canComment: Boolean = false,

    )

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
    val thread: Thread = Thread()

)

data class Thread (
    val count: Int = 0,
    val items: Array<Any> = emptyArray(),
    val canPost: Boolean = false,
    val showReplyButton: Boolean = false,
    val groupCanPost: Boolean = false
        )
class PostNotFoundException(message: String): Exception(message)


object WallService {

    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextIdPost = 0
    private var nextIdComment = 0

    fun creatComment (postId: Int, comment: Comment): Comment {
        for (post in posts){
            if (post.id == postId){
                comments += comment.copy(id = ++nextIdComment)
                return comments.last()
            }
        }
        throw PostNotFoundException("No such posts with this ID")
    }

    fun clear() {
        posts = emptyArray()
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
}

fun main() {
    println("Hello, Kotlin")
}