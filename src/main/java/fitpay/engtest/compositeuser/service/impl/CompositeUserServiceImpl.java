package fitpay.engtest.compositeuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
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
	
	@Value("${fitpay.api.users.url}")
	private String usersUrl;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EntityModel<User> getUserById(String userId) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userId", userId);
		
		ResponseEntity<EntityModel<User>> responseEntity = restTemplate.exchange(usersUrl, 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<EntityModel<User>>() {}, uriVariables);
		
		return responseEntity.getBody();
	}

	@Override
	public List<CreditCard> getUserCreditCardList(EntityModel<User> userResource, Optional<String> creditCardState) {
		
		String userCreditCardsUrl = userResource.getLinks().getLink("creditCards").get().getHref();
		
		CreditCardList creditCardList = restTemplate.getForObject(userCreditCardsUrl, CreditCardList.class);
		
		List<CreditCard> userCreditCards =  creditCardList.getCreditCards();
		
		if(creditCardState.isPresent()) {
			userCreditCards = userCreditCards.stream().filter(cc -> creditCardState.get().equals(cc.getState())).collect(Collectors.toList());
		}
		
		return userCreditCards;
	}

	@Override
	public List<Device> getUserDeviceList(EntityModel<User> userResource, Optional<String> deviceState) {
		
		String userDevicesUrl = userResource.getLinks().getLink("devices").get().getHref();
		
		DeviceList deviceList = restTemplate.getForObject(userDevicesUrl, DeviceList.class);
		
		List<Device> userDevices =  deviceList.getDevices();
		
		if(deviceState.isPresent()) {
			userDevices = userDevices.stream().filter(d -> deviceState.get().equals(d.getState())).collect(Collectors.toList());
		}
		
		return userDevices;
	}

}
