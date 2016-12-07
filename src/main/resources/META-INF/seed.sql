INSERT INTO BANK (id, bic, name, housenr, street, zip, city, county, country) VALUES(1, 'BYLADEM123', 'Bank Reichl', '3', 'Lilienweg', '93047', 'Regensburg', 'Bayern', 'Deutschland')

INSERT INTO CUSTOMER(ID, DTYPE, housenr, street, zip, city, county, country) VALUES(1, 'PrivateCustomer', '1', 'Sauerteigstraße', '93047', 'Regensburg', 'Bayern', 'Deutschland')
INSERT INTO PRIVATECUSTOMER (id, firstName, lastName, gender, dateOfBirth) VALUES(1, 'Thomas', 'Feinschneider', 0, '1982-04-22')

INSERT INTO CUSTOMER(ID, DTYPE, housenr, street, zip, city, county, country) VALUES(2, 'CompanyCustomer', '10b', 'Niederbergweg', '93047', 'Regensburg', 'Bayern', 'Deutschland')
INSERT INTO COMPANYCUSTOMER (id, name, dateOfCreation) VALUES(2, 'Hossas GmbH', '2011-05-01')

INSERT INTO EMPLOYEE (id, firstname, lastname, gender, dateOfBirth, housenr, street, zip, city, county, country) VALUES(1, 'Johann', 'Meier', 0, '1988-04-02', '23', 'Galgenbergstraße', '93047', 'Regensburg', 'Bayern', 'Deutschland')
INSERT INTO EMPLOYEE (id, firstname, lastname, gender, dateOfBirth, housenr, street, zip, city, county, country) VALUES(2, 'Maria', 'Dinkel', 1, '1981-12-10', '8b', 'Grabenstraße', '93047', 'Regensburg', 'Bayern', 'Deutschland')

INSERT INTO ACCOUNT(id, iban, owner_id, accountManager_id, tanType, dateOfCreation) VALUES(1, 'DE49830600211111099610', 1, 1, 0, '2009-01-01')
INSERT INTO ACCOUNT(id, iban, owner_id, accountManager_id, tanType, dateOfCreation) VALUES(2, 'DE96303244471000023401', 2, 2, 1, '2013-04-01')

INSERT INTO STANDINGORDER (id, fromAccount_id, toAccount_id, intervalUnit, intervalAmount, amount, description, startDate) VALUES (1, 1, 2, 0, 1, 5, 'Test Standing Order (Hourly)', '2016-12-08 12:00:00')


