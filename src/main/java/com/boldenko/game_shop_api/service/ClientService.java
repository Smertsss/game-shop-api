package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.ClientDto;
import com.boldenko.game_shop_api.entity.Client;

import java.util.UUID;

public interface ClientService {
    UUID createClient(ClientDto clientDto);
    UUID createClient(Client client);
    ClientDto getClientById(UUID id);
}
