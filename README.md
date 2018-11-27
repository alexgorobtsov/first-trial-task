Цель вашего задания распарсить файл base.dat и исходя из содержимого файла, необходимо реализовать следующие методы интерфейса:

public List getListOfEmployeeOnProject(String project); public List getListOfManagersForEmployee(String empName); public List getListOfNotBusyEmployees(); public List getListOfProjectsForCustomer(String customerName);

Более детальное описание этих метод вы найдете в самом интерфейсе InformationRecipient в приложенном архиве. Класс реализации необходимо назвать InformationRecipientImpl и поместить в тот же package, что и интерфейс InformationRecipient.

Пример структуры файла base.dat представлен ниже:

{ "personName": "Minakov", //Имя служающего "department": "Analyst", //Департамент "projects": [ //Проекты в которых участвует сотрудник. Это список может быть пуст! { "projectName": "4G", //Имя проекта "customer": "NEC Corporation", //Заказчик проекта "manager": "Sidorov" //Руководитель проекта, причем он тоже служающий и может руководить несколькими проектами }, { "projectName": "Windows 300", //второй проект служающего "customer": "Microsoft", "manager": "Bill Geits" } ] }

Просьба сделать это задание всем индивидуально!
