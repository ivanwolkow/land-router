# land-router
Spring Boot application that is able to calculate any possible land route from one country to another.  

CI/CD workflow has been configured for this repo.
Push to the master branch triggers the app to be build and deployed to the Heroku application cloud platform:
[https://land-router.herokuapp.com/healthcheck](https://land-router.herokuapp.com/healthcheck)
A Heroku free dyno is used so accessing the above endpoint may take a while because the dyno may be asleep.

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
