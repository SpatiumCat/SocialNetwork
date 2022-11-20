import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import Post.WallService

class PostTest {

    @Before
    fun clearBeforeTest () {
        WallService.clear()
    }

    @Test
    fun addTest_IdNotEqualZero(){

        val post = Post()
        val result = WallService.add(post)

        assertNotEquals(post.id, result.id)
    }

    @Test
    fun UpdateTestTrue(){

        val post = Post()

        val updatedPostId = WallService.add(post).id
        val updatedPost = Post(updatedPostId, text = "another post")
        val result = WallService.update(updatedPost)

        assertEquals(true, result)
    }

    @Test
    fun UpdateTestFalse(){

        val post = Post()

        val updatedPostId = WallService.add(post).id
        val updatedPost = Post(updatedPostId + 1, text = "other post")
        val result = WallService.update(updatedPost)

        assertEquals(false, result)
    }
}