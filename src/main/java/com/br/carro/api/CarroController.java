package com.br.carro.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.carro.dto.CarroDTO;
import com.br.carro.model.Carro;
import com.br.carro.service.CarroService;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	public ResponseEntity<?> getCarros() {		
		List<CarroDTO> carros = carroService.getCarros();		
		 return carros.isEmpty() 
				 ?	ResponseEntity.notFound().build()
				 :  ResponseEntity.ok(carros);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCarroById(@PathVariable("id") Long id) {
		
		 CarroDTO carro = carroService.getCarroById(id);		 
		 return ResponseEntity.ok(carro);
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<?> getCarrosByTipo(@PathVariable("tipo") String tipo) {
		
		List<CarroDTO> carros = carroService.getCarroByTipo(tipo);
		
		return carros.isEmpty() 
				?  ResponseEntity.noContent().build()
				:  ResponseEntity.ok(carros);
	}
	
	
	@PostMapping
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> createCarro(@RequestBody Carro carro) {
		Optional<CarroDTO> cCarro = carroService.createCarro(carro);
		
		return cCarro.map(c -> ResponseEntity.created(getUri(c.getId())).build())
				     .orElse(ResponseEntity.notFound().build());
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCarro(@PathVariable("id") Long id, @RequestBody Carro carro) {
		
		Optional<CarroDTO> cCarro = carroService.updateCarro(id, carro);		
		return cCarro.map(ResponseEntity::ok)
				     .orElse(ResponseEntity.notFound().build());		
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCarro(@PathVariable("id") Long id) {
		
		carroService.deleteCarro(id);		
		return ResponseEntity.ok().build();
	}

   private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
   }

	  
}
