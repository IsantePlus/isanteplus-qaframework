FROM adoptopenjdk/maven-openjdk8 as build

WORKDIR /

ARG ISANTEPLUS_PW=Admin123
ARG ISANTEPLUS_URL=http://isanteplus:8080/openmrs

RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository

VOLUME /root/.m2/repository

WORKDIR /lib

ADD pom.xml /lib/

RUN ["mvn", "verify", "clean", "--fail-never"]

ADD . /lib/

RUN mvn install -DskipTests

RUN rm ./src/test/resources/test.properties

RUN sed -e "s/\${ISANTEPLUS_URL}/$ISANTEPLUS_URL/" -e "s/\${$ISANTEPLUS_PW}/$ISANTEPLUS_PW/" ./src/test/resources/test.properties.template | tee ./src/test/resources/test.properties

ENTRYPOINT [ "mvn test" ]