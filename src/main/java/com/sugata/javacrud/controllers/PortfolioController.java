package com.sugata.javacrud.controllers;

import com.sugata.javacrud.exceptions.PortfolioIdMismatchException;
import com.sugata.javacrud.exceptions.PortfolioNotFoundException;
import com.sugata.javacrud.models.Portfolio;
import com.sugata.javacrud.repositories.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @GetMapping
    public Iterable<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    @GetMapping("/{portfolioName}")
    public List<Portfolio> findByName(@PathVariable String portfolioName) {
        return portfolioRepository.findByName(portfolioName);
    }

    @PutMapping("/{portfolioName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Portfolio> create(@PathVariable String portfolioName) {
        Portfolio newPortfolio = new Portfolio();
        if (portfolioRepository.findByName(portfolioName).size() == 0) {
            newPortfolio.setName(portfolioName);
            portfolioRepository.save(newPortfolio);
            return ResponseEntity.ok().body(newPortfolio);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{portfolioName}")
    public ResponseEntity<List<Portfolio>> delete(@PathVariable String portfolioName) {
        List<Portfolio> foundPortfolioList = portfolioRepository.findByName(portfolioName);
        if (foundPortfolioList.size() != 0) {
            portfolioRepository.delete(foundPortfolioList.get(0).getId());
            return ResponseEntity.ok().body(portfolioRepository.findAll());
        }
        return ResponseEntity.notFound().build();
    }
}
