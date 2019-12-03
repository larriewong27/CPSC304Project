
drop table AsksFor;
drop table Bill;
drop table CheckIn;
drop table Booking;
drop table RoomHas;
drop table Guest;
drop table Employee;
drop table Service;
drop table RoomType1;
drop table RoomType2;

commit;

CREATE TABLE RoomType2 (
    BedType varchar(20) primary key,
    MaxPeople integer);

CREATE TABLE RoomType1 (
    Name VARCHAR(30),
    BasePrice NUMBER,
    Accessibility INTEGER
    	check(Accessibility in (0, 1)),
    BedType VARCHAR(20),
    PRIMARY KEY (Name, Accessibility, BedType),
    FOREIGN KEY (BedType) REFERENCES RoomType2(BedType));

CREATE TABLE RoomHas (
    Room_num CHAR(3) PRIMARY KEY,
    "Floor" INTEGER NOT NULL
    	check("Floor" < 4 and "Floor" >= 1),
    TypeName VARCHAR(30) NOT NULL,
    Accessibility INTEGER NOT NULL,
    BedType VARCHAR(20) NOT NULL,
	FOREIGN KEY (TypeName, Accessibility, BedType) REFERENCES RoomType1(Name, Accessibility, BedType));

CREATE TABLE Guest(
	Guest_num char(10) PRIMARY KEY
		check(Guest_num like 'G%'),
	First_name varchar(20),
    Last_name varchar(20),
	ID varchar (20),
	CreditCard_num varchar(20),
	Address varchar(50),
	Phone varchar(20));

CREATE TABLE Employee(
	Employee_num char(10) PRIMARY KEY
		check(Employee_num like 'E%'),
	First_name varchar(20),
    Last_name varchar(20),
	Role varchar(20));

CREATE TABLE Booking (
    Booking_num CHAR(10) PRIMARY KEY
    	check(Booking_num like 'BK%'),
    "From" DATE,
    "To" DATE,
    PromoCode INTEGER 
    	check(PromoCode in (0, 1)),
    Guest_num CHAR(10) NOT NULL
    	check(Guest_num like 'G%'), 
    TypeName VARCHAR(30) NOT NULL,
    Accessibility INTEGER NOT NULL
        check(Accessibility in (0,1)),
    BedType VARCHAR(20) NOT NULL,
    FOREIGN KEY (Guest_num) REFERENCES Guest(Guest_num)
        ON DELETE CASCADE,
    FOREIGN KEY (TypeName, Accessibility, BedType) REFERENCES RoomType1(Name, Accessibility, BedType)
        ON DELETE CASCADE);

CREATE TABLE CheckIn(
	Booking_num char (10) PRIMARY KEY
		check(Booking_num like 'BK%'),
	Room_num char(3) NOT NULL,
	CheckInEmployee_num char(10) NOT NULL
		check(CheckInEmployee_num like 'E%'),
	FOREIGN KEY (Booking_num) REFERENCES Booking(Booking_num)
        ON DELETE CASCADE,
	FOREIGN KEY (Room_num) REFERENCES RoomHas(Room_num),
	FOREIGN KEY (CheckInEmployee_num) REFERENCES Employee(Employee_num));

CREATE TABLE Service(
    ServiceType varchar(20) PRIMARY KEY,
    Fee number check(Fee >= 0));

CREATE TABLE AsksFor(
	Service_num char(10)  PRIMARY KEY
		check(Service_num like 'S%'),
    Booking_num char(10)  NOT NULL
    	check(Booking_num like 'BK%'),
	Employee_num char(10)
		check(Employee_num like 'E%'),
	ServiceType varchar(20) NOT NULL,
	FOREIGN KEY (Booking_num) REFERENCES CheckIn(Booking_num)
		ON DELETE CASCADE,
	FOREIGN KEY (Employee_num) REFERENCES Employee(Employee_num),
    FOREIGN KEY (ServiceType) REFERENCES Service(ServiceType));

CREATE TABLE Bill(
    Bill_num  char(10) PRIMARY KEY
    	check(Bill_num like 'B%'),
    Booking_num  char(10) NOT NULL
        check(Booking_num like 'BK%'),
    Amount  number check(Amount >= 0) NOT NULL,
    DateGenerated Date NOT NULL,
    UNIQUE(Booking_num),
    FOREIGN KEY (Booking_num) REFERENCES CheckIn(Booking_num)
        ON DELETE CASCADE);

