package com.cg.bankserver.authenticationservice.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.bankserver.authenticationservice.entities.TokensEntity;

@Repository
public interface TokensRedisRepository extends CrudRepository<TokensEntity, String> {
}