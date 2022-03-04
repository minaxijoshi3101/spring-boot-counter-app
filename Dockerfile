FROM openjdk:11.0.2-jre-slim
COPY target/counter-app-0.0.1-SNAPSHOT.jar .
CMD java -jar counter-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
