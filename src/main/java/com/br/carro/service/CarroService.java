package com.br.carro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.br.carro.dto.CarroDTO;
import com.br.carro.entity.Carro;
import com.br.carro.exception.ObjectNotFoundException;
import com.br.carro.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public List<CarroDTO> getCarros(Pageable pageable){		
		
		//List<Carro> carros = carroRepository.findAll(pageable);		
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
		return carroRepository.findAll(pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO getCarroById(Long id){		
		return carroRepository.findById(id).map(CarroDTO::create)
				.orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado!"));
	}
	
	
	public List<CarroDTO> getCarroByTipo(String tipo, Pageable pageable) {		
		List<Carro> carros = carroRepository.findByTipo(tipo, pageable);
		return carros.stream().map(CarroDTO::create).collect(Collectors.toList());		
	}

	public Optional<CarroDTO> createCarro(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possivel atualizar o registro");
		
		Carro cCarro = carroRepository.save(carro);
		return Optional.of(cCarro).map(CarroDTO::create);		
	}
	
	public Optional<CarroDTO> updateCarro(Long id, Carro carro) {
		Assert.notNull(id, "Não foi possivel atualizar o registro");
		
		//busca o carro no banco de dados
		/*Optional<Carro> optional = carroRepository.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			//copiar as propriedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id >> " + db.getId());
			
			return Optional.of(carroRepository.save(db));
		}
		*/	//OU			
	     return carroRepository.findById(id).map(car -> {
			car.setNome(carro.getNome());
			car.setTipo(carro.getTipo());
			System.out.println("Carro id >> " + car.getId());
			
			return carroRepository.save(car);
			
		}).map(CarroDTO::create);
		
	}

	public void deleteCarro(Long id) {	
		Assert.notNull(id, "Não foi possivel excluir o registro");
		
		/*return getCarroById(id).map(car -> {			
			carroRepository.deleteById(car.getId());
			return Boolean.TRUE;
			
		}).orElse(Boolean.FALSE);*/
		
		carroRepository.deleteById(id);
	}
	
	
	public Optional<List<Carro>> getCarrosFake(){
		
		List<Carro> carros = new ArrayList<>();
		/*carros.add(new Carro(1l,"Fusca"));
		carros.add(new Carro(2l,"Brasilia"));
		carros.add(new Carro(3l,"Corcel"));*/
		
		return Optional.of(carros);		
	}

	


	

	
}
