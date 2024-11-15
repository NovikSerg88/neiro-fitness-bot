
# NeiroFitnessBot

**NeiroFitnessBot** — это Telegram-бот, который позволяет пользователям:
- Просмотреть демо-видео тренировок.
- Оформить подписку на доступ к платному каналу.

## Основные функции
- Просмотр демо-тренировки.
- Оформление подписки.
- Прием платежей через API Telegram и ЮKassa.

---

## Технологический стек
- **Язык**: Java
- **Фреймворк**: Spring Boot
- **База данных**: PostgreSQL
- **ORM**: JPA
- **Сборщик проекта**: Maven
- **Контейнеризация**: Docker (Docker Compose)
- **Развертывание**: Облачный VDS сервер

---

## Установка и запуск

### Требования
- **Docker** и **Docker Compose** установлены на сервере.
- Файл конфигурации `.env` со всеми необходимыми переменными среды.

### Шаги развертывания
1. Клонируйте репозиторий:
   ```bash
   git clone <ссылка на репозиторий>
   cd NeiroFitnessBot
   ```

2. Убедитесь, что файл `.env` содержит необходимые переменные среды:
   ```
   BOT_TOKEN=<ваш токен бота>
   DB_HOST=<адрес базы данных>
   DB_PORT=<порт базы данных>
   DB_NAME=<имя базы данных>
   DB_USERNAME=<имя пользователя базы данных>
   DB_PASSWORD=<пароль пользователя базы данных>
   ```

3. Соберите и запустите приложение через Docker Compose:
   ```bash
   docker compose up -d
   ```

4. Бот будет доступен в Telegram.

---
