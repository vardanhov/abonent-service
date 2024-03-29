Задание

Необходимо разработать web-сервис, который по номеру базовой станции будет отдавать список абонентов, которые в данный момент находятся на ней. Помимо номера телефона в ответе должен присутствовать профиль абонента.

1.	База данных (DB). В базе данных хранится связка номера базовой станции(Cell ID) с номером абонента (CTN). Связь один ко многим. И таблица abonentid Содержащая информацию об ID абонента (связка ctn и uuid). Связь один к одному.
2.	Web-сервис (ProfileService) который по номеру абонента отдает его профиль Для этого необходимо использовать сервис https://randomuser.me , который генерирует фейковые профили пользователей. Пример запроса: curl "https://randomuser.me/api/?phone=1111111111&inc=name,email", где “name,email” список необходимых полей, а 1111111111 номер абонента
3.	Web-сервис (DetailService) который по номеру базовой станции отдает список абонентов с их профилями

Что требуется от кандидата:

•	Развернуть БД с таблицей sessions и таблицей abonentid (mysql или postgresql)<br>
•	Создать таблицу sessions и загрузить в нее файл sessions.csv<br>
•	Создать таблицу abonentid и загрузить в нее тестовую информацию (ctn ->abonent_id) для каждого ctn (данные сгенерировать самостоятельно).<br>
•	Создать web-сервис DetailService, который будет:<br>
•	Принимать GET запрос с номером Cell ID<br>
•	Сервис идет в БД и получает по переданному Cell ID список CNT-ов<br>
•	С полученным списком CTN-ов необходимо сходить во внешний сервис ProfileService (в лице https://randomuser.me) и получить по каждому номеру профиль<br>
•	По полученному списку CTN-ов необходимо выполнить запросы к базе к табличке abonentid для получения uuid по ctn.<br>
•	Результат необходимо собрать в массив и вернуть в формате<br>


{<br>

total: 10,<br>
results:<br> [<br>
{<br>
ctn: 1234567890,<br>
abonentId: d79fccde-4cb3-11ea-b77f-2e728ce88125 <br>
name: “Ivanov Ivan”, <br>
email: “i.ivanov@mail.com” <br>
},<br>
{<br>
ctn: 1234567891, <br>
abonentId:f9ce619e-4cb3-11ea-b77f-2e728ce88125 <br>
name: “Petrov Ivan”, <br>
email: “i.petrov@mail.com” <br>
}, <br>
…<br>
] <br>
}

Требования к реализации

•	Язык программирования Java или Scala <br>
•	Наличие HTTP API, состоящего из одного метода <br>
•	Сервис DetailService должен уметь обрабатывать несколько запросов одновременно <br>
•	Запросы к ProfileService (в лице https://randomuser.me) должны осуществляться параллельно <br>
•	Запросы к ABONENTID должны выполняться параллельно <br>
•	Учесть что ответ от ProfileService и ABONENTID могут приходить с задержкой. <br>
•	Необходимо приложить скрипты по созданию схемы в БД <br>
•	Сервис DetailService всегда должен сформировать результат ответа в установленный лимит (по умолчанию 2 сек), в случае если сервис не успевает получить данны о профиле какого -то абонента или об abonentId, то необходимо в ответе для данного абонента оставить неполученные поля пустыми. <br>
•	“упаковать” данный сервис в docker-compose <br>

Результат выполнения задания <br>

•	необходимо предоставить исходные коды реализующие логику проекта <br>
•	sql скрипты <br>
•	скрипты для сборки проекта <br>
•	конфигурации docker-compose <br>
•	инструкцию по сборке проекта и запуску <br>


Технологии 
Java 17 , Spring boot , Postgres

До запуска надо создать базу abonent и схкму abonent_schema.
Во время запуска создаются таблицы abonentid и sessions