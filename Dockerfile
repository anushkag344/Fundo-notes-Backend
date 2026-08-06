FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY app.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-Dserver.port=8080", "-Xmx384m", "-jar", "app.jar"]
