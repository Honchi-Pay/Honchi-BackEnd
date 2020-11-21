FROM openjdk:8-jre-slim
COPY ./build/libs/*.jar honchi.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "-Duser.timezone=Asia/Seoul", "/honchi.jar"]
EXPOSE 3000
