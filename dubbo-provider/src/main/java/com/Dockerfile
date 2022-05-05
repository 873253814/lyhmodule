FROM java:8
EXPOSE 8087

VOLUME \tmp
ADD dubbo-provider-0.0.1-SNAPSHOT.jar dubboprovider.jar
RUN bash -c "touch /dubboprovider.jar"
ENTRYPOINT ["java", "-jar", "/dubboprovider.jar"]