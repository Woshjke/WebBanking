package unit;

import bank.model.repositories.BankAccountRepository;
import bank.services.UserAccountService;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.OrganisationDaoService;
import bank.services.dbServices.TransactionDaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.Silent.class)
public class AdminAccountServiceTest {

    @Mock
    private OrganisationDaoService organisationDaoService;

    @Mock
    private BankAccountDaoService bankAccountDaoService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionDaoService transactionDaoService;

    @InjectMocks
    private UserAccountService userAccountService;
}
