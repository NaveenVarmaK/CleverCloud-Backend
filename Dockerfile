FROM openjdk:17-jdk-slim as build

WORKDIR /home/app

RUN apt-get update
RUN apt-get install -y git
RUN apt-get install -y maven

COPY ./src/ ./src/
COPY ./pom.xml .

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM openjdk:17-jdk-slim as runtime

RUN apt-get update
RUN apt-get install -y wget
RUN apt-get install -y lsof

WORKDIR /home/app

COPY --from=build /home/app/target/parking-0.0.1-SNAPSHOT.jar /home/app/app.jar
COPY ./src/main/resources/application.properties /home/app/application.properties

CMD ["java", "-Xmx11g","-Dspring.config.location=file:///home/app/application.properties","-jar", "/home/app/app.jar"]