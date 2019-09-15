FROM openjdk:8-alpine

COPY target/uberjar/oceans-eleven.jar /oceans-eleven/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/oceans-eleven/app.jar"]
