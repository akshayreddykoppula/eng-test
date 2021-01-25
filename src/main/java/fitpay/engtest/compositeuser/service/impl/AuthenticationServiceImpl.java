package fitpay.engtest.compositeuser.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import fitpay.engtest.compositeuser.entity.AccessToken;
import fitpay.engtest.compositeuser.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(CompositeUserServiceImpl.class);

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

        logger.info("Calling Oauth Token API. [Oauth Token Url: " + oAuthTokenUrl + "]");

        AccessToken accessToken = restTemplateBuilder.basicAuthentication(clientId, clientSecret).build()
                .getForObject(oAuthTokenUrl, AccessToken.class);

        logger.info("Returning Access Token...");

        return accessToken.getAccessToken();
    }

}
