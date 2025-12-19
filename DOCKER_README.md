# Akiora Matcher - Docker Compose Setup

Полноценная конфигурация Docker Compose для запуска всех сервисов проекта Akiora Matcher.

## Архитектура

Проект состоит из следующих компонентов:

### Backend Services
- **Chat Service** (C#/.NET) - порт `5000` - Сервис обработки чатов
- **Parser Service** (C#/.NET) - порт `5001` - Сервис парсинга данных
- **User Service** (Java/Spring Boot) - порт `5002` - Управление пользователями
- **Notification Service** (Java/Spring Boot) - порт `5003` - Уведомления (Email, Telegram)
- **Search Form Service** (Java/Spring Boot) - порт `5004` - Обработка форм поиска

### Frontend
- **Frontend** (React/Vite) - порт `3000` - Веб-интерфейс

### Infrastructure
- **MongoDB** - порт `27017` - База данных
- **Mongo Express** - порт `28081` - Web UI для MongoDB

## Быстрый старт

### 1. Подготовка переменных окружения

Создайте файл `.env` на основе `.env.example`:

```bash
cp .env.example .env
```

**ВАЖНО:** Отредактируйте `.env` и замените токены и пароли на ваши собственные!

### 2. Запуск всех сервисов

```bash
docker-compose up -d
```

### 3. Запуск конкретных сервисов

Только база данных и backend:
```bash
docker-compose up -d mongodb chat-service user-service notification-service search-form-service parser-service
```

Только frontend:
```bash
docker-compose up -d frontend
```

### 4. Просмотр логов

Все сервисы:
```bash
docker-compose logs -f
```

Конкретный сервис:
```bash
docker-compose logs -f chat-service
```

### 5. Остановка сервисов

```bash
docker-compose down
```

С удалением volumes (данные БД):
```bash
docker-compose down -v
```

## Endpoints

После запуска сервисы доступны по следующим адресам:

- **Frontend**: http://localhost:3000
- **Chat Service**: http://localhost:5000
- **Parser Service**: http://localhost:5001
- **User Service**: http://localhost:5002
- **Notification Service**: http://localhost:5003
  - Swagger UI: http://localhost:5003/swagger-ui.html
- **Search Form Service**: http://localhost:5004
- **Mongo Express**: http://localhost:28081 (admin/admin)

## Сеть и коммуникация

Все сервисы находятся в единой Docker network `backend`, что позволяет им общаться между собой по именам контейнеров:

- MongoDB доступна внутри сети по адресу: `mongodb:27017`
- Сервисы общаются друг с другом по именам: `chat-service`, `user-service`, etc.

## Health Checks

MongoDB настроена с health check, что гарантирует:
- Backend сервисы стартуют только после готовности БД
- Автоматический restart при проблемах

## Разработка

### Rebuild конкретного сервиса

```bash
docker-compose up -d --build chat-service
```

### Rebuild всех сервисов

```bash
docker-compose up -d --build
```

### Проверка статуса

```bash
docker-compose ps
```

## Troubleshooting

### Порты заняты

Если порты заняты, измените их в `docker-compose.yml`. Например:
```yaml
ports:
  - "5050:8080"  # вместо 5000:8080
```

### Проблемы с build

Очистите Docker кэш:
```bash
docker-compose down
docker system prune -a
docker-compose up -d --build
```

### Проблемы с MongoDB connection

Проверьте, что MongoDB запущена и доступна:
```bash
docker-compose logs mongodb
docker exec -it akiora-mongodb mongosh --eval "db.runCommand('ping')"
```

## Production Considerations

Перед деплоем в production:

1. **Secrets Management**: Используйте Docker secrets или внешний secrets manager
2. **Environment Variables**: Не коммитьте `.env` файл с реальными credentials
3. **Volumes**: Настройте backup для MongoDB volume
4. **Scaling**: Рассмотрите использование Docker Swarm или Kubernetes
5. **Monitoring**: Добавьте Prometheus, Grafana для мониторинга
6. **Logging**: Настройте централизованное логирование (ELK stack)
7. **Nginx/Traefik**: Используйте reverse proxy для единой точки входа

## Структура Volumes

- `akiora_db_data` - Данные MongoDB (персистентные)

## Networks

- `backend` - Bridge network для всех сервисов
