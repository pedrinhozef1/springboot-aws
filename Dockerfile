FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/projeto-springboot.jar
WORKDIR /app
ENTRYPOINT java -jar projeto-springboot.jar