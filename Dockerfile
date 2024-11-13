FROM eclipse-temurin:21-jdk AS builder
COPY . /app
WORKDIR /app
RUN chmod +x ./mvnw
RUN ./mvnw clean install -DskipTests
RUN mv -f target/*.jar app.jar

FROM eclipse-temurin:21-jre
ARG PORT
ENV PORT=${PORT}
COPY --from=builder /app/app.jar .
RUN useradd runtime
USER runtime
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=prod", "-jar", "app.jar" ]