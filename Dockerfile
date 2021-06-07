FROM adoptopenjdk/openjdk11
EXPOSE 8080
ADD target/land-router.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
