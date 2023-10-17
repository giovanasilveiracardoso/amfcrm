FROM eclipse-temurin:17-jre
COPY target/*.jar amfcrm-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/amfcrm-1.0-SNAPSHOT.jar"]