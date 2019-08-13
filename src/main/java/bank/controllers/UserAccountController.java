package bank.controllers;

import bank.AuthenticationHelperService;
import bank.RequestValidator;
import bank.model.dto.BankAccountDTO;
import bank.model.entity.BankAccount;
import bank.model.entity.Transaction;
import bank.model.entity.User;
import bank.model.entity.UserDetails;
import bank.responses.ServiceResponse;
import bank.services.UserAccountService;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.TransactionDaoService;
import bank.services.dbServices.UserDetailsDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static bank.ApplicationProperties.*;

/**
 * User features controller
 */
@RestController
@RequestMapping(value = "/user")
public class UserAccountController {

    private UserAccountService userAccountService;
    private AuthenticationHelperService authenticationHelperService;
    private BankAccountDaoService bankAccountDaoService;
    private RequestValidator validator;
    private UserDetailsDaoService userDetailsDaoService;
    private TransactionDaoService transactionDaoService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService,
                                 AuthenticationHelperService authenticationHelperService,
                                 BankAccountDaoService bankAccountDaoService,
                                 RequestValidator validator,
                                 UserDetailsDaoService userDetailsDaoService,
                                 TransactionDaoService transactionDaoService) {
        this.userAccountService = userAccountService;
        this.authenticationHelperService = authenticationHelperService;
        this.bankAccountDaoService = bankAccountDaoService;
        this.validator = validator;
        this.userDetailsDaoService = userDetailsDaoService;
        this.transactionDaoService = transactionDaoService;
    }

    /**
     * Handling request of getting user page, adding currency rate, needed messages.
     *
     * @param request - request from JSP with messages of completed/failed
     *                transaction, etc.
     * @return user page view
     */
    @RequestMapping(value = "/user_page", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showUserPage(HttpServletRequest request) {
        ModelAndView mnv = new ModelAndView(USER_PAGE);
        String resultMessage = request.getParameter("resultMessage");
        if (resultMessage != null) {
            mnv.addObject("resultMessage", resultMessage);
        }
        User authUser = authenticationHelperService.getAuthenticatedUser(false);
        mnv.addObject("authUser", authUser);
        List<BankAccount> userBankAccounts = bankAccountDaoService
                .getBankAccountsByUserId(authUser.getId());
        List<String> roleList = authenticationHelperService.getAuthUserRoles();
        mnv.addObject("bankAccounts", userBankAccounts);
        mnv.addObject("userRoles", roleList);
        UserDetails userDetails = userDetailsDaoService.findByUser(authUser);
        if (userDetails != null) {
            if (userDetails.getProfileImage() != null) {
                String base64image = Base64.getEncoder().encodeToString(userDetails.getProfileImage());
                if (base64image.equals("")) {
                } else {
                    mnv.addObject("myImage", base64image);
                }
            }
        }
        return mnv;
    }


    /**
     * Method processes a AJAX call request of getting currency rates form NBRB.
     *
     * @param currency - currency name.
     * @return json with currency rate and ok status.
     */
    @ResponseBody
    @RequestMapping(value = "/getCurrency", method = RequestMethod.GET)
    public ResponseEntity<String> getCurrency(@RequestParam String currency) {
        String json = userAccountService.getCurrencyRate(currency);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        responseHeaders.setCacheControl("no-cache, max-age=0");
        return new ResponseEntity<>(json, responseHeaders, HttpStatus.OK);
    }

    /**
     * Handling AJAX request form bank accounts reading view
     *
     * @param bankAccountId - specific username in
     * @return all bank accounts if username field was empty
     * or bank accounts of specific user if username field wasn't empty
     */
    @ResponseBody
    @RequestMapping(value = "/filterBankAccounts", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllBankAccounts(@RequestParam Long bankAccountId) {
        BankAccountDTO bankAccountDTO = bankAccountDaoService.getBankAccountDTOById(bankAccountId);
        ServiceResponse<BankAccountDTO> response = new ServiceResponse<>("success", bankAccountDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Method processes a request of getting add money page.
     *
     * @return add money page.
     */
    @RequestMapping(value = "/addMoney", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showAddMoneyPage() {
        ModelAndView mnv = new ModelAndView("add_money_page");
        List<BankAccount> bankAccounts = bankAccountDaoService.getAllBankAccounts();
        mnv.addObject("bankAccounts", bankAccounts);
        return mnv;
    }

    /**
     * Method processes a request to add money on bank account and calls a service for that.
     *
     * @param request - request, that was send form view.
     * @return user page with result of adding money on bank account.
     */
    @RequestMapping(value = "/doAddMoney", method = {RequestMethod.POST})
    public RedirectView doAddMoney(HttpServletRequest request) {
        try {
            validator.isValidAddMoneyRequest(request);
            Long bankAccountID = Long.parseLong(request.getParameter("bankAccounts"));
            Integer moneyToAdd = Integer.parseInt(request.getParameter("moneyToAdd"));
            userAccountService.addMoney(bankAccountID, moneyToAdd);
            return new RedirectView(USER_PAGE + "?resultMessage=Money adding completed!");
        } catch (Exception ex) {
            return new RedirectView(USER_PAGE + "?resultMessage=" + ex.getMessage());
        }
    }

    /**
     * Method processes a request of getting add bank account page.
     *
     * @return add bank account page.
     */
    @RequestMapping(value = "/addBankAccount", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showBankAccountAddingMenu() {
        return new ModelAndView(ADD_BANK_ACCOUNT_PAGE);
    }

    /**
     * Method processes a request to add bank account for user and calls a service for that.
     *
     * @return user page with result of adding new bank account for user.
     */
    @RequestMapping(value = "/doAddBankAccount", method = {RequestMethod.POST, RequestMethod.GET})
    public RedirectView doAddBankAccount() {
        userAccountService.addBankAccount();
        return new RedirectView(USER_PAGE);
    }

    /**
     * Method processes a request of getting add organisation page.
     *
     * @return add organisation page.
     */
    @RequestMapping(value = "/addOrganisation", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView showAddOrganisationPage() {
        ModelAndView mnv = new ModelAndView(ADD_ORGANISATION);
        User authUser = authenticationHelperService.getAuthenticatedUser();
        if (authUser == null) {
            return new ModelAndView(new RedirectView(USER_PAGE + "?resultMessage=No free bank accounts!"));
        }
        List<BankAccount> bankAccountList = authUser.getBankAccounts().stream()
                .filter(x -> x.getOrganisation() == null)
                .collect(Collectors.toList());
        if (bankAccountList.isEmpty()) {
            return new ModelAndView(new RedirectView(USER_PAGE + "?resultMessage=No free bank accounts!"));
        }
        mnv.addObject("bankAccounts", bankAccountList);
        return mnv;
    }

    /**
     * Method processes a request to add organisation for user.
     *
     * @param request - request, that was send form view.
     * @return user page with result of adding organisation.
     */
    @RequestMapping(value = "/doAddOrganisation", method = {RequestMethod.POST})
    public ModelAndView addOrganisation(HttpServletRequest request) {
        try {
            validator.isValidAddOrganisationRequest(request);
            Long bankAccountId = Long.valueOf(request.getParameter("bankAccounts"));
            String organisationName = request.getParameter("organisationName");
            userAccountService.addOrganisation(bankAccountId, organisationName);
            return new ModelAndView(new RedirectView(USER_PAGE + "?resultMessage=Organisation successfully added!"));
        } catch (Exception ex) {
            return new ModelAndView(new RedirectView(USER_PAGE + "?resultMessage=" + ex.getMessage()));
        }
    }

    /**
     * Method processes a request of getting user transaction history page.
     *
     * @return user transaction history page.
     */
    @RequestMapping(value = "/showTransactionsHistory")
    public ModelAndView showUserTransactionsHistory() {
        ModelAndView mnv = new ModelAndView(USER_TRANSACTIONS_HISTORY_PAGE);
        User authUser = authenticationHelperService.getAuthenticatedUser(true);
        if (authUser == null) {
            return new ModelAndView(new RedirectView(USER_PAGE + "?resultMessage=You have not bank accounts!"));
        }
        List<BankAccount> userBankAccounts = authUser.getBankAccounts();
        List<Transaction> transactionsFromUser = new ArrayList<>();
        List<Transaction> transactionsToUser = new ArrayList<>();
        for (BankAccount iter : userBankAccounts) {
            transactionsFromUser.addAll(transactionDaoService.findBySourceBankAccount(iter));
            transactionsToUser.addAll(transactionDaoService.findByDestinationBankAccount(iter));
        }
        mnv.addObject("transactionsFromUser", transactionsFromUser);
        mnv.addObject("transactionsToUser", transactionsToUser);
        if (transactionsFromUser.isEmpty() && transactionsToUser.isEmpty()) {
            mnv.setView(new RedirectView(USER_PAGE + "?resultMessage=Transactions history is empty!"));
        }
        return mnv;
    }
}
