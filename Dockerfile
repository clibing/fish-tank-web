FROM clibing/alpine-openjdk:x86_64-8-212

LABEL maintainer="clibing <wmsjhappy@gozap.com>"

ARG SPRING_PROFILES_ACTIVE

ENV JAVA_OPTS "${JAVA_OPTS} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"

COPY target/*.jar /

CMD ["bash","-c","java ${JAVA_OPTS} -jar /*.jar"]
