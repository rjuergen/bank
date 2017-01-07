use juergen_reichl;
INSERT INTO Account(id, accountNumber, iban, tanType, dateOfCreation, passwordSalt, hashedPassword) VALUES(3, '00000000002', 'DE91772300000000000002', 1, '1990-01-01', '9KAFF9OVR8384B9', 'd065cda77a8dbbca87c19eea54f6a7cffa2d62f798bd6637ea84615f5ab82723');
INSERT INTO Account(id, accountNumber, iban, tanType, dateOfCreation, passwordSalt, hashedPassword) VALUES(4, '00000000003', 'DE91772300000000000003', 1, '1990-01-01', '9KAFF9OVR8384B9', 'd065cda77a8dbbca87c19eea54f6a7cffa2d62f798bd6637ea84615f5ab82723');

INSERT INTO Bank (id, bankCode, bic, countryAlpha2Code, name, creditAccount_id, salaryAccount_id, housenr, street, zip, city, county, country) VALUES(1, '7723000', 'BYLADEM1DQE', 'DE', 'Bank Reichl', 3, 4, '3', 'Lilienweg', '93047', 'Regensburg', 'Bayern', 'Deutschland');

INSERT INTO AccountTransaction (id, account_id, type, transactionDate, amount, description) VALUES (3, 3, 1, '1990-01-01 12:00:00', 100000000000000, 'kapital der bank fuer kredite');
INSERT INTO AccountTransaction (id, account_id, type, transactionDate, amount, description) VALUES (4, 4, 1, '1990-01-01 12:00:00', 100000000000000, 'kapital der bank fuer loehne');

INSERT INTO Employee (id, firstname, lastname, gender, dateOfBirth, housenr, street, zip, city, county, country) VALUES(1, 'Johann', 'Meier', 0, '1988-04-02', '23', 'Galgenbergstraße', '93047', 'Regensburg', 'Bayern', 'Deutschland');
INSERT INTO Employee (id, firstname, lastname, gender, dateOfBirth, housenr, street, zip, city, county, country) VALUES(2, 'Maria', 'Dinkel', 1, '1981-12-10', '8b', 'Grabenstraße', '93047', 'Regensburg', 'Bayern', 'Deutschland');

INSERT INTO Customer(ID, DTYPE, housenr, street, zip, city, county, country) VALUES(1, 'PrivateCustomer', '1', 'Sauerteigstraße', '93047', 'Regensburg', 'Bayern', 'Deutschland');
INSERT INTO PrivateCustomer (id, firstName, lastName, gender, dateOfBirth) VALUES(1, 'Thomas', 'Feinschneider', 0, '1982-04-22');

INSERT INTO Customer(ID, DTYPE, housenr, street, zip, city, county, country) VALUES(2, 'CompanyCustomer', '10b', 'Niederbergweg', '93047', 'Regensburg', 'Bayern', 'Deutschland');
INSERT INTO CompanyCustomer (id, name, dateOfCreation) VALUES(2, 'Hossas GmbH', '2011-05-01');

INSERT INTO Account(id, accountNumber, iban, owner_id, accountManager_id, tanType, dateOfCreation, passwordSalt, hashedPassword) VALUES(1, '00000000000', 'DE21772300000000000000', 1, 1, 0, '2009-01-01', 'DDL9RM77TH1PD9RJ', '3735824d87d7dd09f754c509a5062a7c7f424022866b6fa9cbcadd8fe23c4ad5');
INSERT INTO Account(id, accountNumber, iban, owner_id, accountManager_id, tanType, dateOfCreation, passwordSalt, hashedPassword) VALUES(2, '00000000001', 'DE91772300000000000001', 2, 2, 1, '2013-04-01', '9KAFF9OVR8384B9', 'd065cda77a8dbbca87c19eea54f6a7cffa2d62f798bd6637ea84615f5ab82723');

INSERT INTO StandingOrder (id, fromAccount_id, toAccount_id, intervalUnit, intervalAmount, amount, description, startDate) VALUES (1, 1, 2, 3, 1, 1499, 'Test Standing Order (Monthly)', '2016-11-01 12:00:00');

INSERT INTO AccountTransaction (id, account_id, type, transactionDate, amount, description) VALUES (1, 1, 1, '2016-12-01 12:10:02', 30000, '300,- € eingezahlt');
INSERT INTO AccountTransaction (id, account_id, type, transactionDate, amount, description) VALUES (2, 1, 0, '2016-12-03 10:30:22', 1500, '15,- € abgehoben');
