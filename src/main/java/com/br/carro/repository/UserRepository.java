package com.br.carro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.carro.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByLogin(String login);
}
