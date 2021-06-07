# land-router
Spring Boot application that is able to calculate any possible land route from one country to another

## Build and run
Build fat jar and run it:
```bash
./mvnw package
java -jar target/land-router.jar
```

Or build docker image and start container:
```bash
./mvnw package
docker build -t land-router .
docker run -p 8080:8080 --rm land-router
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
