#!/bin/bash

echo "Остановка всех сервисов..."
docker-compose down

echo "Статус контейнеров после остановки:"
docker-compose ps 