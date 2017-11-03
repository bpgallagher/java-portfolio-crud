package com.sugata.javacrud.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_id")
    private long id;

    @Column(name= "ticker", nullable = false)
    private String ticker;

    private double marketValue;

    @Column(name = "portfolio_id")
    private long portfolioId;

    public String getPortfolioTickerId() {
        return portfolioTickerId;
    }

    public void setPortfolioTickerId(String portfolioTickerId) {
        this.portfolioTickerId = portfolioTickerId;
    }

    @Column(name = "portfolio_id_ticker_id", unique = true)
    private String portfolioTickerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }
}
