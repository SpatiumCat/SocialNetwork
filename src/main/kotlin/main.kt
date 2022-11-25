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
    val geo: Geo = Geo(),
    val signerId: Int = 0,
    val copyHistory: Array<Post> = emptyArray(),
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Boolean = false,
    val markAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val donut: Donut,
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


object WallService {

    private var posts = emptyArray<Post>()
    private var nextId = 0

    fun clear() {
        posts = emptyArray()
    }

    fun add(post: Post): Post {
        val post1 = post.copy(id = ++nextId)
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