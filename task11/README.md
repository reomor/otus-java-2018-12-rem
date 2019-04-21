#### ДЗ 10. Hibernate ORM

Задачи. На основе ДЗ 10:
- Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)
- Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
- Добавить в UsersDataSet поля:
 * адресс (OneToOne) 
 * class AddressDataSet{ private String street; }
 * телефон (OneToMany) class PhoneDataSet{ private String number; }
- Добавить соответствущие датасеты и DAO
