FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./build/libs/dev-0.0.1-SNAPSHOT.jar /app/learnmate.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=dev", "-jar", "/app/learnmate.jar"]