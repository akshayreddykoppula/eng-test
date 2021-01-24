package fitpay.engtest.compositeuser.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import fitpay.engtest.compositeuser.entity.CompositeUser;
import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.User;
import fitpay.engtest.compositeuser.service.CompositeUserService;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeUserController {

	private static final String TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJLN3paSTB1QSIsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MTY2XC9vYXV0aFwvdG9rZW4iLCJjbGllbnRfaWQiOiJLN3paSTB1QSIsImF1dGhvcml0aWVzIjpbInVzZXIucmVhZCIsInVzZXIud3JpdGUiLCJ1c2Vycy5yZWFkIiwidXNlcnMud3JpdGUiLCJvcmdhbml6YXRpb25zLkVOR1RFU1QiLCJjcmVkaXRDYXJkcy5yZWFkIiwiY3JlZGVudGlhbHMucmVhZCIsImRldmljZXMucmVhZCJdLCJhdWQiOlsidXNlciIsInVzZXJzIiwib3JnYW5pemF0aW9ucyIsImNyZWRpdENhcmRzIiwiY3JlZGVudGlhbHMiLCJkZXZpY2VzIl0sInppZCI6ImlkZW50aXR5LWFwaSIsImdyYW50X3R5cGUiOiJjbGllbnRfY3JlZGVudGlhbHMiLCJhenAiOiJLN3paSTB1QSIsInNjb3BlIjpbInVzZXIucmVhZCIsInVzZXIud3JpdGUiLCJ1c2Vycy5yZWFkIiwidXNlcnMud3JpdGUiLCJvcmdhbml6YXRpb25zLkVOR1RFU1QiLCJjcmVkaXRDYXJkcy5yZWFkIiwiY3JlZGVudGlhbHMucmVhZCIsImRldmljZXMucmVhZCJdLCJleHAiOjE2MTE0NzMwNzksImlhdCI6MTYxMTQyOTg3OSwianRpIjoiMTY5YzI1YjItMGNmOC00MjQwLWEwNTYtZTJjNzcxMTQxYTUxIiwiY2lkIjoiSzd6WkkwdUEifQ.to0hHJSbSeZ78587-ctZKjd8haSq_hlh_MCBh7XgpA2WTOuh74fMUNogg9GPLbZOIf6B2TBzayd06HdFgBdLqiXdZ17veCYmOwYGzJ97Ak7mbZdS63-SlCyW-83rHub_fXpcKAYwtAqqzr1IcJ8tUCuqVKYEBH1xCE0-JurKRZ00DWlP3KEeVlAQxvT4eTnxxGf3XxSMzHtgrmp1jah-UZMZBLkzi6B0xdPYYhPpW4CoQIDIvzwWKeCceTu_XFpAM0LxL1VtsaurN4QEAvRPQDenlJMBBmimJH1f9MhEyv5rF_TCZcb_eCO1riH1xHOR3pZhWEdukyQn3iSn9fHQ3A";	
	
	@Autowired
	private CompositeUserService compositeUserService;
	
	@GetMapping("/compositeUsers/{userId}")
	public CompositeUser retrieveCompositeUsers(@PathVariable String userId, Optional<String> creditCardState, Optional<String> deviceState) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + TOKEN);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		EntityModel<User> userResource = compositeUserService.findUserById(userId, request);
		
		List<CreditCard> userCreditCards = compositeUserService.findUserCreditCardList(userResource, request, creditCardState);
				
		List<Device> userDevices = compositeUserService.findUserDeviceList(userResource, request, deviceState);
		
		return new CompositeUser(userResource.getContent().getUserId(), userCreditCards, userDevices);
	}
}
