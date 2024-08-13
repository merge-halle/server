FROM amazoncorretto:17-alpine
ENV SPRING_PROFILES_ACTIVE=prod
COPY /project/build/libs/project-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]