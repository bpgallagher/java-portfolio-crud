package com.sugata.javacrud.controllers;

import com.sugata.javacrud.models.Portfolio;
import com.sugata.javacrud.models.Stock;
import com.sugata.javacrud.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ResponseEntity<List<Stock>> findByName(@PathVariable String portfolioName) {
        List<Portfolio> foundPortfolioList = portfolioRepository.findByName(portfolioName);
        if (foundPortfolioList.size() != 0) {
            return ResponseEntity.ok().body(foundPortfolioList.get(0).getPositions());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{portfolioName}")
    public ResponseEntity<Portfolio> create(@PathVariable String portfolioName) {
        Portfolio newPortfolio = new Portfolio();
        if (portfolioRepository.findByName(portfolioName).size() == 0) {
            List<Stock> positions = new ArrayList<Stock>();
            newPortfolio.setName(portfolioName);
            newPortfolio.setPositions(positions);
            portfolioRepository.save(newPortfolio);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{portfolioName}")
    public ResponseEntity<List<Portfolio>> delete(@PathVariable String portfolioName) {
        List<Portfolio> foundPortfolioList = portfolioRepository.findByName(portfolioName);
        if (foundPortfolioList.size() != 0) {
            portfolioRepository.delete(foundPortfolioList.get(0).getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value="/{portfolioName}/{ticker}")
    public ResponseEntity<Stock> createPosition(@PathVariable(value="portfolioName") String portfolioName, @PathVariable(value="ticker") String ticker, @RequestParam(value="marketValue") double marketValue) {
        // find the portfolio using find by name, get id
        List<Portfolio> foundPortfolioList = portfolioRepository.findByName(portfolioName);
        if (foundPortfolioList.size() != 0) {
            Portfolio portfolio = foundPortfolioList.get(0);

            try {
                // create a new stock obj, with ticker and marketvalue
                Stock position = new Stock();
                position.setTicker(ticker);
                position.setMarketValue(marketValue);
                position.setPortfolioId(portfolio.getId());

                position.setPortfolioTickerId(Long.toString(portfolio.getId())+position.getTicker());

                // add stock to portfolio's positions list
                portfolio.addPosition(position);
                portfolioRepository.save(portfolio);
                return ResponseEntity.ok().body(position);
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value="/{portfolioName}/{ticker}")
    public ResponseEntity<Stock> updatePosition(@PathVariable(value="portfolioName") String portfolioName, @PathVariable(value="ticker") String ticker, @RequestParam(value="marketValue") double marketValue) {
        // find the portfolio using find by name, get id
        List<Portfolio> foundPortfolioList = portfolioRepository.findByName(portfolioName);
        if (foundPortfolioList.size() != 0) {
            Portfolio portfolio = foundPortfolioList.get(0);
            boolean hasTicker = false;
            // find position in positions array matching ticker
            // update marketValue
            for (Stock stock : portfolio.getPositions()) {
                if (stock.getTicker().equals(ticker)) {
                    stock.setMarketValue(marketValue);
                    hasTicker = true;
                }
            }

            if (hasTicker) {
                // save the portfolio
                portfolioRepository.save(portfolio);
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{portfolioName}/{ticker}")
    public ResponseEntity<Stock> updatePosition(@PathVariable(value="portfolioName") String portfolioName, @PathVariable(value="ticker") String ticker) {
        List<Portfolio> foundPortfolioList = portfolioRepository.findByName(portfolioName);
        if (foundPortfolioList.size() != 0) {
            Portfolio portfolio = foundPortfolioList.get(0);
            int initSize = portfolio.getPositions().size();
            portfolio.getPositions().removeIf(stock -> stock.getTicker().equals(ticker));

            if (initSize > portfolio.getPositions().size()) {
                // save the portfolio
                portfolioRepository.save(portfolio);
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value="/net-asset-value")
    public ResponseEntity<List<HashMap>> netAssetValue() {
        // result list
        List<HashMap> portfolioAssets = new ArrayList<HashMap>();

        // get all portfolios
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        // for each portfolio
        for (Portfolio portfolio : portfolioList) {
            HashMap<String, Object> portfolioMap = new HashMap<String, Object>();
            double cumulativeTotal = 0;

            for (Stock stock : portfolio.getPositions()) {
                // add the marketValue for each position
                cumulativeTotal += stock.getMarketValue();
            }

            // add the portfolio name & totalMarketValue as hash to list
            portfolioMap.put("portfolioName", portfolio.getName());
            portfolioMap.put("marketValue", cumulativeTotal);
            portfolioAssets.add(portfolioMap);
        }

        // return list
        return ResponseEntity.ok().body(portfolioAssets);
    }
}
