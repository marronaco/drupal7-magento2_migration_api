FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
ENV CSV_DIRECTORY /app/csv_files
RUN mkdir -p $CSV_DIRECTORY
WORKDIR /app
COPY ./target/migration-products-snapshot.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]