package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.*;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message addMessage(Message msg){
        boolean exists = accountDAO.checkIfAccountIdExists(msg.getPosted_by());
        if(!(msg.getMessage_text().isBlank()) && (msg.getMessage_text().length() < 256) & (exists)){
            return messageDAO.insertMessage(msg);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMessageId(int msg_id){
        return messageDAO.getMessageByMessageId(msg_id);
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
