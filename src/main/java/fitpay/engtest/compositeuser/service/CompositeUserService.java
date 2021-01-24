package fitpay.engtest.compositeuser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;

import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.User;

public interface CompositeUserService {

	EntityModel<User> findUserById(String id, HttpEntity<String> request);
	
	List<CreditCard> findUserCreditCardList(EntityModel<User> userResource, HttpEntity<String> request, Optional<String> creditCardState);
	
	List<Device> findUserDeviceList(EntityModel<User> userResource, HttpEntity<String> request, Optional<String> deviceState);
}