commit;

insert into RoomType2
values('2 double', 4);

insert into RoomType2
values('2 queen', 5);

insert into RoomType2
values('2 king', 6);

insert into RoomType2
values('1 queen 1 sofabed', 4);

insert into RoomType2
values('1 double 1 sofabed', 3);

insert into RoomType2
values('3 king', 6);

insert into RoomType2
values('2 single', 2);

insert into RoomType2
values('2 queen 1 sofabed', 5);

insert into RoomType1
values('standard', 110, 0, '1 double 1 sofabed');

insert into RoomType1
values('standard', 120, 1, '2 double');

insert into RoomType1
values('standard', 120, 0, '2 double');

insert into RoomType1
values('deluxe', 140, 0, '1 queen 1 sofabed');

insert into RoomType1
values('deluxe', 140, 1, '1 queen 1 sofabed');

insert into RoomType1
values('deluxe', 150, 1, '2 queen');

insert into RoomType1
values('deluxe', 150, 0, '2 queen');

insert into RoomType1
values('suite', 250, 1, '2 king');

insert into RoomType1
values('suite', 250, 0, '2 king');

insert into RoomType1
values('presidential suite', 300, 0, '3 king');

insert into RoomType1
values('presidential suite', 300, 1, '3 king');

insert into RoomType1
values('quad', 160, 0, '2 queen 1 sofabed');

insert into RoomType1
values('quad', 160, 1, '2 queen 1 sofabed');

insert into RoomType1
values('twin', 80, 0, '2 single');

insert into RoomType1
values('twin', 80, 1, '2 single');

insert into RoomHas
values('100', 1, 'standard', 1, '2 double');

insert into RoomHas
values('101', 1, 'standard', 1, '2 double');

insert into RoomHas
values('102', 1, 'standard', 1, '2 double');

insert into RoomHas
values('103', 1, 'standard', 0, '1 double 1 sofabed');

insert into RoomHas
values('104', 1, 'deluxe', 1, '1 queen 1 sofabed');

insert into RoomHas
values('105', 1, 'deluxe', 1, '2 queen');

insert into RoomHas
values('200', 2, 'standard', 0, '2 double');

insert into RoomHas
values('201', 2, 'standard', 0, '2 double');

insert into RoomHas
values('202', 2, 'standard', 0, '2 double');

insert into RoomHas
values('203', 2, 'standard', 0, '2 double');

insert into RoomHas
values('204', 2, 'deluxe', 0, '2 queen');

insert into RoomHas
values('205', 2, 'suite', 0, '2 king');

insert into RoomHas
values('300', 3, 'deluxe', 0, '1 queen 1 sofabed');

insert into RoomHas
values('301', 3, 'deluxe', 0, '2 queen');

insert into RoomHas
values('302', 3, 'deluxe', 0, '2 queen');

insert into RoomHas
values('303', 3, 'deluxe', 0, '2 queen');

insert into RoomHas
values('304', 3, 'suite', 0, '2 king');

insert into RoomHas
values('305', 3, 'suite', 0, '2 king');

insert into RoomHas
values('311', 2, 'presidential suite', 0, '3 king');

insert into RoomHas
values('312', 3, 'presidential suite', 1, '3 king');

insert into RoomHas
values('313', 3, 'quad', 0, '2 queen 1 sofabed');

insert into RoomHas
values('314', 3, 'quad', 1, '2 queen 1 sofabed');

insert into RoomHas
values('320', 3, 'quad', 1, '2 queen 1 sofabed');

insert into RoomHas
values('321', 3, 'twin', 0, '2 single');

insert into RoomHas
values('322', 3, 'twin', 1, '2 single');

insert into Guest 
values('G000000019', 'Christina', 'Barton', '8824-2357', '1234567890345612', '3723 Garry St., Burnaby, BC, V3H1G7', '7788838967');

insert into Guest 
values('G000000025', 'Steven', 'Shi', '9928-1123', '4564567456349014', '4598 Graville Avenue., Vancouver, BC, V6M2V1', '7786538865');

insert into Guest 
values('G000000032', 'Amy', 'Yang', '1098-8362', '5464568560345987', '3884 Harry St. Vancouver, BC, V5H 3Z7', '7789347813');

