FROM maven:3.6.3-openjdk-8 AS build
COPY nba-stat-endpoints /usr/src/app/nba-stat-endpoints
COPY nba-pojo-builder /usr/src/app/nba-pojo-builder
COPY source-writer /usr/src/app/source-writer
COPY pom.xml /usr/src/app
RUN cd /usr/src/app && mvn -Djdk.net.URLClassPath.disableClassPathURLCheck=true clean package

FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/nba-stat-endpoints/target/nba-stat-endpoints-0.0.1-SNAPSHOT.jar /usr/app/nba-stat-endpoints-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/nba-stat-endpoints-0.0.1-SNAPSHOT.jar"]