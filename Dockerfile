# ─────────────────────────────────────────────────────────────────────────────
# Dockerfile — taskDois (single-stage)
#
# Em desenvolvimento: o JAR está em build/libs/ (gerado pelo Gradle local)
# Na VM (Homolog/Prod): o JAR é copiado pelo deploy workflow como app.jar
# ─────────────────────────────────────────────────────────────────────────────

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# O deploy workflow copia o JAR renomeado como app.jar na mesma pasta
COPY app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]