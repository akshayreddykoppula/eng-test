package fitpay.engtest.compositeuser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;

import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.User;

public interface CompositeUserService {

	EntityModel<User> getUserById(String id);
	
	List<CreditCard> getUserCreditCardList(EntityModel<User> userResource, Optional<String> creditCardState);
	
	List<Device> getUserDeviceList(EntityModel<User> userResource, Optional<String> deviceState);
}
