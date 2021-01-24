package fitpay.engtest.compositeuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CompositeUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompositeUserApplication.class, args);
    }
    
	@Bean
	RestTemplate restTemplate() {
	  return new RestTemplate();
	}
}
