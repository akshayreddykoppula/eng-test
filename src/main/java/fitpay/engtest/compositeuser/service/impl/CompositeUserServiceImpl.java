package fitpay.engtest.compositeuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(CompositeUserServiceImpl.class);
	
	@Value("${fitpay.api.user.url}")
	private String userUrl;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EntityModel<User> getUserById(String userId) {
		
		logger.info("Calling User API. [User Url: " + userUrl + ", userId: " + userId + "]");
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("userId", userId);
		
		ResponseEntity<EntityModel<User>> responseEntity = restTemplate.exchange(userUrl, 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<EntityModel<User>>() {}, uriVariables);
		
		logger.info("Returning User Entity Model...");
		
		return responseEntity.getBody();
	}

	@Override
	public List<CreditCard> getUserCreditCardList(String userCreditCardUrl, Optional<String> creditCardState) {
		
		logger.info("Calling User Credit Card API. [userCreditCardUrl: " + userCreditCardUrl + ", creditCardState: " + creditCardState + "]");
		
		CreditCardList creditCardList = restTemplate.getForObject(userCreditCardUrl, CreditCardList.class);
		
		List<CreditCard> userCreditCards =  creditCardList.getCreditCards();
		
		if(creditCardState.isPresent()) {
			userCreditCards = userCreditCards.stream().filter(cc -> creditCardState.get().equals(cc.getState())).collect(Collectors.toList());
		}
		
		logger.info("Returning User Credit Cards after filtering by creditCardState if present...");
		
		return userCreditCards;
	}

	@Override
	public List<Device> getUserDeviceList(String userDeviceUrl, Optional<String> deviceState) {
		
		logger.info("Calling User Device API. [userDeviceUrl: " + userDeviceUrl + ", deviceState: " + deviceState + "]");
		
		DeviceList deviceList = restTemplate.getForObject(userDeviceUrl, DeviceList.class);
		
		List<Device> userDevices =  deviceList.getDevices();
		
		if(deviceState.isPresent()) {
			userDevices = userDevices.stream().filter(d -> deviceState.get().equals(d.getState())).collect(Collectors.toList());
		}
		
		logger.info("Returning User Devices after filtering by deviceState if present...");
		
		return userDevices;
	}

}