insert into Guest 
values('G000000038', 'Coco', 'Lee', '9372-4625', '4287367890345908', '10783 Thunder St., Calgary, TY11K7', '6048834567');

insert into Guest 
values('G000000042', 'Kuoso', 'Saiki', '4765-5867', '9232497899045094', '3723 Nendo St., Toronto, M4C5K7', '7789807654');

insert into Guest 
values('G000000113', 'Silas', 'Krouse', '8824-1235', '1234567890123450', '1005 Garry St', '7788835074');

insert into Guest 
values('G000002412', 'Ebonie', 'Repka', '8824-1236', '1234567890123451', '1006 Granville Avenue', '7788835075');

insert into Guest 
values('G000056272', 'Colin', 'Perrotta', '8824-1237', '1234567890123452', '1007 Harry St', '7788835076');

insert into Guest 
values('G003423438', 'Valarie', 'Folks', '8824-1238', '1234567890123453', '1008 Thunder St', '7788835077');

insert into Guest 
values('G012323342', 'Candis', 'Krauth', '8824-1239', '1234567890123454', '1009 Nendo St', '7788835078');

insert into Guest
values('G000000102', 'Shannon', 'Nash', '9372-1004', '4287367890345999', '2314 St. John St., Calgary, TY31K9', '6048834599');

insert into Guest
values('G000000104', 'Charles', 'Castro', '8824-1006', '1234567890123499', '3719 Eglantine Ave', '7788835099');

insert into Guest
values('G000002105', 'Wilbert', 'Long', '8824-1007', '1234567890123430', '3713 Reserve St', '7788848384');

insert into Guest
values('G000000109', 'Jorge', 'Stevenson', '8824-1011', '1234567890125748', '3948 Third Ave', '7788835119');

insert into Guest
values('G000002110', 'Gustavo', 'Newman', '8824-1012', '1234567890122847', '3958 Blundell St', '7788848114');

insert into Guest
values('G000056111', 'Lee', 'Mcbride', '8824-1013', '1234567890125736', '3939 Vanderhoof Rd', '7788835116');

insert into Guest
values('G003423112', 'Guillermo', 'Wilson', '8824-1014', '1234567890144855', '3999 184th St', '7788835119');

insert into Guest
values('G000000099', 'Patti', 'Cruz', '8824-1001', '1234567890345699', '3807 Fourth Ave., Calgary, AB, T2P0H3', '7788838999');

insert into Guest
values('G000000100', 'Vicki', 'Reynolds', '8824-1002', '4564567456349099', '2086 Silver Springs Blvd., Calgary, AB, T3K4E2', '7786538899');

insert into Guest
values('G000000101', 'Kurt', 'Ford', '1098-1003', '5464568560345999', '2452 Mountain Rd., Vancouver, BC, V1C1H6', '7789347899');

insert into Guest
values('G000000103', 'Barry', 'Massey', '4765-1005', '9232497899045099', '2167 Algonquin Blvd., Ontario, P4N1C3', '7789807699');

insert into Guest
values('G000056106', 'Evan', 'Vega', '8824-1008', '1234567890124839', '2874 Wycroft Rd', '7788835996');

insert into Guest
values('G003423107', 'Lee', 'Mitchell', '8824-1009', '1234567890148485', '3612 Riverport Rd', '7788835899');

insert into Guest
values('G012323108', 'Boyd', 'Washington', '8824-1010', '1234567890143874', '1039 Nendom St', '7788835998');

insert into Employee 
values('E234111111', 'Vivian', 'Heigh', 'Server');

insert into Employee 
values('E234111112', 'Harrison', 'Lei', 'Server');

insert into Employee 
values('E234111113', 'Judy', 'Garland', 'Housekeeper');

insert into Employee 
values('E234111114', 'Justin', 'Fu', 'Housekeeper');

insert into Employee 
values('E234111115', 'Hazel', 'Merrit', 'Server');

insert into Employee
values('E234111116', 'Carol', 'Jiang', 'Conceirge');

insert into Employee 
values('E234111117', 'Nicole', 'Fanning', 'Housekeeper');

insert into Employee 
values('E234111118', 'Sisley', 'Washaw', 'Server');

insert into Employee 
values('E234111119', 'Kaitlyn', 'Ou', 'Conceirge');

insert into Employee 
values('E234111110', 'Illean', 'Yan', 'Server');

