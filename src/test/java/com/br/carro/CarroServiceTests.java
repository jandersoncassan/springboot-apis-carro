package com.br.carro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.carro.dto.CarroDTO;
import com.br.carro.exception.ObjectNotFoundException;
import com.br.carro.model.Carro;
import com.br.carro.service.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarroServiceTests {

	@Autowired
	private CarroService carroService;
	
	@Test
	public void testCreate() {
		Carro carro = new Carro();
		carro.setNome("Nome do Carro >> FERRARI TESTE");
		carro.setTipo("Tipo do Carro >> ESPORTIVO  TESTE");
		
		Optional<CarroDTO> carroDTO = carroService.createCarro(carro);
		assertNotNull(carroDTO.get());
		
		Long id = carroDTO.get().getId();
		assertNotNull(id);
		
		CarroDTO carroById = carroService.getCarroById(id);
		assertNotNull(carroById);
		
		assertEquals("Nome do Carro >> FERRARI TESTE", carroDTO.get().getNome());
		assertEquals("Tipo do Carro >> ESPORTIVO  TESTE", carroDTO.get().getTipo());
		
		//DELETAR NOSSO OBJETO DE TESTE
		carroService.deleteCarro(id);
		
		try{
			assertNull(carroService.getCarroById(id));
			fail("O carro não foi excluido precisamos verificar");
		}catch(ObjectNotFoundException ex) {
			//conduzimos assim qdo o correto é lançpar exceção
		}
		
	}

	@Test
	public void testGetCarros() {		
		List<CarroDTO> carros = carroService.getCarros();
		assertEquals(30, carros.size());
	}

	@Test
	public void testGetCarroById() {		
		CarroDTO carro = carroService.getCarroById(1l);
		assertNotNull(carro);
		assertEquals("Tucker 1948", carro.getNome());
	}


	@Test
	public void testGetCarroByTipo() {		
		List<CarroDTO> carro = carroService.getCarroByTipo("esportivos");
		assertEquals(false, carro.isEmpty());
	}
	
}
