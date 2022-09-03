FROM maven:latest as user-builder
ENV APPDIR /usr/local/app
RUN mkdir $APPDIR
LABEL "author"="rados" \
    "language"="java" \
    "buildtool"="maven"
WORKDIR $APPDIR
COPY . $APPDIR
RUN mvn clean package

FROM openjdk:latest
LABEL "author"="rados" \
    "language"="java"
ENV APPDIR /usr/local/app
RUN mkdir $APPDIR
WORKDIR $APPDIR
COPY --from=user-builder $APPDIR/target/user-service.jar $APPDIR
EXPOSE 8081
CMD ["java", "-jar", "user-service.jar"]
