package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO ){
        this.messageDAO = messageDAO;
    }

    public Message addMessage (Message message){
        message = messageDAO.postMessage(message);
        return message;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMsgId (int message_id){
        if (messageDAO.getMessageByMessageId(message_id) != null){
            return messageDAO.getMessageByMessageId(message_id);
        } else{
            return null;
        }
    }

    public Message deleteMessageByMsgId (int message_id){
        if (messageDAO.getMessageByMessageId(message_id) != null){
            Message deletedMessage = messageDAO.getMessageByMessageId(message_id);
            messageDAO.deleteMessage(message_id);
            return deletedMessage;
        } else{
            return null;
        }
    }

    public Message updateMessage (int message_id, Message message, String new_message){
        if (messageDAO.getMessageByMessageId(message_id) != null){
            messageDAO.updateMessage(message_id, message, new_message);
            return messageDAO.getMessageByMessageId(message_id);
        } else{
            return null;
        }
    }

    public List<Message> getAllMessagesByUserId (int account_id){
        return messageDAO.getAllMessagesByUserId(account_id);
    }

}
