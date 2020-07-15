package com.br.carro.model;

import lombok.Data;

@Data
public class UploadOutput {
	private String url;

	public static UploadOutput converter(String url) {
		UploadOutput modelMapper = new UploadOutput();
		modelMapper.setUrl(url);
		return modelMapper;
	}
}
