FROM openjdk:17-alpine

# 기본 패키지 업데이트 및 설치
RUN apk update && apk upgrade && \
    apk add --no-cache \
    bash \
    curl \
    chromium \
    chromium-chromedriver \
    harfbuzz \
    nss \
    freetype \
    ttf-freefont \
    font-noto \
    udev \
    ttf-dejavu \
    ttf-droid \
    ttf-liberation && \
    rm -rf /var/cache/apk/*

# ARG로 Jar 파일을 받음
ARG JAR_FILE=build/libs/*.jar

# Jar 파일을 이미지에 복사
COPY ${JAR_FILE} app.jar

# 환경 변수 설정
ENV CHROMEDRIVER_PATH=/usr/bin/chromedriver
ENV CHROME_BIN=/usr/bin/chromium-browser

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
