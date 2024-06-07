# 기반 이미지 설정
FROM openjdk:17-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY build/libs/*.jar app.jar

COPY src/main/resources/static /app/static

# 실행 명령어 설정
CMD ["java", "-jar", "app.jar"]