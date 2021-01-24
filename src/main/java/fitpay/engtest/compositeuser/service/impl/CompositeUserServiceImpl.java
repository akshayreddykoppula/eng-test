package fitpay.engtest.compositeuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.CreditCardList;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.DeviceList;
import fitpay.engtest.compositeuser.entity.User;
import fitpay.engtest.compositeuser.service.CompositeUserService;

@Service
public class CompositeUserServiceImpl implements CompositeUserService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EntityModel<User> findUserById(String userId, HttpEntity<String> request) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userId", userId);
		
		String USER_URL = "https://api.qa.fitpay.ninja/users/{userId}";
		
		ResponseEntity<EntityModel<User>> responseEntity = restTemplate.exchange(USER_URL, 
				HttpMethod.GET, 
				request, 
				new ParameterizedTypeReference<EntityModel<User>>() {}, uriVariables);
		
		return responseEntity.getBody();
	}

	@Override
	public List<CreditCard> findUserCreditCardList(EntityModel<User> userResource, HttpEntity<String> request, Optional<String> creditCardState) {
		
		String USER_CREDITCARDS_URL = userResource.getLinks().getLink("creditCards").get().getHref();
		
		ResponseEntity<CreditCardList> creditCardListResponseEntity = restTemplate.exchange(USER_CREDITCARDS_URL, 
				HttpMethod.GET, 
				request, 
				new ParameterizedTypeReference<CreditCardList>() {});
		
		List<CreditCard> userCreditCards =  creditCardListResponseEntity.getBody().getCreditCards();
		
		if(creditCardState.isPresent()) {
			userCreditCards = userCreditCards.stream().filter(cc -> creditCardState.get().equals(cc.getState())).collect(Collectors.toList());
		}
		
		return userCreditCards;
	}

	@Override
	public List<Device> findUserDeviceList(EntityModel<User> userResource, HttpEntity<String> request, Optional<String> deviceState) {
		
		String USER_DEVICES_URL = userResource.getLinks().getLink("devices").get().getHref();
		
		ResponseEntity<DeviceList> deviceListResponseEntity = restTemplate.exchange(USER_DEVICES_URL, 
				HttpMethod.GET, 
				request, 
				new ParameterizedTypeReference<DeviceList>() {});
		
		List<Device> userDevices =  deviceListResponseEntity.getBody().getDevices();
		
		if(deviceState.isPresent()) {
			userDevices = userDevices.stream().filter(d -> deviceState.get().equals(d.getState())).collect(Collectors.toList());
		}
		
		return userDevices;
	}

}
