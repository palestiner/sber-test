FROM openjdk:17-oracle
MAINTAINER Yuri Medvedev
WORKDIR /app
COPY ./target/sber-test-jar-with-dependencies.jar ./app/sber-test.jar
CMD ["java", "-jar", "./app/sber-test.jar"]
