package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.ClientDto;
import com.boldenko.game_shop_api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(ClientController.PATH)
public class ClientController {
    public static final String PATH = "/api/clients";
    
    @Autowired
    private ClientService clientService;

    @GetMapping
    ClientDto getClient(@RequestParam UUID id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    UUID createClient(@RequestBody ClientDto clientDto) {
        return clientService.createClient(clientDto);
    }
}
