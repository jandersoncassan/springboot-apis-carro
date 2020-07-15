package com.br.carro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.carro.entity.User;
import com.br.carro.repository.UserRepository;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//FORMA 3, BUSCAMOS TUDO NO BANCO DE DADOS!
		User user = userRep.findByLogin(username);
		if(Optional.ofNullable(user).isPresent()) {
			return user;
		}
		throw new UsernameNotFoundException("user not found");
		
		//FORMA 2, BUSCAMOS O USUARIO NO BANCO E ATRIBUIMOS A ROLE FIXA
/*		com.br.carro.model.User user = userRep.findByLogin(username);
		if(Optional.ofNullable(user).isPresent()) {
			return User.withUsername(username).password(user.getSenha()).roles("USER").build();
		}
		throw new UsernameNotFoundException("user not found");
*/		

		// FORMA 1 , FIXA 
		/*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (username.equals("user")) {
			return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
		}
		if (username.equals("admin")) {
			return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
		}

		throw new UsernameNotFoundException("user not found");*/
	}


}
