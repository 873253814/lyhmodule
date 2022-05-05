FROM java:8
EXPOSE 8080

VOLUME \tmp
ADD kafka-0.0.1-SNAPSHOT.jar kafka.jar
RUN bash -c "touch /kafka.jar"
ENTRYPOINT ["java", "-jar", "/app.jar"]