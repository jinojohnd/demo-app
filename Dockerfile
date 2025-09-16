FROM azul/zulu-openjdk-debian:17.0.16-17.60

WORKDIR /app

# Copy the built JAR (produced by Jenkins or mvn package)
COPY target/*.jar app.jar

# Add non-root user for security
RUN addgroup --system spring && adduser --system --group spring
USER spring:spring

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
