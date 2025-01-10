package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    public Account insertAccount (Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet accIdResultSet = preparedStatement.getGeneratedKeys();
            if (accIdResultSet.next()){
                int generated_account_id = (int) accIdResultSet.getLong (1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account verifyAccount (Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account acc = new Account(rs.getString("username"),
                    rs.getString("password"));
                return acc;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
