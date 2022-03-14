#-------- build stage ------

FROM maven:3.8.3-adoptopenjdk-11@sha256:22a19189017f08cb45f420bb93e820e5bde687b5b37f47be3d3924695276816e AS build

WORKDIR /app

COPY src ./src

COPY pom.xml .

RUN mvn clean package -DskipTests


#------- final stage ------

FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.14.1_1@sha256:103aab7146d5a86a271af150b87572d1fd909fee410103902e2cdb9b6f78d95a

WORKDIR /usr/app

COPY --from=build /app/target/*.jar .

ENTRYPOINT ["java", "-jar", "customer-service.jar"]
