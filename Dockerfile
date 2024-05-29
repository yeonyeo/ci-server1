# baseimage
FROM openjdk:17-slim
#복사 내꺼 이미지
COPY ./build/libs/*T.jar app.jar
RUN ["java", "-version"]
ENV profiles prod
#ARG profiles prod

CMD ["java","-jar","-Dspring.profiles.active=${profiles}", "app.jar"]
EXPOSE 8080