package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.entity.Company;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    UUID createCompany(CompanyDto companyDto);
    UUID createCompany(Company company);
    CompanyDto getCompanyById(UUID id);
    void deleteCompanyById(UUID id);
    List<CompanyDto> getAllCompany();
}
