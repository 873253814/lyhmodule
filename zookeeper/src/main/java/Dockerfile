FROM java:8
EXPOSE 8080

VOLUME \tmp
ADD zookeeper-0.0.1-SNAPSHOT.jar zookeeper.jar
RUN bash -c "touch /zookeeper.jar"
ENTRYPOINT ["java", "-jar", "/zookeeper.jar"]