FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 9100
ADD ./target/springboot-service-oauth-0.0.1-SNAPSHOT.jar services-oauth.jar
ENTRYPOINT ["java","-jar","/services-oauth.jar"]