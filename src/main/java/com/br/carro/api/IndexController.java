package com.br.carro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
	
	@GetMapping
	public ResponseEntity<String> get() {
		return ResponseEntity.ok("Apis Carros Flutter >> GET");
	}
	
	@GetMapping("userinfo")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails user) {
		return ResponseEntity.ok(user);
	}


}
