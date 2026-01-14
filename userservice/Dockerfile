# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# declare build arguments
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

# expose them as env vars for Maven
ENV GITHUB_USERNAME=${GITHUB_USERNAME}
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

COPY settings.xml /root/.m2/settings.xml
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -s /root/.m2/settings.xml

COPY src ./src
RUN mvn clean package -DskipTests -s /root/.m2/settings.xml

# ---------- RUN STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
