data class Chat(

    val id: Int = 0,
    val ownerId: Int = 0,
    val recipientId: Int = 0,
    val date: Long = 0,
    val messageList: MutableList<Message> = mutableListOf(),
    var messageCount: Int = messageList.size,
    var unreadMessagesCount: Int = messageList.filter { !it.isRead }.size,
    var nextMessageId: Int = 0,

    ) {

    fun receiveMessage(message: Message): Message {
        val message1 = message.copy(id = ++nextMessageId, isReceived = true,)
        messageList += message1
        return messageList.last()
    }

    fun sendMessage(message: Message): Message {
        val message1 = message.copy(id = ++nextMessageId, isRead = true)
        messageList += message1
        return messageList.last()
    }

    fun editMessage(message: Message): Boolean {
        for ((index, messageInList) in messageList.withIndex()) {
            if (messageInList.id == message.id) {
                messageList[index] = messageInList.copy(text = message.text)
                return true
            }
        }
        throw MessageNotFoundException("This message not exist")
    }

    fun deleteMessage(justMessage: Message): Boolean {
        for ((index, message) in messageList.withIndex()) {
            if (message.id == justMessage.id) {
                messageList.removeAt(index)
                if (this.messageCount == 0) ChatService.deleteChat(this)
                return true
            }
        }
        throw MessageNotFoundException("Message not found")
    }
}

data class Message(

    val id: Int = 0,
    val ownerId: Int = 0,
    val recipientId: Int = 0,
    val date: Long = 0,
    val text: String = "",
    val isRead: Boolean = false,
    val isReceived: Boolean = false
)


object ChatService {

    private var chats = mutableListOf<Chat>()
    private var nextChatsId = 0

    fun clear () {
        chats = mutableListOf<Chat>()
        nextChatsId = 0
    }

    fun addChat(newChat: Chat): Chat {

        for (chat in chats) {
            if (chat == newChat) {
                return chat
            }
        }
        val chat1 = newChat.copy(id = ++nextChatsId)
        chats += chat1
        return chats.last()
    }

    fun deleteChat (justChat: Chat): Boolean {

        for ((index, chat) in chats.withIndex()) {
            if (chat.id == justChat.id) {
                chats.removeAt(index)
                return true
            }
        }
        throw ChatNotFoundException ("Chat not found")
    }

    fun createMessage(message: Message) {
        for (chat in chats) {
            if (chat.recipientId == message.recipientId) {
                chat.sendMessage(message)
                return
            }
        }
        val newChat = Chat(ownerId = message.ownerId, recipientId = message.recipientId)
        newChat.sendMessage(message)
        addChat(newChat)
    }

    fun receiveMessage(message: Message) {
        for (chat in chats) {
            if (chat.recipientId == message.ownerId) {
                chat.receiveMessage(message)
                return
            }
        }
        val newChat = Chat(ownerId = message.recipientId, recipientId = message.ownerId)
        newChat.receiveMessage(message)
        addChat(newChat)
    }

    fun getUnreadChatsCount(ownerId: Int): Int {
        return chats.filter { it.ownerId == ownerId }.filter { it.unreadMessagesCount > 0 }.size
    }

    fun getChats(ownerId: Int): List<Chat> {
        val list = chats.filter { it.ownerId == ownerId }.filter { it.messageCount > 0 }
        return list.ifEmpty {
            println("Нет сообщений")
            list
        }
    }

    fun getMessagesList(chatId: Int, lastMessageId: Int, messageCount: Int): List<Message> {
        val getChat = chats.find { it.id == chatId }

        val indexLastMessage = getChat?.messageList?.indexOfFirst { it.id == lastMessageId }
            ?: throw ChatNotFoundException("Chat not found")

        val list = getChat.messageList.subList(
            indexLastMessage,
            if (indexLastMessage + messageCount > getChat.messageList.size - indexLastMessage - 1)
                getChat.messageList.size - 1 else indexLastMessage + messageCount
        )

        for ((index, message) in getChat.messageList.withIndex()) {
            if (index in indexLastMessage until indexLastMessage + list.size && message.isReceived)
                getChat.messageList[index] = message.copy(isRead = true)
        }

        return list
    }
}

class MessageNotFoundException(message: String) : Exception(message)
class ChatNotFoundException(message: String) : Exception(message)

//fun main() {
//    ChatService.createMessage()
//}