package com.br.carro.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.carro.entity.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

	List<Carro> findByTipo(String tipo, Pageable pageable);
	
}
