package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService (AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount (Account account){
        account = accountDAO.insertAccount(account);
        return account;
    }    

    public Account verifyAccount(Account account){
        return accountDAO.verifyAccount(account);
    }
}