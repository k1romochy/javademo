#!/bin/sh

# Ожидание доступности PostgreSQL
echo "Ожидание доступности PostgreSQL..."
until nc -z postgres 5432; do
  echo "PostgreSQL недоступен - ожидание..."
  sleep 2
done
echo "PostgreSQL доступен!"

# Запуск приложения с миграциями
echo "Запуск приложения с применением миграций Liquibase..."
java ${JAVA_OPTS} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Dspring.liquibase.enabled=true \
  -Djava.security.egd=file:/dev/./urandom \
  -jar /app/app.jar 