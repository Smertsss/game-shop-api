package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.service.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CompanyController.PATH_NAME)
@RequiredArgsConstructor
public class CompanyController {
    public static final String PATH_NAME = "/api/companies";
    private final CompanyServiceImpl companyService;

    @PostMapping
    public UUID createCompany(@RequestBody CompanyDto companyDto) {
        return companyService.createCompany(companyDto);
    }

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable UUID id) {
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable UUID id) {
        companyService.deleteCompanyById(id);
    }

    @GetMapping
    public List<CompanyDto> getAllCompany() {
        return companyService.getAllCompany();
    }
}
