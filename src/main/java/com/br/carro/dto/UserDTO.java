package com.br.carro.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.br.carro.entity.Role;
import com.br.carro.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserDTO {
    private String login;
    private String nome;
    private String email;    
    // token jwt
    private String token;
    private List<String> roles;

   
    public static UserDTO create(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.roles = user.getRoles().stream().map(Role::getNome).collect(Collectors.toList());
        return dto;
    }
    
    public static UserDTO create(User user, String token) {
        UserDTO dto = create(user);
        dto.token = token;
        dto.roles = null;
        return dto;
    }


    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}