package com.poslovna.poslovna.securityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.repository.KorisnikRepository;
import com.poslovna.poslovna.securityBeans.CustomUserDetailsFactory;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikRepository.findByKorisnickoIme(username);
		
		if(korisnik==null) {
			throw new UsernameNotFoundException("Korisnik ne postoji");
		}else {
			return CustomUserDetailsFactory.createCustomUserDetails(korisnik);
		}
			
		
	}

}
