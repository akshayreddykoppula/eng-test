package fitpay.engtest.compositeuser.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fitpay.engtest.compositeuser.entity.CreditCard;
import fitpay.engtest.compositeuser.entity.Device;
import fitpay.engtest.compositeuser.entity.User;
import fitpay.engtest.compositeuser.exception.BadRequestException;
import fitpay.engtest.compositeuser.exception.NotFoundException;
import fitpay.engtest.compositeuser.service.CompositeUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompositeUserServiceImplTest {

    @Value("${fitpay.api.valid.user.id}")
    private String validUserId;

    @Value("${fitpay.api.invalid.user.id}")
    private String invalidUserId;

    @Value("${fitpay.api.user.creditcard.url}")
    private String userCreditCardUrl;

    @Value("${fitpay.api.user.device.url}")
    private String userDeviceUrl;

    @Value("${fitpay.api.user.invalid.creditcard.url}")
    private String invalidUserCreditCardUrl;

    @Value("${fitpay.api.user.invalid.device.url}")
    private String invalidUserDeviceUrl;

    @Autowired
    private CompositeUserService compositeUserService;

    @Test
    public void testGetUserById_valid() throws Exception {
        User user = compositeUserService.getUserById(validUserId).getContent();
        assertEquals(user.getUserId(), validUserId);
    }

    @Test
    public void testGetUserById_invalidUserId() throws Exception {
        assertThrows(BadRequestException.class, () -> {
            compositeUserService.getUserById(invalidUserId).getContent();
        });
    }

    @Test
    public void testGetUserCreditCardList_valid() throws Exception {
        List<CreditCard> userCreditCards = compositeUserService.getUserCreditCardList(userCreditCardUrl,
                Optional.ofNullable(null));
        assertEquals(userCreditCards.size(), 3);
    }

    @Test
    public void testGetUserCreditCardList_invalidRequestURL() throws Exception {
        assertThrows(NotFoundException.class, () -> {
            compositeUserService.getUserCreditCardList(invalidUserCreditCardUrl, Optional.ofNullable(null));
        });
    }

    @Test
    public void testGetUserDeviceList_valid() throws Exception {
        List<Device> userDevices = compositeUserService.getUserDeviceList(userDeviceUrl, Optional.ofNullable(null));
        assertEquals(userDevices.size(), 2);
    }

    @Test
    public void testGetUserDeviceList_invalidRequestURL() throws Exception {
        assertThrows(NotFoundException.class, () -> {
            compositeUserService.getUserDeviceList(invalidUserDeviceUrl, Optional.ofNullable(null));
        });
    }
}