insert into Employee 
values('E234111201', 'Toni', 'Mitchell', 'Server');

insert into Employee 
values('E234111202', 'Sherman', 'Pittman', 'Housekeeper');

insert into Employee 
values('E234111203', 'Homer', 'Powers', 'Housekeeper');

insert into Employee 
values('E234111204', 'Renee', 'Grant', 'Housekeeper');

insert into Employee 
values('E234111205', 'Darin', 'Black', 'Server');

insert into Employee
values('E234111206', 'Peter', 'Blair', 'Conceirge');

insert into Employee 
values('E234111207', 'Jared', 'Kennedy', 'Housekeeper');

insert into Employee 
values('E234111208', 'Chester', 'Holland', 'Conceirge');

insert into Employee 
values('E234111209', 'Fred', 'Soto', 'Conceirge');

insert into Employee 
values('E234111210', 'Lola', 'Carson', 'Server');

insert into Booking
values ('BK00000000', (date '2018-01-01'), (date '2018-01-03'), 0, 'G000000019', 'standard', 0, '2 double');

insert into Booking
values ('BK00000001', (date '2018-01-04'), (date '2018-01-07'), 0, 'G000000025', 'standard', 1, '2 double');

insert into Booking
Values ('BK00000002', (date '2018-02-03'), (date '2018-02-05'), 0, 'G000000032', 'deluxe', 0, '1 queen 1 sofabed');

insert into Booking
Values ('BK00000003', (date '2018-05-14'), (date '2018-05-18'), 1, 'G000000038', 'deluxe', 0, '2 queen');

insert into Booking
Values ('BK00000004', (date '2018-04-01'), (date '2018-04-03'), 1, 'G000000042', 'suite', 0, '2 king');

insert into Booking
Values ('BK00000005', (date '2018-06-04'), (date '2018-06-07'), 0, 'G000000113', 'standard', 1, '2 double');

insert into Booking
Values ('BK00000006', (date '2018-06-06'), (date '2018-06-07'), 0, 'G000002412', 'deluxe', 1, '2 queen');

insert into Booking
Values ('BK00000007', (date '2018-06-02'), (date '2018-06-05'), 1, 'G000002412', 'standard', 0, '2 double');

insert into Booking
Values ('BK00000008', (date '2018-06-12'), (date '2018-06-14'), 0, 'G000056272', 'suite', 0, '2 king');

insert into Booking
Values ('BK00000009', (date '2018-08-05'), (date '2018-08-07'), 0, 'G003423438', 'standard', 1, '2 double');

