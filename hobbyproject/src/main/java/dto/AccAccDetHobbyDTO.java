package dto;

import entities.Account;
import entities.AccountDetail;
import entities.Hobby;
import lombok.Getter;

@Getter
public class AccAccDetHobbyDTO {

    private Account account;
    private AccountDetail accountDetail;
    private Hobby hobby;


    public AccAccDetHobbyDTO(Account account, AccountDetail accountDetail, Hobby hobby) {
        this.account = account;
        this.accountDetail = accountDetail;
        this.hobby = hobby;
    }
}
