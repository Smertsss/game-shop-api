package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID> {
}
