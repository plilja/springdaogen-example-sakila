FROM openjdk:11-jre-slim
VOLUME /tmp
EXPOSE 80
ARG JAR_FILE=target/sakila-1.0.0.jar
ADD ${JAR_FILE} sakila-1.0.0.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5001", "-jar", "-Dserver.port=80", "/sakila-1.0.0.jar"]
