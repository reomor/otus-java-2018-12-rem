#### ДЗ 10. myORM

Задачи:
- Создать в базе данных таблицу с полями: 
  * id bigint(20) NOT NULL auto_increment 
  * name varchar(255)
  * age int(3)
- Создать абстрактный класс DataSet. Поместить long id в DataSet
- Добавить класс UserDataSet (с полями, которые соответствуют таблице), унаследовав его от DataSet
- Разработать Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу
- <T extends DataSet> void save(T user){…}
- <T extends DataSet> T load(long id, Class<T> clazz){…}
- Проверить его работу на UserDataSet
