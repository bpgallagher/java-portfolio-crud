Clone repo

Run using `mvn spring-boot:run`

Access the API at `localhost:8080/api/v1/portfolios`

### Portfolio(s) 
 - `/GET localhost:8080/api/v1/portfolios` lists all portfolios on the system by name
 - `/PUT localhost:8080/api/v1/portfolios/{portfolioName}` creates a new portfolio
 - `/GET localhost:8080/api/v1/portfolios/{portfolioName}` list the stocks owned in a single portfolio
 - `/DELETE localhost:8080/api/v1/portfolios/{portfolioName}` delete a portfolio
 
### Positions(s)
 - `/PUT localhost:8080/api/v1/portfolios/{portfolioName}/{ticker}?marketValue={marketValue}` creates a new position
 - `/POST localhost:8080/api/v1/portfolios/{portfolioName}/{ticker}?marketValue={marketValue}` updates an existing
 - `/DELETE localhost:8080/api/v1/portfolios/{portfolioName}/{ticker}` removes a position

#### Bonus
 - `/GET localhost:8080/api/v1/portfolios/net-asset-value` aggregates the net value of the portfolio
