import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addTest_IdNotEqualZero() {

        val post = Post()
        val result = WallService.add(post)

        assertNotEquals(post.id, result.id)
    }

    @Test
    fun UpdateTestTrue() {

        val post = Post()

        val updatedPostId = WallService.add(post).id
        val updatedPost = Post(updatedPostId, text = "another post")
        val result = WallService.update(updatedPost)

        assertTrue(result)
    }

    @Test
    fun UpdateTestFalse() {

        val post = Post()

        val updatedPostId = WallService.add(post).id
        val updatedPost = Post(updatedPostId + 1, text = "other post")
        val result = WallService.update(updatedPost)

        assertFalse(result)
    }

    @Test
    fun creatComment_ShouldReturnComment(){

        val post = Post()
        val comment = Comment(text = "Hello, Netology")

        val returnPost = WallService.add(post)
        val returnComment = WallService.creatComment(returnPost.id, comment)

        assertEquals(1, returnComment.id)
        assertEquals(comment.text, returnComment.text)
    }

    @Test (expected = PostNotFoundException::class)
    fun creatComment_ShouldThrowException() {

        val post = Post()
        val comment = Comment(text = "Hello, Netology")

        val returnPost = WallService.add(post)
        val returnComment = WallService.creatComment(returnPost.id + 1, comment)

    }
}