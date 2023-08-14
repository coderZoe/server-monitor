FROM openjdk:8
LABEL maintainer="coder_yin@qq.com"

WORKDIR /app
COPY target/server-monitor-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
EXPOSE 8081
