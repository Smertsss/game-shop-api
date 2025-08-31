package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.entity.Company;
import com.boldenko.game_shop_api.entity.Game;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final DataMapper mapper;
    private final CompanyRepo companyRepo;

    @Override
    @Transactional
    public UUID createCompany(CompanyDto companyDto) {
        Company company = companyRepo.save(mapper.toCompany(companyDto));
        log.info("Add Company: " + company.getName());
        return company.getId();
    }

    @Override
    @Transactional
    public UUID createCompany(Company company) {
        UUID id = companyRepo.save(company).getId();
        log.info("Add Company: " + company.getName());
        return id;
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
    @Transactional(readOnly = true)
    public List<CompanyDto> getAllCompany() {
        List<Company> companies = companyRepo.findAll();
        log.info("Found {} companies in database", companies.size());

        return companies.stream()
                .map(company -> {
                        CompanyDto dto = new CompanyDto();
                        dto.setId(company.getId());
                        dto.setName(company.getName());
                        dto.setContext(company.getContext());
                        dto.setCreationDate(company.getCreationDate());

                        log.info("Created DTO for company: ID={}, Name={}", company.getId(), company.getName());
                        return dto;
                    })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllCompanyData() {
        List<Company> companies = companyRepo.findAll();
        log.info("Found {} companies in database for data table", companies.size());

        return companies.stream()
                .map(company -> {
                    Map<String, Object> companyData = new LinkedHashMap<>();

                    companyData.put("id", company.getId().toString());
                    companyData.put("name", company.getName());
                    companyData.put("context", company.getContext());
                    companyData.put("creationDate", company.getCreationDate());

                    companyData.put("users", company.getUsers() != null ? company.getUsers().size() : 0);
                    companyData.put("games", company.getGames() != null ? company.getGames().size() : 0);
                    companyData.put("images", company.getImages() != null ? company.getImages().size() : 0);

                    log.info("Created data for company: {}, users: {}, games: {}, images: {}",
                            company.getName(),
                            company.getUsers() != null ? company.getUsers().size() : 0,
                            company.getGames() != null ? company.getGames().size() : 0,
                            company.getImages() != null ? company.getImages().size() : 0);
                    return companyData;
                })
                .collect(Collectors.toList());
    }
}
