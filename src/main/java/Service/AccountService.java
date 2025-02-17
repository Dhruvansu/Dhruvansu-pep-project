package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    AccountDAO accountDAO;
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account acc){
        boolean exists = accountDAO.checkIfUsernameExists(acc.getUsername());
        if(!(acc.getUsername().isBlank()) && (acc.getPassword().length() > 3) && !(exists)){
            return accountDAO.insertAccount(acc);
        }
        return null;
    }

    public Account checkIfAccountCredsAreValid(Account acc){
        return accountDAO.validCreds(acc);
    }
}
