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

    class Likes(count: Int, var userLikes: Boolean, var canLike: Boolean, val canPublish: Boolean) {
        var count = count
            set(value) {
                if (value < 0) {
                    return
                }
                field = value
            }
    }

    class Comments(
        count: Int,
        val canPost: Boolean,
        val groupCanPost: Boolean,
        val canClose: Boolean,
        val canOpen: Boolean
    ) {
        var count = count
            set(value) {
                if (value < 0) {
                    return
                }
                field = value
            }
    }

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

        fun update (post: Post): Boolean {

            for ((index, post1) in posts.withIndex()) {
                if (post.id == post1.id){
                    posts[index] = post.copy()
                    return true
                }
            }
            return false
        }
    }


}

fun main() {
    println("Hello, Kotlin")
}