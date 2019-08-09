package bank.controllers;

import bank.AuthenticationHelperService;
import bank.BankCardNumberGenerator;
import bank.RequestValidator;
import bank.model.dto.BankAccountDTO;
import bank.model.entity.BankAccount;
import bank.model.entity.Role;
import bank.model.entity.User;
import bank.responses.ServiceResponse;
import bank.services.UserAccountService;
import bank.services.dbServices.BankAccountDaoService;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static bank.ApplicationProperties.USER_PAGE;

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
	private UserDaoService userDaoService;

	@Autowired
	public UserAccountController(UserAccountService userAccountService,
								 AuthenticationHelperService authenticationHelperService,
								 BankAccountDaoService bankAccountDaoService,
								 RequestValidator validator,
								 UserDaoService userDaoService) {
		this.userAccountService = userAccountService;
		this.authenticationHelperService = authenticationHelperService;
		this.bankAccountDaoService = bankAccountDaoService;
		this.validator = validator;
		this.userDaoService = userDaoService;
	}

	/**
	 * Handling request of getting user page, adding currency rate, needed messages.
	 *
	 * @param request - request from JSP with messages of completed/failed
	 *                transaction, etc.
	 * @return user page view
	 */
	@RequestMapping(value = "/user_page", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showUserPage(HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView(USER_PAGE);

		String resultMessage = request.getParameter("resultMessage");
		if (resultMessage != null) {
			mnv.addObject("resultMessage", resultMessage);
		}

		User authUser = authenticationHelperService.getAuthenticatedUser();
		mnv.addObject("authUser", authUser);

		List<BankAccount> userBankAccounts =
				bankAccountDaoService.getBankAccountsByUserId(authenticationHelperService.getAuthenticatedUser()
						.getId());

		List<String> roleList = authenticationHelperService.getAuthUserRoles();
		mnv.addObject("bankAccounts", userBankAccounts);
		mnv.addObject("userRoles", roleList);

		return mnv;
	}


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
	 * Handling AJAX call form bank accounts reading view
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

	@RequestMapping(value = "/addMoney", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView showAddMoneyPage() {
		ModelAndView mnv = new ModelAndView("add_money_page");
		List<BankAccount> bankAccounts = bankAccountDaoService.getAllBankAccounts();
		mnv.addObject("bankAccounts", bankAccounts);
		return mnv;
	}

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

	@RequestMapping(value = "/addBankAccount", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView showBankAccountAddingMenu() {
		return new ModelAndView("add_bank_account_page");
	}

	@RequestMapping(value = "/doAddBankAccount", method = {RequestMethod.POST, RequestMethod.GET})
	public RedirectView doAddBankAccount(HttpServletRequest request) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setMoney(0.);
		bankAccount.setUser(authenticationHelperService.getAuthenticatedUser());
		BankCardNumberGenerator generator= new BankCardNumberGenerator();
		bankAccount.setCardNumber(generator.generate("21", 16));
		bankAccountDaoService.saveBankAccount(bankAccount);
		return new RedirectView(USER_PAGE);
	}

	@RequestMapping(value = "/a", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView reg() {
		return new ModelAndView("new");
	}
}
