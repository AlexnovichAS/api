</h2>

[![made-with-java](https://img.shields.io/badge/Java-8-ff0000.svg)](https://www.java.com/)
[![made-with-java](https://img.shields.io/badge/Maven-3.8.1-2626ae.svg)](https://downloads.apache.org)
[![made-with-java](https://img.shields.io/badge/JUnit5-5.7.0-cd4848.svg)](https://junit.org/)
[![made-with-java](https://img.shields.io/badge/Cucumber-6.9.1-cd4848.svg)](https://docs.cucumber.io/)
[![made-with-java](https://img.shields.io/badge/Restassured-4.2.0-green.svg)](https://rest-assured.io/)

# Тестирование API приложений:
# -["rickandmortyapi"](https://rickandmortyapi.com/documentation/#episode-schema) <br>
# -["reqres.in"](https://reqres.in/)
##
***
1. Создайте отдельную директорию на локальном компьютере
2. Установить JVM
3. Необходима java JDK 8
4. Установить apache-maven-3.8.1
5. Скачайте все файлы которые расположены в [директории](https://github.com/AlexnovichAS/api.git) <br>
   git clone https://github.com/AlexnovichAS/api.git
6. Установить Allure
7. Откройте проект
8. Установить все библиотеки из файла pom.xml
9. Запустите все тесты mvn clean test
10. Введите команду mvn allure:install
11. Сформируйте отчет Allure mvn allure:serve
12. В проекте используется maven-surefire-plugin версия 2.20


## Описание проекта
***
Целью написания данного проекта является проверка корректной работы API приложений <br>

В проекте реализовано автоматическое создание отчета в Allure и прикрепление логов запроса и ответа.

## Описание тестов
***
В данный тестовый набор вошли следующие проверки:
### Тест "Поиск персонажей и проверка совпадения данных" в приложении ["rickandmortyapi"](https://rickandmortyapi.com/documentation/#episode-schema)
- отправляет GET запрос с названием персонажа, для получения данных по персонажу
- получает информацию по персонажу в формате json
- отправляет GET запрос с ID последнего эпизода, для получения ID последнего персонажа
- получает информацию в формате json
- отправляет GET запрос с ID последнего персонажа, для получения данных по персонажу
- сравнивает данные по найденным персонажам

### Тест "Создание пользователя и проверка тела ответа" в приложении ["reqres.in"](https://reqres.in/)
- отправляет POST запрос для создания пользователя с телом в формате json 
- получает информацию в формате json
- проверяет код ответа
- проверяет результат ответа json по значениям key и value
