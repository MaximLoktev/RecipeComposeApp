# Recipe Compose App

![Android CI](https://github.com/MaximLoktev/RecipeComposeApp/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/gh/MaximLoktev/RecipeComposeApp/graph/badge.svg)](https://codecov.io/gh/MaximLoktev/RecipeComposeApp)

Приложение для просмотра кулинарных рецептов, разработанное на Jetpack Compose с использованием современных архитектурных подходов.

## 🛠 Стек технологий & Архитектура
* **UI Слой:** Jetpack Compose (Modern Declarative UI), Single Activity architecture.
* **Архитектурный паттерн:** MVVM (Model-View-ViewModel) + Clean Architecture (слои Data, Domain, UI).
* **Асинхронщина & Потоки:** Kotlin Coroutines & StateFlow / SharedFlow (реактивные потоки).
* **Локальная БД & Кэширование:** Room Database (Offline-First подход, стратегия Single Source of Truth).
* **Сетевой слой:** Retrofit 2 + Kotlinx Serialization (асинхронный парсинг JSON).
* **Хранение настроек:** Jetpack DataStore Preferences (для Избранного).
* **Внедрение зависимостей:** Dagger Hilt (инъекция репозиториев, БД и API-сервисов).

## 🧪 Тестирование (Quality Assurance)
В проекте настроена автоматизированная пирамида тестирования:
1. **Unit-тесты:** Тестирование бизнес-логики (Use Cases) и ViewModel-слоя (MockK, Turbine).
2. **Интеграционные тесты:** Проверка Room DAO (In-Memory БД) и интеграции репозитория с кэшем на Android эмуляторе.
3. **End-to-End (E2E) тесты:** Стабильные UI-тесты на базе фреймворка **Kaspresso** с использованием паттерна *Screen Object*.

## 🚀 Непрерывная интеграция (CI)
В репозитории развернут автоматический пайплайн на **GitHub Actions** (`ci.yml`), который при каждом `push` и `pull_request` выполняет:
* Статический анализ кода (`Android Lint`)
* Прогон Unit и интеграционных тестов (`./gradlew test`)
* Генерацию отчетов о покрытии кода (`JaCoCo`) и отправку метрик в `Codecov`
* Автоматическую сборку артефакта (`Debug APK`)