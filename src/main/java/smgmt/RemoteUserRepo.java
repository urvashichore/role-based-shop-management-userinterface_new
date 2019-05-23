package smgmt;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RemoteUserRepo implements UserRepo{
	@Autowired
	protected RestTemplate restTemplate;
	
	protected String serviceUrl;
	
	public RemoteUserRepo(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	@Override
	public List<UserProfile> getAllProfiles() {
		UserProfile[] profiles = restTemplate.getForObject(serviceUrl+"/profiles", UserProfile[].class);
		return Arrays.asList(profiles);
	}

}
