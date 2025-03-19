#!/bin/bash

echo "Запуск всех сервисов..."
docker-compose up -d

echo "Проверка статуса контейнеров..."
docker-compose ps

echo "Доступные сервисы:"
echo "- Приложение: http://localhost:8080"
echo "- Kafka UI: http://localhost:8090"
echo "- PostgreSQL: localhost:5432"
echo "- Redis: localhost:6379"
echo "- Kafka: localhost:9092" 