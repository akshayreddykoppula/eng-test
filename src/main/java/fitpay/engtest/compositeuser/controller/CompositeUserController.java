package fitpay.engtest.compositeuser.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import fitpay.engtest.compositeuser.entity.CompositeUser;
import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.User;
import fitpay.engtest.compositeuser.service.CompositeUserService;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeUserController {
	
	private static final Logger logger = LoggerFactory.getLogger(CompositeUserController.class);
	
	@Autowired
	private CompositeUserService compositeUserService;
	
	@GetMapping("/compositeUsers/{userId}")
	public CompositeUser retrieveCompositeUsers(@PathVariable String userId, Optional<String> creditCardState, Optional<String> deviceState) {
		
		logger.info("Retrieving composite users. [HTTP method: GET, URI:  /compositeUsers/{userId}, PathVariable: " + userId + ", Query params: [creditCardState: " +  creditCardState + ", deviceState: "+ deviceState +"]]");
		
		EntityModel<User> userResource = compositeUserService.getUserById(userId);
		
		String userCreditCardUrl = userResource.getLinks().getLink("creditCards").get().getHref();
		List<CreditCard> userCreditCards = compositeUserService.getUserCreditCardList(userCreditCardUrl, creditCardState);
		
		String userDeviceUrl = userResource.getLinks().getLink("devices").get().getHref();
		List<Device> userDevices = compositeUserService.getUserDeviceList(userDeviceUrl, deviceState);
		
		logger.info("Returning composite user info...");
		
		return new CompositeUser(userResource.getContent().getUserId(), userCreditCards, userDevices);
	}
}
