package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public Message insertMessage(Message msg){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, msg.getPosted_by());
            preparedStatement.setString(2, msg.getMessage_text());
            preparedStatement.setLong(3, msg.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkrs = preparedStatement.getGeneratedKeys();
            if(pkrs.next()){
                int pk_id = (int) pkrs.getLong(1);
                return new Message(pk_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), 
                    rs.getInt("posted_by"), rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch")));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByMessageId(int msg_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, msg_id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return new Message();
    }

    public boolean deleteMessageUsingMessageId(int msg_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, msg_id);

            int change = preparedStatement.executeUpdate();
            if(change > 0){
                // deletion successful
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        // deletion not succesful
        return false;
    }

    public boolean updateMessageBody(int msg_id, String bodyUpdate){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bodyUpdate);
            preparedStatement.setInt(2, msg_id);

            int change = preparedStatement.executeUpdate();
            if(change > 0){
                // message_text was updated successfully
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        // message_text was not updated successfully
        return false;
    }

    public List<Message> getAllMessagesForTheUser(int acc_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, acc_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), 
                    rs.getInt("posted_by"), rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch")));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
