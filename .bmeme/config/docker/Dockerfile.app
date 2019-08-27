FROM openjdk:11.0.4-jre-slim-buster

ARG JAR
ARG PROFILE

ENV JAR ${JAR}
ENV PROFILE ${PROFILE}

COPY target/${JAR} /home/project/

CMD java -jar /home/project/${JAR} --spring.profiles.active=${PROFILE}
