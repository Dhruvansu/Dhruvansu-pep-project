package Service;

import DAO.MessageDAO;
import Model.*;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message msg){
        return null;
    }

    public List<Message> getAllMessages(){
        return null;
    }

    public Message getMessageByMessageId(int msg_id){
        return null;
    }

    public Message deleteMessage(int msg_id, Message msg){
        return null;
    }

    public Message messageBodyUpdate(int msg_id, String bodyUpdate){
        return null;
    }

    public List<Message> getMessageByAccountId(int acc_id){
        return null;
    }
}
