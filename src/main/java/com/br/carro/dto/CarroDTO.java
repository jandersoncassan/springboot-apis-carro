package com.br.carro.dto;

import org.modelmapper.ModelMapper;

import com.br.carro.entity.Carro;

import lombok.Data;

@Data
public class CarroDTO {

	private Long id;
	private String nome;
	private String tipo;	
	
	
	public static CarroDTO create(Carro carro) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(carro, CarroDTO.class);
	}

}
