#### ДЗ 02. Измерение памяти

Задачи:
- Напишите стенд для определения размера объекта. 
- Передавайте для измерения в стенд фабрику объектов.
- Определите размер пустой строки и пустых контейнеров.
- Определите рост размера контейнера от количества элементов в нем.
- С использованием инструментирования, сравнить результаты измерениий.

Как запустить:
```
java.exe -javaagent:..\..\agent\target\agent.jar -jar .\app-1.0-SNAPSHOT.jar
```

Комментарии:
Реализован вариант со статическим подключением агента
