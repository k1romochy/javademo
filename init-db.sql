-- Убедимся, что база данных существует
CREATE DATABASE IF NOT EXISTS javademo;

-- Подключаемся к базе данных
\c javademo;

-- Установка владельца базы данных
ALTER DATABASE javademo OWNER TO postgres; 