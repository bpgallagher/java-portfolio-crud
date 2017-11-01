package com.sugata.javacrud.repositories;

import com.sugata.javacrud.models.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByName(String name);
}
