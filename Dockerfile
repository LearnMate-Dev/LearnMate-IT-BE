FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./build/libs/app.jar /app/learnmate.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=dev", "-jar", "/app/learnmate.jar"]