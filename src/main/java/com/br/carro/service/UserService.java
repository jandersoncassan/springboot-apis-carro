package com.br.carro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.carro.dto.UserDTO;
import com.br.carro.entity.User;
import com.br.carro.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserDTO> getUsers(){		
		
		List<User> users = userRepository.findAll();		
		/* 1° Jeito
	 		List<CarroDTO> lista = new ArrayList<>();
			for(Carro c : carros) {
				lista.add(new CarroDTO(c));
			}		
			return lista;
		 */		
		
		/* 2° Jeito
		  return carros.stream().map(carro -> new CarroDTO(carro)).collect(Collectors.toList());
		 */
		return users.stream().map(UserDTO::create).collect(Collectors.toList());
	}

	
	
}
