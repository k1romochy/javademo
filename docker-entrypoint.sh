#!/bin/sh

# Ожидание доступности PostgreSQL
echo "Ожидание доступности PostgreSQL..."
until nc -z postgres 5432; do
  echo "PostgreSQL недоступен - ожидание..."
  sleep 2
done
echo "PostgreSQL доступен!"

# Запуск приложения
echo "Запуск приложения..."
java ${JAVA_OPTS} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Djava.security.egd=file:/dev/./urandom \
  -jar /app/app.jar 