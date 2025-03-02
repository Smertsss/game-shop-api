package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientRepo extends CrudRepository<Client, UUID> {
}
