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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeUserController {
	
	@Autowired
	private CompositeUserService compositeUserService;
	
	@GetMapping("/compositeUsers/{userId}")
	public CompositeUser retrieveCompositeUsers(@PathVariable String userId, Optional<String> creditCardState, Optional<String> deviceState) {
		
		EntityModel<User> userResource = compositeUserService.getUserById(userId);
		
		List<CreditCard> userCreditCards = compositeUserService.getUserCreditCardList(userResource, creditCardState);
				
		List<Device> userDevices = compositeUserService.getUserDeviceList(userResource, deviceState);
		
		return new CompositeUser(userResource.getContent().getUserId(), userCreditCards, userDevices);
	}
}
