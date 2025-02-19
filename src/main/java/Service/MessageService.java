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

    public Message deleteMessage(int msg_id){
        Message exists = messageDAO.getMessageByMessageId(msg_id);
        if((exists != null) && (messageDAO.deleteMessageUsingMessageId(msg_id))){
            return exists;
        }
        return null;
    }

    public Message messageBodyUpdate(int msg_id, String bodyUpdate){
        Message exists = messageDAO.getMessageByMessageId(msg_id);
        if((exists != null) && !(bodyUpdate.isBlank()) && (bodyUpdate.length() < 256) && (messageDAO.updateMessageBody(msg_id, bodyUpdate))){
            return messageDAO.getMessageByMessageId(msg_id);
        }
        return null;
    }

    public List<Message> getMessageByAccountId(int acc_id){
        return messageDAO.getAllMessagesForTheUser(acc_id);
    }
}
