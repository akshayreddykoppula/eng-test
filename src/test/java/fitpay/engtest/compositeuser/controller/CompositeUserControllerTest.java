package fitpay.engtest.compositeuser.controller;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fitpay.engtest.compositeuser.entity.CompositeUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompositeUserControllerTest {

    @Value("${fitpay.api.valid.user.id}")
    private String validUserId;

    @Autowired
    CompositeUserController compositeUserController;

    @Test
    public void testRetrieveCompositeUsers_withQueryParams() throws Exception {
        CompositeUser compositeUser = compositeUserController.retrieveCompositeUsers(validUserId, Optional.of("ACTIVE"),
                Optional.of("INITIALIZED"));
        assertEquals(compositeUser.getUserId(), validUserId);
        assertEquals(compositeUser.getCreditCardIds().size(), 2);
        assertEquals(compositeUser.getDeviceIds().size(), 2);
    }

    @Test
    public void testRetrieveCompositeUsers_withoutQueryParams() throws Exception {
        CompositeUser compositeUser = compositeUserController.retrieveCompositeUsers(validUserId,
                Optional.ofNullable(null), Optional.ofNullable(null));
        assertEquals(compositeUser.getUserId(), validUserId);
        assertEquals(compositeUser.getCreditCardIds().size(), 3);
        assertEquals(compositeUser.getDeviceIds().size(), 2);
    }
}
