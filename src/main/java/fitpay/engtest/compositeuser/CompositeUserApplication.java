package fitpay.engtest.compositeuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import fitpay.engtest.compositeuser.service.AuthenticationService;

@SpringBootApplication
public class CompositeUserApplication {

    @Autowired
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
        SpringApplication.run(CompositeUserApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        String accessToken = authenticationService.getOauthToken();
        return new RestTemplateBuilder(rtb -> rtb.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        })).build();
    }
}
