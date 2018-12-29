#### ДЗ 01. Сборка и запуск проекта

Задачи:

- Создать проект под управлением maven в Intellij IDEA. 
- Добавить зависимость на Google Guava/Apache Commons/библиотеку на ваш выбор.
- Использовать библиотечные классы для обработки входных данных.
- Задать имя проекта (project_name) в pom.xml
- Собрать project_name.jar содержащий все зависимости.
- Проверить, что приложение можно запустить из командной строки.
- Выложить проект на github. 
- Создать ветку "obfuscation" изменить в ней pom.xml, так чтобы сборка содержала стадию обфускации байткода.

Как запустить:
```
java.exe -cp C:\Git\java\otus-java\otus-java-2018-12-rem\task01\target\task01.jar;C:\Users\User\.m2\repository\org\apache\commons\commons-text\1.1\commons-text-1.1.jar;C:\Users\User\.m2\repository\org\apache\commons\commons-lang3\3.8.1\commons-lang3-3.8.1.jar rem.hw01.Application
java.exe -cp C:\Git\java\otus-java\otus-java-2018-12-rem\task01\target\task01-jar-with-dependencies.jar rem.hw01.Application
```

Комментарии:

Плагин proguard удалось запустить только с jvm 1.8

Материалы:
> [Настройки плагина assembly](https://maven.apache.org/plugins/maven-assembly-plugin/single-mojo.html)