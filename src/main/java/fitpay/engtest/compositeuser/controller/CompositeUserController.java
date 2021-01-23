package fitpay.engtest.compositeuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import fitpay.engtest.compositeuser.entity.CompositeUser;
import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.CreditCardList;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.DeviceList;
import fitpay.engtest.compositeuser.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CompositeUserController {

	private static final String TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJLN3paSTB1QSIsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MTY2XC9vYXV0aFwvdG9rZW4iLCJjbGllbnRfaWQiOiJLN3paSTB1QSIsImF1dGhvcml0aWVzIjpbInVzZXIucmVhZCIsInVzZXIud3JpdGUiLCJ1c2Vycy5yZWFkIiwidXNlcnMud3JpdGUiLCJvcmdhbml6YXRpb25zLkVOR1RFU1QiLCJjcmVkaXRDYXJkcy5yZWFkIiwiY3JlZGVudGlhbHMucmVhZCIsImRldmljZXMucmVhZCJdLCJhdWQiOlsidXNlciIsInVzZXJzIiwib3JnYW5pemF0aW9ucyIsImNyZWRpdENhcmRzIiwiY3JlZGVudGlhbHMiLCJkZXZpY2VzIl0sInppZCI6ImlkZW50aXR5LWFwaSIsImdyYW50X3R5cGUiOiJjbGllbnRfY3JlZGVudGlhbHMiLCJhenAiOiJLN3paSTB1QSIsInNjb3BlIjpbInVzZXIucmVhZCIsInVzZXIud3JpdGUiLCJ1c2Vycy5yZWFkIiwidXNlcnMud3JpdGUiLCJvcmdhbml6YXRpb25zLkVOR1RFU1QiLCJjcmVkaXRDYXJkcy5yZWFkIiwiY3JlZGVudGlhbHMucmVhZCIsImRldmljZXMucmVhZCJdLCJleHAiOjE2MTE0NzMwNzksImlhdCI6MTYxMTQyOTg3OSwianRpIjoiMTY5YzI1YjItMGNmOC00MjQwLWEwNTYtZTJjNzcxMTQxYTUxIiwiY2lkIjoiSzd6WkkwdUEifQ.to0hHJSbSeZ78587-ctZKjd8haSq_hlh_MCBh7XgpA2WTOuh74fMUNogg9GPLbZOIf6B2TBzayd06HdFgBdLqiXdZ17veCYmOwYGzJ97Ak7mbZdS63-SlCyW-83rHub_fXpcKAYwtAqqzr1IcJ8tUCuqVKYEBH1xCE0-JurKRZ00DWlP3KEeVlAQxvT4eTnxxGf3XxSMzHtgrmp1jah-UZMZBLkzi6B0xdPYYhPpW4CoQIDIvzwWKeCceTu_XFpAM0LxL1VtsaurN4QEAvRPQDenlJMBBmimJH1f9MhEyv5rF_TCZcb_eCO1riH1xHOR3pZhWEdukyQn3iSn9fHQ3A";	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/compositeUsers/{userId}")
	public CompositeUser retrieveCompositeUsers(@PathVariable String userId, @RequestParam(required = false) String creditCardState, @RequestParam(required = false) String deviceState) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + TOKEN);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userId", userId);
		
		String USER_URL = "https://api.qa.fitpay.ninja/users/{userId}";
		
		ResponseEntity<EntityModel<User>> responseEntity = restTemplate.exchange(USER_URL, 
				HttpMethod.GET, 
				request, 
				new ParameterizedTypeReference<EntityModel<User>>() {}, uriVariables);
		
		EntityModel<User> userResource = responseEntity.getBody();
		
		String USER_CREDITCARDS_URL = userResource.getLinks().getLink("creditCards").get().getHref();
		String USER_DEVICES_URL = userResource.getLinks().getLink("devices").get().getHref();
		
		ResponseEntity<CreditCardList> creditCardListResponseEntity = restTemplate.exchange(USER_CREDITCARDS_URL, 
				HttpMethod.GET, 
				request, 
				new ParameterizedTypeReference<CreditCardList>() {});
		
		List<CreditCard> userCreditCards =  creditCardListResponseEntity.getBody().getCreditCards();
		
		if(creditCardState != null) {
			userCreditCards = userCreditCards.stream().filter(cc -> creditCardState.equals(cc.getState())).collect(Collectors.toList());
		}
		
		ResponseEntity<DeviceList> deviceListResponseEntity = restTemplate.exchange(USER_DEVICES_URL, 
				HttpMethod.GET, 
				request, 
				new ParameterizedTypeReference<DeviceList>() {});
		
		List<Device> userDevices =  deviceListResponseEntity.getBody().getDevices();
		
		if(deviceState != null) {
			userDevices = userDevices.stream().filter(d -> deviceState.equals(d.getState())).collect(Collectors.toList());
		}
		
		return new CompositeUser(userResource.getContent().getUserId(), 
				userCreditCards, 
				userDevices);
		
	}
}
