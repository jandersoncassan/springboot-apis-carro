package com.br.carro.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.carro.dto.UserDTO;
import com.br.carro.entity.User;
import com.br.carro.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getUsers() {
		List<UserDTO> users = userService.getUsers();
		return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
	}

	@GetMapping("userinfo")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user) {
		//JwtUtil.getUserDetails(); //OUTRA FORMA DE PEGAR O USUARIO LOGADO
		return ResponseEntity.ok(UserDTO.create(user));
	}

}
