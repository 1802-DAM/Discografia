# Stage 1

FROM gradle:jdk21 as builder 

WORKDIR /app

COPY ./build.gradle .
COPY ./settings.gradle .

COPY src ./src

RUN gradle bootJar --no-daemon -x test -Dorg.gradle.jvmargs="-Xmx256m -Xms256m"


# Stage 2

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar Discografia-1.jar

EXPOSE 443 

CMD ["java", "-jar", "Discografia-1.jar"]