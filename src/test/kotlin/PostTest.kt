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
    fun createComment_ShouldReturnComment(){

        val post = Post()
        val comment = Comment(text = "Hello, Netology")

        val returnPost = WallService.add(post)
        val returnComment = WallService.createComment(returnPost.id, comment)

        assertEquals(comment.text, returnComment.text)
    }

    @Test (expected = PostNotFoundException::class)
    fun createComment_ShouldThrowException() {

        val post = Post()
        val comment = Comment(text = "Hello, Netology")

        val returnPost = WallService.add(post)
        val returnComment = WallService.createComment(returnPost.id + 1, comment)

    }

    @Test
    fun createReportAComment_ShouldTurnReport () {

        val post = Post()
        val comment = Comment(text = "Hello, World")
        val report = ReportComment()

        val returnPost = WallService.add(post)
        val returnComment = WallService.createComment(returnPost.id, comment)
        val returnReport = WallService.createReportAComment(returnComment.id, report)

        assertEquals(returnComment.id, returnReport.commentId)
        assertTrue(returnReport.reason is Spam)
    }

    @Test (expected = CommentNotFoundException::class)
    fun createReportAComment_ShouldThrowException () {

        val post = Post()
        val comment = Comment(text = "Hello, World")
        val report = ReportComment()

        val returnPost = WallService.add(post)
        val returnComment = WallService.createComment(returnPost.id, comment)
        val returnReport = WallService.createReportAComment(returnComment.id + 1, report)


    }
}