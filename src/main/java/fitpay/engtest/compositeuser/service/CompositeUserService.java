package fitpay.engtest.compositeuser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;

import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.User;

public interface CompositeUserService {

    EntityModel<User> getUserById(String id) throws Exception;

    List<CreditCard> getUserCreditCardList(String userCreditCardUrl, Optional<String> creditCardState) throws Exception;

    List<Device> getUserDeviceList(String userDeviceUrl, Optional<String> deviceState) throws Exception;
}
