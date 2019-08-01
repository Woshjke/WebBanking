package bank.controllers;

import bank.AuthenticationHelper;
import bank.model.entity.BankAccount;
import bank.model.entity.User;
import bank.services.UserAccountService;
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
	private AuthenticationHelper authenticationHelper;

	@Autowired
	public UserAccountController(UserAccountService userAccountService, AuthenticationHelper authenticationHelper) {
		this.userAccountService = userAccountService;
		this.authenticationHelper = authenticationHelper;
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

		User authUser = authenticationHelper.getAuthenticatedUser();
		mnv.addObject("authUser", authUser);

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

//	@RequestMapping(value = "/addMoney", method = {RequestMethod.POST, RequestMethod.GET})
//	public ModelAndView showAddMoneyPage() {
//		ModelAndView mnv = new ModelAndView("add_money_page");
//		List<BankAccount> bankAccounts = bankAccountDaoService.getAllBankAccounts();
//		mnv.addObject("bankAccounts", bankAccounts);
//		return mnv;
//	}
//
//	@RequestMapping(value = "/doAddMoney", method = {RequestMethod.POST})
//	public RedirectView doAddMoney(HttpServletRequest request) {
//		// TODO: 01.08.2019 Написать валидацию
//		Long bankAccountID = Long.parseLong(request.getParameter("bankAccounts"));
//		Integer moneyToAdd = Integer.parseInt(request.getParameter("moneyToAdd"));
//		RedirectView rv = new RedirectView();
//		if (adminService.addMoney(bankAccountID, moneyToAdd)) {
//			rv.setUrl(USER_PAGE + "?resultMessage=Operation completed!");
//		} else {
//			rv.setUrl(USER_PAGE + "?resultMessage=Operation failed!");
//		}
//		return rv;
//	}
}
