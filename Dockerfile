ARG ISANTEPLUS_USER=admin
ARG ISANTEPLUS_PW=Admin123
ARG ISANTEPLUS_URL=http://isanteplus:8080/openmrs
ARG REMOTE_URL_CHROME=http://chrome:4444/wd/hub
ARG REMOTE_URL_FIREFOX=http://firefox:4444/wd/hub

FROM adoptopenjdk/maven-openjdk8 as build

WORKDIR /

RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository

VOLUME /root/.m2/repository

WORKDIR /lib

ADD pom.xml /lib/

RUN ["mvn", "verify", "clean", "--fail-never"]

ADD ./src /lib/src/

RUN mvn install -DskipTests

RUN rm /lib/target/test-classes/test.properties

ENV ISANTEPLUS_URL=${ISANTEPLUS_URL}
ENV ISANTEPLUS_USER=${ISANTEPLUS_USER}
ENV ISANTEPLUS_PW=${ISANTEPLUS_PW}
ENV REMOTE_URL_CHROME=${REMOTE_URL_CHROME}
ENV REMOTE_URL_FIREFOX=${REMOTE_URL_FIREFOX}

ADD ./entrypoint.sh /lib/

ENTRYPOINT /lib/entrypoint.sh

