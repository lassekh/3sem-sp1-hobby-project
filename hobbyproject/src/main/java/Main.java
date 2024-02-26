import config.HibernateConfig;
import dao.AccountDAO;
import entities.Account;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();

        Account account = new Account("Lasse");
        AccountDAO dao = new AccountDAO();
        //dao.create(account);

    }
}