#!/bin/bash

echo "Проверка и управление миграциями Liquibase"
echo "============================================"

# Проверка статуса контейнеров
echo "Проверка статуса контейнеров:"
docker-compose ps

# Проверка логов приложения для миграций
echo -e "\nПроверка логов миграций:"
docker-compose logs --tail=50 app | grep -i "liquibase\|migration\|migrat"

echo -e "\nДоступные команды для управления миграциями:"
echo "1. Посмотреть полные логи приложения: docker-compose logs app"
echo "2. Перезапустить приложение для повторного применения миграций: docker-compose restart app"
echo "3. Ручной запуск миграций через Maven: mvn liquibase:update"
echo "4. Проверка статуса миграций: mvn liquibase:status" 