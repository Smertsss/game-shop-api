package com.boldenko.game_shop_api.mapper;

import com.boldenko.game_shop_api.dto.*;
import com.boldenko.game_shop_api.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DataMapper {
    DataMapper INSTANCE = Mappers.getMapper(DataMapper.class);

    CompanyDto toCompanyDto(Company company);
    Company toCompany(CompanyDto companyDto);

    GameDto toGameDto(Game game);
    Game toGame(GameDto gameDto);

    GenreDto toGenreDto(Genre genre);
    Genre toGenre(GenreDto genreDto);

    RoleDto toRoleDto(Role role);
    Role toRole(RoleDto roleDto);

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
