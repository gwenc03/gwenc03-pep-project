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
        return message;
    }

    public List<Message> getAllMessages(){
        //return messageDAO.getAllMessages;
        return null;
    }

    public Message getMessageByMsgId (int message_id, Message message){
        return null;
    }

    public Message deleteMessageByMsgId (int message_id, Message message){
        return null;
    }

    public Message updateMessage (int message_id, Message message){
        return null;
    }

    public List<Message> getAllMessagesByUserId (int account_id, Message message){
        return null;
    }

}
