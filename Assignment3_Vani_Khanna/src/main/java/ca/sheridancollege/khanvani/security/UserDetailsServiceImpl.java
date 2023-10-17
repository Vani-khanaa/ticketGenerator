package ca.sheridancollege.khanvani.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.khanvani.repositories.SecurityRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private SecurityRepository secRepo;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
       ca.sheridancollege.khanvani.beans.User user = secRepo.getUsersByUserName(username);
		
		if(user == null)
		{
			System.out.println("Cannot find user");
			throw new UsernameNotFoundException("Could not find user");
		}
		
		List<String> roles = secRepo.getRolesByUserId(user.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		 for(String role : roles)
		 {
			 grantList.add(new SimpleGrantedAuthority(role));
		 }
		 
		 User springUser = new User(user.getUserName(), user.getEncryptedPassword(), grantList);
		 
		 return (UserDetails)springUser;
		
	}

}
