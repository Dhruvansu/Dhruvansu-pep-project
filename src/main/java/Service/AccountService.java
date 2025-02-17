package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    AccountDAO accountDAO;
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account acc){
        return null;
    }

    public Account checkIfAccountCredsAreValid(Account acc){
        return null;
    }
}
