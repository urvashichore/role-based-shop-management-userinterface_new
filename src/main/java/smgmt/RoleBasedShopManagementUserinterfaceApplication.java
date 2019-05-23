package smgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
//@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class RoleBasedShopManagementUserinterfaceApplication/* extends SpringBootServletInitializer */{

		public static final String PROFILES_SERVICE_URL = "http://ROLE-BASED-SHOP-MANAGEMENT-BACK-END:7777";
		
	/*
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder application) { return
	 * application.sources(RoleBasedShopManagementUserinterfaceApplication.class); }
	 */
	 
	    public static void main(String[] args) throws Exception {
	        SpringApplication.run(RoleBasedShopManagementUserinterfaceApplication.class, args);
	    }
	    @Bean
		@LoadBalanced
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
		@Bean
		public UserRepo profileRepository(){
			return new RemoteUserRepo(PROFILES_SERVICE_URL);
		}
}
