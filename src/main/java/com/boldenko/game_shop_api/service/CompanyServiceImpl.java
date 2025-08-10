package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.entity.Company;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public List<CompanyDto> getAllCompany() {
        List<Company> companies = companyRepo.findAll();
        log.info("Found {} companies in database", companies.size());

        List<CompanyDto> result = new ArrayList<>();

        for (Company company : companies) {
            log.info("Processing company: ID={}, Name={}, CreationDate={}",
                    company.getId(), company.getName(), company.getCreationDate());

            CompanyDto dto = new CompanyDto();
            dto.setId(company.getId());
            dto.setName(company.getName());
            dto.setContext(company.getContext());
            dto.setCreationDate(company.getCreationDate());

            log.info("Created DTO: {}", dto);
            result.add(dto);
        }

        log.info("Returning {} company DTOs", result.size());
        return result;
    }
}