insert into Booking
values ('BK00000010', (date '2018-12-22'), (date '2018-12-25'), 0, 'G012323342', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000011', (date '2018-08-02'), (date '2018-09-02'), 0, 'G000000099', 'suite', 0, '2 king');

insert into Booking
values ('BK00000012', (date '2018-05-22'), (date '2018-05-25'), 1, 'G000000100', 'suite', 0, '2 king');

insert into Booking
values ('BK00000013', (date '2018-07-22'), (date '2018-07-23'), 0, 'G000000101', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000014', (date '2018-11-22'), (date '2018-12-25'), 1, 'G000000102', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000015', (date '2018-06-22'), (date '2018-06-22'), 0, 'G000000103', 'suite', 0, '2 king');

insert into Booking
values ('BK00000016', (date '2018-07-22'), (date '2018-07-25'), 0, 'G000000104', 'standard', 1, '2 double');

insert into Booking
values ('BK00000017', (date '2018-10-10'), (date '2018-10-25'), 0, 'G000002105', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000018', (date '2018-09-09'), (date '2018-09-25'), 1, 'G000056106', 'standard', 0, '2 double');

insert into Booking
values ('BK00000019', (date '2018-10-22'), (date '2018-10-23'), 0, 'G003423107', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000020', (date '2018-11-22'), (date '2018-12-25'), 0, 'G012323108', 'deluxe', 0, '2 queen');

insert into Booking
values ('BK00000021', (date '2018-07-22'), (date '2018-08-01'), 0, 'G000000109', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000022', (date '2018-02-22'), (date '2018-02-25'), 0, 'G000002110', 'deluxe', 0, '2 queen');

insert into Booking
values ('BK00000023', (date '2018-04-11'), (date '2018-04-15'), 0, 'G000056111', 'deluxe', 1, '2 queen');

insert into Booking
values ('BK00000024', (date '2018-03-11'), (date '2018-03-12'), 1, 'G003423112', 'standard', 0, '2 double');

insert into Booking
values ('BK00000025', (date '2018-05-22'), (date '2018-05-25'), 0, 'G000002412', 'suite', 0, '2 king');

Insert into CheckIn 
Values('BK00000000', '302', 'E234111110');

Insert into CheckIn 
Values('BK00000001', '100', 'E234111111');

Insert into CheckIn 
Values('BK00000002', '101', 'E234111112');

Insert into CheckIn 
Values('BK00000003', '102', 'E234111113');

Insert into CheckIn 
Values('BK00000004', '103', 'E234111114');

Insert into CheckIn 
Values('BK00000005', '104', 'E234111115');

Insert into CheckIn 
Values('BK00000006', '201', 'E234111116');

Insert into CheckIn 
Values('BK00000007', '202', 'E234111117');

Insert into CheckIn 
Values('BK00000008', '203', 'E234111118');

insert into CheckIn
values('BK00000009', '200', 'E234111118');

Insert into CheckIn
Values('BK00000010', '322', 'E234111110');
Insert into CheckIn
Values('BK00000011', '311', 'E234111110');
Insert into CheckIn
Values('BK00000012', '312', 'E234111110');
Insert into CheckIn
Values('BK00000013', '303', 'E234111110');
Insert into CheckIn
Values('BK00000014', '305', 'E234111110');
Insert into CheckIn
Values('BK00000015', '302', 'E234111110');
Insert into CheckIn
Values('BK00000016', '300', 'E234111110');
Insert into CheckIn
Values('BK00000017', '204', 'E234111110');
Insert into CheckIn
Values('BK00000018', '200', 'E234111110');
Insert into CheckIn
Values('BK00000019', '101', 'E234111110');
Insert into CheckIn
Values('BK00000020', '105', 'E234111110');
Insert into CheckIn
Values('BK00000021', '203', 'E234111110');
Insert into CheckIn
Values('BK00000022', '102', 'E234111110');
Insert into CheckIn
Values('BK00000023', '101', 'E234111110');
Insert into CheckIn
Values('BK00000024', '103', 'E234111110');
Insert into CheckIn
Values('BK00000025', '104', 'E234111110');

insert into Service 
values('Breakfast', 15);

insert into Service 
values('Lunch', 20);

insert into Service
values('Dinner', 40);

insert into Service 
values('Laundry', 2);

insert into Service 
values('Shuttle', 25);

insert into Service 
values('Housekeeping', 5);

insert into Service
values('SPA Package', 50);

insert into Service
values('Treadmill', 10);

insert into Service
values('Swimming Pool', 7);

insert into Service
values('Dog Feeding', 14);

insert into Service
values('TV Channel Upgrade', 8);

insert into Service
values('Car Hire', 7);

insert into Service
values('Bike Hire', 8);

insert into Service
values('Guide', 4);

insert into Service
values('Bar Night', 30);

insert into Service
values('Night Party', 15);

insert into AsksFor
values ('S000000000', 'BK00000002', 'E234111117', 'Housekeeping');

insert into AsksFor
values ('S000000010', 'BK00000002', 'E234111115', 'Breakfast');

insert into AsksFor
values ('S000000011', 'BK00000002', 'E234111115', 'Lunch');

insert into AsksFor
values ('S000000012', 'BK00000002', 'E234111111', 'Dinner');

insert into AsksFor
values ('S000000013', 'BK00000002', 'E234111116', 'Shuttle');

insert into AsksFor
values ('S000000014', 'BK00000002', 'E234111113', 'Laundry');

insert into AsksFor
values ('S000000001', 'BK00000002', 'E234111209', 'SPA Package');

insert into AsksFor
values ('S000000021', 'BK00000002', 'E234111209', 'Treadmill');

insert into AsksFor
values ('S000000022', 'BK00000002', 'E234111210', 'Swimming Pool');

insert into AsksFor
values ('S000000023', 'BK00000002', 'E234111204', 'Dog Feeding');

insert into AsksFor
values ('S000000024', 'BK00000002', 'E234111204', 'TV Channel Upgrade');

insert into AsksFor
values ('S000000025', 'BK00000002', 'E234111202', 'Car Hire');

insert into AsksFor
values ('S000000026', 'BK00000002', 'E234111202', 'Bike Hire');

insert into AsksFor
values ('S000000027', 'BK00000002', 'E234111115', 'Guide');

insert into AsksFor
values ('S000000028', 'BK00000002', 'E234111201', 'Bar Night');

insert into AsksFor
values ('S000000029', 'BK00000002', 'E234111201', 'Night Party');

insert into AsksFor
values ('S000000030', 'BK00000002', 'E234111115', 'Breakfast');

insert into AsksFor
values ('S000000031', 'BK00000002', 'E234111115', 'Breakfast');

insert into AsksFor
values ('S000000002', 'BK00000005', 'E234111112', 'Breakfast');

insert into AsksFor
values ('S000000008', 'BK00000005', 'E234111113', 'Laundry');

insert into AsksFor
values ('S000000019', 'BK00000005', 'E234111117', 'Housekeeping');

insert into AsksFor
values ('S000000015', 'BK00000005', 'E234111115', 'Lunch');

insert into AsksFor
values ('S000000016', 'BK00000005', 'E234111111', 'Dinner');

insert into AsksFor
values ('S000000017', 'BK00000005', 'E234111116', 'Shuttle');

insert into AsksFor
values ('S000000041', 'BK00000005', 'E234111209', 'SPA Package');

insert into AsksFor
values ('S000000042', 'BK00000005', 'E234111209', 'Treadmill');

insert into AsksFor
values ('S000000043', 'BK00000005', 'E234111210', 'Swimming Pool');

insert into AsksFor
values ('S000000044', 'BK00000005', 'E234111204', 'Dog Feeding');

insert into AsksFor
values ('S000000045', 'BK00000005', 'E234111204', 'TV Channel Upgrade');

insert into AsksFor
values ('S000000046', 'BK00000005', 'E234111202', 'Car Hire');

insert into AsksFor
values ('S000000047', 'BK00000005', 'E234111202', 'Bike Hire');

insert into AsksFor
values ('S000000048', 'BK00000005', 'E234111115', 'Guide');

insert into AsksFor
values ('S000000049', 'BK00000005', 'E234111201', 'Bar Night');

insert into AsksFor
values ('S000000050', 'BK00000005', 'E234111201', 'Night Party');

insert into AsksFor
values ('S000000003', 'BK00000004', 'E234111115', 'Lunch');

insert into AsksFor
values ('S000000004', 'BK00000008', 'E234111112', 'Lunch');

insert into AsksFor
values ('S000000005', 'BK00000002', 'E234111111', 'Lunch');

insert into AsksFor
values ('S000000006', 'BK00000003', 'E234111111', 'Lunch');

insert into AsksFor
values ('S000000007', 'BK00000003', 'E234111116', 'Shuttle');

insert into AsksFor
values ('S000000009', 'BK00000008', 'E234111113', 'Laundry');

insert into AsksFor
values ('S000001000', 'BK00000013', 'E234111117', 'Housekeeping');

insert into AsksFor
values ('S000002010', 'BK00000013', 'E234111115', 'Breakfast');

insert into AsksFor
values ('S000003011', 'BK00000013', 'E234111115', 'Lunch');

insert into AsksFor
values ('S000004012', 'BK00000013', 'E234111111', 'Dinner');

insert into AsksFor
values ('S000005013', 'BK00000013', 'E234111116', 'Shuttle');

insert into AsksFor
values ('S000006014', 'BK00000013', 'E234111113', 'Laundry');

insert into AsksFor
values ('S000007001', 'BK00000013', 'E234111209', 'SPA Package');

insert into AsksFor
values ('S000008021', 'BK00000013', 'E234111209', 'Treadmill');

insert into AsksFor
values ('S000009022', 'BK00000013', 'E234111210', 'Swimming Pool');

insert into AsksFor
values ('S000001123', 'BK00000013', 'E234111204', 'Dog Feeding');

insert into AsksFor
values ('S000001224', 'BK00000013', 'E234111204', 'TV Channel Upgrade');

insert into AsksFor
values ('S000001325', 'BK00000013', 'E234111202', 'Car Hire');

insert into AsksFor
values ('S000001426', 'BK00000013', 'E234111202', 'Bike Hire');

insert into AsksFor
values ('S000001527', 'BK00000013', 'E234111115', 'Guide');

insert into AsksFor
values ('S000001628', 'BK00000013', 'E234111201', 'Bar Night');

insert into AsksFor
values ('S000001729', 'BK00000013', 'E234111201', 'Night Party');

insert into AsksFor
values ('S100001000', 'BK00000017', 'E234111117', 'Housekeeping');

insert into AsksFor
values ('S200002010', 'BK00000017', 'E234111115', 'Breakfast');

insert into AsksFor
values ('S300003011', 'BK00000017', 'E234111115', 'Lunch');

insert into AsksFor
values ('S400004012', 'BK00000017', 'E234111111', 'Dinner');

insert into AsksFor
values ('S500005013', 'BK00000017', 'E234111116', 'Shuttle');

insert into AsksFor
values ('S600006014', 'BK00000017', 'E234111113', 'Laundry');

insert into AsksFor
values ('S700007001', 'BK00000017', 'E234111209', 'SPA Package');

insert into AsksFor
values ('S800008021', 'BK00000017', 'E234111209', 'Treadmill');

insert into AsksFor
values ('S900009022', 'BK00000017', 'E234111210', 'Swimming Pool');

insert into AsksFor
values ('S110001123', 'BK00000017', 'E234111204', 'Dog Feeding');

insert into AsksFor
values ('S120001224', 'BK00000017', 'E234111204', 'TV Channel Upgrade');

insert into AsksFor
values ('S130001325', 'BK00000017', 'E234111202', 'Car Hire');

insert into AsksFor
values ('S140001426', 'BK00000017', 'E234111202', 'Bike Hire');

insert into AsksFor
values ('S150001527', 'BK00000017', 'E234111115', 'Guide');

insert into AsksFor
values ('S160001628', 'BK00000017', 'E234111201', 'Bar Night');

insert into AsksFor
values ('S170001729', 'BK00000017', 'E234111201', 'Night Party');

insert into Bill
values ('B123848711', 'BK00000000', 500, (date '2018-01-04'));

insert into Bill
values ('B348712398', 'BK00000001', 1002, (date '2018-02-03'));

insert into Bill
values ('B234874821', 'BK00000002', 550, (date '2018-04-01'));

insert into Bill
values ('B304395809', 'BK00000003', 850, (date '2018-05-23'));

insert into Bill
values ('B234878488', 'BK00000004', 700, (date '2018-06-11'));

insert into Bill
values ('B209856784', 'BK00000005', 400, (date '2018-05-09'));

insert into Bill
values ('B001847737', 'BK00000006', 200, (date '2018-04-16'));

insert into Bill
values ('B579834588', 'BK00000007', 1000, (date '2018-03-23'));

insert into Bill
values ('B345875348', 'BK00000008', 3200, (date '2018-07-18'));

insert into Bill
values ('B398273487', 'BK00000009', 220, (date '2018-11-23'));

insert into Bill
values ('B123841211', 'BK00000010', 500, (date '2018-10-15'));

insert into Bill
values ('B123848712', 'BK00000011', 456, (date '2018-12-01'));

insert into Bill
values ('B123848713', 'BK00000012', 456, (date '2018-05-01'));

insert into Bill
values ('B123848714', 'BK00000013', 678, (date '2018-05-03'));

insert into Bill
values ('B123848715', 'BK00000014', 2345, (date '2018-03-05'));

insert into Bill
values ('B123848716', 'BK00000015', 454, (date '2018-08-10'));

insert into Bill
values ('B123848717', 'BK00000016', 754, (date '2018-12-21'));

insert into Bill
values ('B123848718', 'BK00000017', 734, (date '2018-11-21'));

insert into Bill
values ('B123848719', 'BK00000018', 456, (date '2018-02-22'));

insert into Bill
values ('B123848721', 'BK00000019', 574, (date '2018-01-14'));

insert into Bill
values ('B123848731', 'BK00000020', 378, (date '2018-10-17'));

insert into Bill
values ('B123848741', 'BK00000021', 983, (date '2018-09-24'));

insert into Bill
values ('B123848751', 'BK00000022', 368, (date '2018-12-18'));

insert into Bill
values ('B123848761', 'BK00000023', 5009, (date '2018-07-02'));

insert into Bill
values ('B123848771', 'BK00000024', 3490, (date '2018-12-05'));

insert into Bill
values ('B123848791', 'BK00000025', 1500, (date '2018-12-12'));

commit;