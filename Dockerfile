# maven:3.8.6
FROM maven@sha256:3cd540cea2ec98978069ba5b192476111eff75c221c676a654d80d4fafce8822 as user-builder
ENV APPDIR /usr/local/app
RUN mkdir $APPDIR
LABEL "author"="rados" \
    "language"="java" \
    "buildtool"="maven"
WORKDIR $APPDIR
COPY . $APPDIR
RUN mvn clean package

# eclipse-temurin:18.0.2.1_1-jdk
FROM eclipse-temurin@sha256:b58430116602b6ae7d6afa347138a7d74fad6629a0156d58e434e9881920f3ad
LABEL "author"="rados" \
    "language"="java"
ENV APPDIR /usr/local/app
RUN mkdir $APPDIR
WORKDIR $APPDIR
COPY --from=user-builder $APPDIR/target/user-service.jar $APPDIR
EXPOSE 8081
CMD ["java", "-jar", "user-service.jar"]
