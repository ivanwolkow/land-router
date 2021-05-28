# land-router
Spring Boot application that is able to calculate any possible land route from one country to another

## Build and run
```bash
./mvnw spring-boot:run
```

## Example of usage
Application is available on port 8080 by default:
``` 
http://localhost:8080/routing/PRT/DNK

{
  "route": [
    "PRT",
    "ESP",
    "FRA",
    "DEU",
    "DNK"
  ]
}
```
