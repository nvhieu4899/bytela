FROM gradle:8-alpine as TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle  . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle build --no-daemon || return 0
COPY . .
RUN gradle clean build

FROM openjdk:17-alpine
ENV ARTIFACT_NAME=bytela-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

RUN mkdir /app
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "$ARTIFACT_NAME"]