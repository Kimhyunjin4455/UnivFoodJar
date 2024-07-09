# 기반 이미지 설정
FROM openjdk:17-jdk

# 빌드된 JAR 파일 복사, 나의 build한 스냅샷 자르 파일을 컨테이너의 app.jar로 복사
COPY build/libs/*SNAPSHOT.jar app.jar
# 템플릿 파일 및 정적 리소스 복사
COPY src/main/resources/static /app/static
COPY src/main/resources/templates /app/templates

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
# 단순 문서화 역할



