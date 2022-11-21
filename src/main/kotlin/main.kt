import kotlin.random.Random

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val postType: String = "",
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val markAsAds: Boolean = false
) {
}

data class Likes(var count: Int, var userLikes: Boolean, var canLike: Boolean, val canPublish: Boolean)

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)


object WallService {

    private var posts = emptyArray<Post>()
    private var nextId = 0

    fun clear () {
        posts = emptyArray()
    }

    fun add (post: Post): Post {
        val post1 = post.copy(id = ++nextId)
        posts += post1
        return posts.last()
    }

    fun update (newPost: Post): Boolean {

        for ((index, post) in posts.withIndex()) {
            if (newPost.id == post.id){
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