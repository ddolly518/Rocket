# 1단계: 빌드 -> /app/build/libs/xxx.jar 생성
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# 2단계: 실행
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# java -jar app.jar 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]