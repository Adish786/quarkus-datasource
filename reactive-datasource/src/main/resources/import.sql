INSERT INTO CUSTOMER(id,name,city) VALUES (1, 'Adish','Delhi');
INSERT INTO CUSTOMER(id,name,city) VALUES (2, 'Murugan','Coimbatore');
INSERT INTO CUSTOMER(id,name,city) VALUES (3, 'Rahul','Pune');
ALTER SEQUENCE customer_seq RESTART WITH 4;


INSERT INTO EMPLOYEE(id,name,city) VALUES (1, 'Adish','Delhi');
INSERT INTO EMPLOYEE(id,name,city) VALUES (2, 'Murugan','Coimbatore');
INSERT INTO EMPLOYEE(id,name,city) VALUES (3, 'Rahul','Pune');
ALTER SEQUENCE  employee_seq RESTART WITH 4;
