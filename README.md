# Payout-calculator

Payout-calculator - сервис для расчета суммы отпускных. Принимает среднюю зарплату за 12 месяцев и количество дней отпуска. Отвечает суммой отпускных. 

### _Выполнено:_
* REST API сервис, без UI.
* Основные функции приложения:
  - получает запрос в формате json (среднняя зарплата, количество дней отпуска, дополнительно - дата ухода в отпуск);
  - отвечает суммой отпускных выплат;
  - если указана дата начала отпуска, то расчет выдется с учетом праздничных дней. 
* Написаны Unit-тесты и MOCK-тесты.
* Для учета праздничных дней формируется запрос к стороннему API производственного календаря.