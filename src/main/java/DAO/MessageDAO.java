package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class MessageDAO {

    public Message postMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.posted_by);
            preparedStatement.setString(2, message.message_text);
            preparedStatement.setLong(3, message.time_posted_epoch);

            preparedStatement.executeUpdate();
            ResultSet mIdResultSet = preparedStatement.getGeneratedKeys();
            if (mIdResultSet.next()){
                int generated_message_id = (int) mIdResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List <Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message( rs.getInt("message_id"),
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            } 

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByMessageId (int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message where message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                return message;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        return null;
    }

    public void deleteMessage (int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            preparedStatement.executeUpdate();
            // while(rs.next()){
            //     Message message = new Message(rs.getInt("message_id"),
            //         rs.getInt("posted_by"), 
            //         rs.getString("message_text"),
            //         rs.getLong("time_posted_epoch"));
            //     return message;
            // }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void updateMessage (int id, Message message, String new_message){ //add a String new_message object?
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // message.setMessage_text(new_message);
            // System.out.print("NEW MESSAGE: " + new_message);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, new_message);
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List <Message> getAllMessagesByUserId (int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List <Message> userMessages = new ArrayList<>();

        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                userMessages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return userMessages;
    }
    
}
