FROM eclipse-temurin:22-jdk-alpine
RUN mkdir /app
WORKDIR /app
COPY build/libs/construction-expenses-0.0.1.jar /app/

ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Dhibernate.types.print.banner=false"
ENV TZ="Europe/Tallinn"
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar /app/construction-expenses-0.0.1.jar
ARG tag
ENV manifest_hack_tag=$tag