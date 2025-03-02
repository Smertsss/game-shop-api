package com.boldenko.game_shop_api.mapper;

import com.boldenko.game_shop_api.dto.ClientDto;
import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.entity.Client;
import com.boldenko.game_shop_api.entity.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataMapper {
    ClientDto toClientDto(Client Client);
    Client toClient(ClientDto ClientDto);

    GameDto toGameDto(Game Game);
    Game toGame(GameDto GameDto);
}
