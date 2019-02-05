#### ДЗ 03. MyArrayList

Задачи:
- Написать свою реализацию ArrayList на основе массива.
- class MyArrayList<T> implements List<T>{...}
- Проверить, что на ней работают методы из java.util.Collections
  - addAll(Collection<? super T> c, T... elements)
  - static <T> void	copy(List<? super T> dest, List<? extends T> src)
  - static <T> void	sort(List<T> list, Comparator<? super T> c)

Как запустить:
```
"C:\Program Files\Java\jdk-11.0.1\bin\java.exe" -cp target/benchmarks.jar rem.hw03.MyArrayListBechmark
```

Комментарии:
<pre>
Benchmark                                 Mode  Cnt     Score     Error  Units
MyArrayListBechmark.testAddAtList         avgt    5   309.971 ? 385.054  us/op
MyArrayListBechmark.testAddAtMyList       avgt    5  2499.746 ? 225.536  us/op
MyArrayListBechmark.testContainsAtList    avgt    5   410.394 ?  15.169  us/op
MyArrayListBechmark.testContainsAtMyList  avgt    5   468.569 ? 160.155  us/op
MyArrayListBechmark.testGetAtList         avgt    5     0.002 ?   0.001  us/op
MyArrayListBechmark.testGetAtMyList       avgt    5     0.002 ?   0.001  us/op
MyArrayListBechmark.testRemoveAtList      avgt    5   474.453 ?  22.714  us/op
MyArrayListBechmark.testRemoveAtMyList    avgt    5   418.959 ?  35.679  us/op
</pre>

Материалы:
https://www.baeldung.com/java-collections-complexity
https://github.com/eugenp/tutorials/blob/master/core-java-collections/src/main/java/com/baeldung/performance/


