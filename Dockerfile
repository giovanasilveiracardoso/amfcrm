#FROM eclipse-temurin:17-jre
#COPY target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/app.jar"]
####
#FROM maven:3.8.5-openjdk-17

#WORKDIR /amfcrm
#COPY . .
#RUN mvn clean install

#CMD mvn spring-boot:run

FROM maven:3.8.5-openjdk-17 as builder
COPY . /usr/src/mymaven
WORKDIR /usr/src/mymaven
RUN mvn clean install -f /usr/src/mymaven

FROM java:17

COPY --from=builder /usr/src/mymaven/target/amfcrm.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]