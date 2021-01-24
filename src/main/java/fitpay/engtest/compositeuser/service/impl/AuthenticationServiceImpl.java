package fitpay.engtest.compositeuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import fitpay.engtest.compositeuser.entity.AccessToken;
import fitpay.engtest.compositeuser.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Value("${fitpay.api.client.id}")
	private String clientId;
	
	@Value("${fitpay.api.client.secret}")
	private String clientSecret;
	
	@Value("${fitpay.api.oauth.token.url}")
	private String oAuthTokenUrl;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
	@Override
	public String getOauthToken() {
		AccessToken accessToken = restTemplateBuilder
				.basicAuthentication(clientId, clientSecret)
				.build()
				.getForObject(oAuthTokenUrl, AccessToken.class);
		
		return accessToken.getAccessToken();
	}

}
