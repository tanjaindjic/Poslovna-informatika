package com.poslovna.poslovna.securityBeans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.poslovna.poslovna.model.Korisnik;
import com.poslovna.poslovna.model.enums.TipKorisnika;

public class CustomUserDetailsFactory {

	private CustomUserDetailsFactory() {}
	
	public static CustomUserDetails createCustomUserDetails(Korisnik korisnik) {

		return new CustomUserDetails(
				korisnik.getKorisnickoIme(),
				korisnik.getId(),
				mapToGrantedAuthorities(korisnik.getTip())
				);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(TipKorisnika role) {
        
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		
		return authorities;
    }
	
}
