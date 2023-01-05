import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class ChatTest {

    @Before
    fun clearChatsBeforeTests () {
        ChatService.clear()
    }

    @Test
    fun addChat_ShouldReturnChat () {

        val chat = ChatService.addChat(Chat(ownerId = 1, recipientId = 11))

        assertEquals(11, chat.recipientId)
    }

    @Test
    fun deleteChat_ShouldReturnTrue () {

        val chat = ChatService.addChat(Chat(ownerId = 2, recipientId = 22))
        val result = ChatService.deleteChat(chat)

        assertTrue(result)
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteChat_ShouldThrowException() {

        val chat = ChatService.addChat(Chat(ownerId = 3, recipientId = 33))
        val chat2 = Chat(ownerId = 4, recipientId = 44)

        val result = ChatService.deleteChat(chat2)
    }

    @Test
    fun createMessage_ShouldReturnMessage() {

    }
}