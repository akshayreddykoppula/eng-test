package fitpay.engtest.compositeuser.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fitpay.engtest.compositeuser.entity.User;
import fitpay.engtest.compositeuser.service.CompositeUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompositeUserServiceImplTest {
	
	private static final String USER_ID = "7d3500f9-8676-4775-9bb8-03dc6a4fffe1";

	@Autowired
	private CompositeUserService compositeUserService;
	
	@Test
	public void whenGetUserById_thenReturnUser() throws Exception {
		
		// when
		User found =  compositeUserService.getUserById(USER_ID).getContent();
		
		// then
		assertThat(found.getUserId()).isEqualTo(USER_ID);
		
		assertEquals(found.getUserId(), USER_ID);
	}
	
}	
