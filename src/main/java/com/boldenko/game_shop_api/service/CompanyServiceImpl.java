package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.entity.Company;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final DataMapper mapper;
    private final CompanyRepo companyRepo;

    @Override
    @Transactional
    public UUID createCompany(CompanyDto companyDto) {
        Company company = mapper.toCompany(companyDto);
        companyRepo.save(company);
        log.info("Add Company: " + company.getName());
        return null;
    }

    @Override
    @Transactional
    public UUID createCompany(Company company) {
        companyRepo.save(company);
        log.info("Add Company: " + company.getName());
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDto getCompanyById(UUID id) {
        return mapper.toCompanyDto(companyRepo.findById(id).orElseThrow());
    }

    @Override
    public void deleteCompanyById(UUID id) {
        String name = mapper.toCompanyDto(companyRepo.findById(id).orElseThrow()).getName();
        companyRepo.deleteById(id);
        log.info("Delete Company: " + name);
    }

    @Override
    public List<CompanyDto> getAllCompany() {
        return List.of((CompanyDto) companyRepo.findAll());
    }
}
