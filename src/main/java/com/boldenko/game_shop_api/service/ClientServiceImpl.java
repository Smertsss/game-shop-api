package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.ClientDto;
import com.boldenko.game_shop_api.entity.Client;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private DataMapper mapper;

    @Override
    @Transactional
    public UUID createClient(ClientDto clientDto) {
        Client client = mapper.toClient(clientDto);
        clientRepo.save(client);
        return client.getId();
    }

    @Override
    @Transactional
    public UUID createClient(Client client) {
        clientRepo.save(client);
        return client.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientById(UUID id) {
        return mapper.toClientDto(clientRepo.findById(id).orElseThrow());
    }
}
