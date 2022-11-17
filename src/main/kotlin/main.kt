data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val postType: String,
    val canPin: Boolean,
    val canDelete: Boolean,
    val markAsAds: Boolean
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

        fun add (post: Post): Post {
            posts += post
            return posts.last()
        }
    }
}

fun main() {
    println("Hello, Kotlin")
}