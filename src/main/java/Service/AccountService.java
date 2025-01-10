package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService (AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount (Account account){
        //account = accountDAO.insertAccount(account);
        return account;
    }    

    //should not have an account_id
    public Account verifyAccount(Account account){
        //need to check if user and pass exist
        return account;
    }
}