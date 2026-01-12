# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY settings.xml /root/.m2/settings.xml
COPY pom.xml .

RUN echo "USERNAME=$GITHUB_USERNAME" && echo "TOKEN=${#GITHUB_TOKEN}"

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
