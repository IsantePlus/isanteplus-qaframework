ARG ISANTEPLUS_USER=admin
ARG ISANTEPLUS_PW=Admin123
ARG ISANTEPLUS_URL=http://isanteplus:8080/openmrs

FROM adoptopenjdk/maven-openjdk8 as build

RUN apt-get update -qqy \
  && apt-get -qqy install \
    xvfb \
    pulseaudio \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#============================
# Some configuration options
#============================
ENV SCREEN_WIDTH 1360
ENV SCREEN_HEIGHT 1020
ENV SCREEN_DEPTH 24
ENV SCREEN_DPI 96
ENV DISPLAY :99.0
ENV DISPLAY_NUM 99
ENV START_XVFB true
ENV START_NO_VNC true
# Path to the Configfile
ENV CONFIG_FILE=/opt/selenium/config.toml
ENV GENERATE_CONFIG true

#========================
# Selenium Configuration
#========================
# As integer, maps to "max-concurrent-sessions"
ENV SE_NODE_MAX_SESSIONS 1
# As integer, maps to "session-timeout" in seconds
ENV SE_NODE_SESSION_TIMEOUT 300
# As boolean, maps to "override-max-sessions"
ENV SE_NODE_OVERRIDE_MAX_SESSIONS false

# Following line fixes https://github.com/SeleniumHQ/docker-selenium/issues/87
ENV DBUS_SESSION_BUS_ADDRESS=/dev/null

# Creating base directory for Xvfb
RUN mkdir -p /tmp/.X11-unix && chmod 1777 /tmp/.X11-unix

#==============================
# Locale and encoding settings
#==============================
ENV LANG_WHICH en
ENV LANG_WHERE US
ENV ENCODING UTF-8
ENV LANGUAGE ${LANG_WHICH}_${LANG_WHERE}.${ENCODING}
ENV LANG ${LANGUAGE}
# Layer size: small: ~9 MB
# Layer size: small: ~9 MB MB (with --no-install-recommends)
RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    language-pack-en \
    tzdata \
    locales \
  && locale-gen ${LANGUAGE} \
  && dpkg-reconfigure --frontend noninteractive locales \
  && apt-get -qyy autoremove \
  && rm -rf /var/lib/apt/lists/* \
  && apt-get -qyy clean

#=====
# VNC
#=====
RUN apt-get update -qqy \
  && apt-get -qqy install \
  x11vnc \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#=========
# fluxbox
# A fast, lightweight and responsive window manager
#=========
RUN apt-get update -qqy \
  && apt-get -qqy install \
    fluxbox \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

#================
# Font libraries
#================
# libfontconfig            ~1 MB
# libfreetype6             ~1 MB
# xfonts-cyrillic          ~2 MB
# xfonts-scalable          ~2 MB
# fonts-liberation         ~3 MB
# fonts-ipafont-gothic     ~13 MB
# fonts-wqy-zenhei         ~17 MB
# fonts-tlwg-loma-otf      ~300 KB
# ttf-ubuntu-font-family   ~5 MB
#   Ubuntu Font Family, sans-serif typeface hinted for clarity
# Removed packages:
# xfonts-100dpi            ~6 MB
# xfonts-75dpi             ~6 MB
# Regarding fonts-liberation see:
#  https://github.com/SeleniumHQ/docker-selenium/issues/383#issuecomment-278367069
# Layer size: small: 36.28 MB (with --no-install-recommends)
# Layer size: small: 36.28 MB
RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    libfontconfig \
    libfreetype6 \
    xfonts-cyrillic \
    xfonts-scalable \
    fonts-liberation \
    fonts-ipafont-gothic \
    fonts-wqy-zenhei \
    fonts-tlwg-loma-otf \
    ttf-ubuntu-font-family \
    wget \
    unzip \
  && rm -rf /var/lib/apt/lists/* \
  && apt-get -qyy clean

RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    libnss3

WORKDIR /

RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository

VOLUME /root/.m2/repository

WORKDIR /lib

ADD pom.xml /lib/

RUN ["mvn", "verify", "clean", "--fail-never"]

ADD ./src /lib/src/

RUN mvn install -DskipTests

RUN rm /lib/src/test/resources/test.properties

ENV ISANTEPLUS_URL=${ISANTEPLUS_URL}
ENV ISANTEPLUS_USER=${ISANTEPLUS_USER}
ENV ISANTEPLUS_PW=${ISANTEPLUS_PW}

ADD ./entrypoint.sh /lib/

ENTRYPOINT /lib/entrypoint.sh

