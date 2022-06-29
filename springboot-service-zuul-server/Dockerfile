FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8090
ADD ./target/springboot-service-zuul-server-0.0.1-SNAPSHOT.jar zuul-server.jar
ENTRYPOINT ["java","-jar","/zuul-server.jar"]