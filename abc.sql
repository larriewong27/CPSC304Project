drop table Process;
drop table PayBill;
drop table CheckIn;
drop table Reservation;
drop table HasRoom;
drop table Member;
drop table Customer;
drop table Employee;
drop table RoomService;
drop table RoomType;


commit;


CREATE TABLE Customer(
    CID char(10) PRIMARY KEY
        check(CID like 'C%'),
    FirstName varchar(20),
    LastName varchar(20),
    Phone varchar(20),
    CreditCard varchar(20));
    
-- is a member

CREATE TABLE Member(
    CID char(10) PRIMARY KEY
        check(CID like 'C%'),
    points NUMBER,
    FOREIGN KEY (CID) REFERENCES Customer(CID));


CREATE TABLE Employee(
    EID char(10) PRIMARY KEY
        check(EID like 'E%'),
    FirstName varchar(20),
    LastName varchar(20));

    
    -- UPDATE Employee SET FirstName = 'aaa', LastName = 'bbb' WHERE EID = 'E234000119'

CREATE TABLE RoomType (
    RoomName varchar(30) PRIMARY KEY,
    DailyPrice NUMBER,
    points NUMBER,
    MaxPeople NUMBER);

CREATE TABLE HasRoom (
    RoomNum CHAR(3) PRIMARY KEY,
    RoomName VARCHAR(30) NOT NULL,
    Availability INTEGER NOT NULL,
    MaxPeople INTEGER NOT NULL,
    FOREIGN KEY (RoomName) REFERENCES RoomType(RoomName));
    -- 
    

CREATE TABLE RoomService(
    ServiceType varchar(20) PRIMARY KEY,
    Fees NUMBER check(Fees >= 0));


   -- SELECT b.TotalFee, rs.Fees FROM RoomService rs, PayBill b, Reservation r WHERE  r.cid = 'C000000001'  AND r.BookID = b.BookID AND rs.sid = 'S000000001';
    
CREATE TABLE Reservation (
    BookID CHAR(10) PRIMARY KEY
        check(BookID like 'B%'),
    "checkin" DATE,
    "checkout" DATE,
    deposit NUMBER,
    CID CHAR(10) NOT NULL
        check(CID like 'C%'), 
    RoomNum char(3) NOT NULL,
    Availability INTEGER NOT NULL,
    FOREIGN KEY (CID) REFERENCES Customer(CID)
        ON DELETE CASCADE,
    FOREIGN KEY (RoomNum) REFERENCES HasRoom(RoomNum)
        ON DELETE CASCADE);

CREATE TABLE CheckIn(
    BookID char(10) PRIMARY KEY
        check(BookID like 'B%'),
    RoomNum char(3) NOT NULL,
    EID char(10) NOT NULL
        check(EID like 'E%'),
    FOREIGN KEY (BookID) REFERENCES Reservation(BookID)
    ON DELETE CASCADE, 
    FOREIGN KEY (RoomNum) REFERENCES HasRoom(RoomNum),
    FOREIGN KEY (EID) REFERENCES Employee(EID));

-- SELECT c.CID, c.FirstName, c.LastName, ci.BookID, ci.RoomNum FROM CheckIn ci,Reservation r, Customer c WHERE c.CID=r.CID AND r.BookID = ci.BookID AND ci.EID = 'E234000119' 
    
    
   -- SELECT c.cid FROM Customer c, Process p, RoomService rs WHERE p.cid = c.cid AND p.ServiceType = rs.ServiceType GROUP BY c.cid HAVING COUNT(c.cid)>=(SELECT COUNT (*) FROM RoomService);
    
-- select c.cid from customer c, process p WHERE p.cid = c.cid Group BY c.cid HAVING COUNT(c.cid) >= (SELECT count(*) from roomservice)
CREATE TABLE Process(
    SID varchar(20)  PRIMARY KEY
        check(SID like 'S%'),
    ServiceType varchar(20),
    CID char(10) 
        check(CID like 'C%'),
    EID char(10)
        check(EID like 'E%'),
    --ServiceType varchar(20) NOT NULL,
    FOREIGN KEY (ServiceType) REFERENCES RoomService(ServiceType)
    ON DELETE CASCADE,
    FOREIGN KEY (CID) REFERENCES Customer(CID)
    ON DELETE CASCADE,
    FOREIGN KEY (EID) REFERENCES Employee(EID) 
    ON DELETE CASCADE);

    


CREATE TABLE PayBill(
    BID char(10) PRIMARY KEY
        check(BID like 'BI%'),
    BookID char(10) NOT NULL
        check(BookID like 'B%'),
    TotalFee number check(TotalFee >= 0) NOT NULL,
    PayDate Date NOT NULL,
    UNIQUE(BookID),
    FOREIGN KEY (BookID) REFERENCES CheckIn(BookID)
        ON DELETE CASCADE);

-- UPDATE PayBill SET TotalFee = 111 WHERE BID = (select pb.BID from PayBill pb,CheckIn ci,Reservation r WHERE pb.BookID=ci.BookId AND ci.BookId=r.BookId AND r.CID = 'C000000001')   



commit;


insert into Customer 
values('C000000001', 'Amy', 'Yang', '7789347813','5464568560345987');

insert into Customer 
values('C000056272', 'Colin', 'Perrotta', '7788835076','1234567890123452');

insert into Customer 
values('C000000002', 'Xiaoyu', 'Chen', '7789347814','5460008560341111');

insert into Customer 
values('C000000003', 'David', 'Long', '7789347814','5460008560341111');

insert into Customer 
values('C000000004', 'Dong', 'Chen', '7780007814','5460008560341110');

insert into Customer 
values('C000000005', 'Illean', 'Yan', '7782239893','1234567890125736');

insert into Customer 
values('C000000006', 'Kurt', 'Ford', '7782239894','5464568560345999');

insert into Customer 
values('C000000007', 'Barry', 'Massey', '7782239894','9232497899045099');

insert into Customer 
values('C000000008', 'Evan', 'Vega', '7782239866','1234567890143874');

insert into Customer 
values('C000000009', 'Iria', 'Suu', '7782239866','1000563330143874');

insert into Customer 
values('C000000010', 'XianJi', 'Fei', '2283239626','1111567890149999');

insert into Customer 
values('C000000011', 'Barry', 'Vega', '2283239626','9232497899045099');

insert into Customer 
values('C000000012', 'Ivan', 'Chen', '7782232323','3344567890143874');

insert into Customer 
values('C000000013', 'Jieying', 'Su', '7782232323','1667567890143874');

insert into Customer 
values('C000000014', 'Xiang', 'Long', '7782232323','1229567890143874');

insert into Customer 
values('C000000015', 'Jiahui', 'Massey', '7782239009','9232492233045099');

insert into Customer 
values('C000000016', 'Yi', 'Vega', '7782239668','1234009990143874');

insert into Customer 
values('C000000017', 'Fangzu', 'Su', '7782239668','100056789046464');

insert into Customer 
values('C000000018', 'Ted', 'Ta', '7292200026','1789567890143874');

insert into Customer 
values('C000000019', 'Amy', 'Zhang', '7782239895','9092497899045099');

insert into Customer 
values('C000000020', 'Ivan', 'Li', '7782239866','1256067890143874');

insert into Customer
values('C000000050', 'AC', 'Canada','9507777777', '1029384756473829');



insert into Member 
values('C000000001', '20');

insert into Member 
values('C000000002',  '200');

insert into Member 
values('C000000003',  '220');

insert into Member 
values('C000000004',  '0');

insert into Member 
values('C000000005',  '700');

insert into Member 
values('C000000017', '10');

insert into Member 
values('C000000019',  '23');

insert into Member 
values('C000000020',  '890');

insert into Member 
values('C000000014',  '0');

insert into Member 
values('C000000010',  '350');

insert into RoomType
values('One Bed Room', 200, 20, 4);

insert into RoomType
values('Two Bed Room', 400, 40, 6);

insert into RoomType
values('Three Bed Room', 500, 60, 8);

insert into RoomType
values('Four Bed Room', 600, 80, 10);

insert into RoomType
values('Big Room', 700, 100, 12);

insert into RoomType
values('Super Room', 800, 120, 14);

insert into RoomType
values('King', 1000, 200, 15);

insert into Employee 
values('E234000119', 'Vivian', 'Heigh');

insert into Employee 
values('E234000129', 'Kaitlyn', 'Ou');

insert into Employee 
values('E234000001', 'Judy', 'Garland');

insert into Employee 
values('E234000002', 'Sisley', 'Merrit');

insert into Employee 
values('E234000003', 'David', 'Lei');

insert into Employee 
values('E234000004', 'Illean', 'Lai');

insert into Employee 
values('E234000005', 'Marco', 'Wan');

insert into Employee 
values('E234000006', 'Tony', 'Ye');

insert into Employee 
values('E234000007', 'Sherman', 'Fu');

insert into Employee 
values('E234000008', 'Tom', 'Powers');

insert into Employee 
values('E234000009', 'Jennie', 'Lee');

insert into Employee 
values('E234000010', 'Yuehua', 'Ou');

insert into Employee 
values('E234000011', 'Sukiyaki', 'Wang');

insert into Employee 
values('E234000012', 'Nicole', 'Luo');

insert into HasRoom
values('100', 'One Bed Room', 1, 4);

insert into HasRoom
values('101', 'One Bed Room', 1, 4);

insert into HasRoom
values('102', 'One Bed Room', 1, 4);

insert into HasRoom
values('103', 'One Bed Room', 1, 4);

insert into HasRoom
values('104', 'One Bed Room', 0, 4);

insert into HasRoom
values('105', 'One Bed Room', 1, 4);

insert into HasRoom
values('106', 'One Bed Room', 0, 4);

insert into HasRoom
values('107', 'One Bed Room', 1, 4);

insert into HasRoom
values('108', 'One Bed Room', 1, 4);

insert into HasRoom
values('109', 'One Bed Room', 1, 4);

insert into HasRoom
values('110', 'One Bed Room', 1, 4);

insert into HasRoom
values('200', 'Two Bed Room', 1, 6);

insert into HasRoom
values('201', 'Two Bed Room', 1, 6);

insert into HasRoom
values('202', 'Two Bed Room', 0, 6);

insert into HasRoom
values('203', 'Two Bed Room', 0, 6);

insert into HasRoom
values('204', 'Two Bed Room', 0, 6);

insert into HasRoom
values('205', 'Two Bed Room', 1, 6);

insert into HasRoom
values('206', 'Two Bed Room', 1, 6);

insert into HasRoom
values('207', 'Two Bed Room', 0, 6);

insert into HasRoom
values('208', 'Two Bed Room', 1, 6);

insert into HasRoom
values('209', 'Two Bed Room', 1, 6);

insert into HasRoom
values('210', 'Two Bed Room', 1, 6);

insert into HasRoom
values('301', 'Three Bed Room', 1, 8);

insert into HasRoom
values('302', 'Three Bed Room', 1, 8);

insert into HasRoom
values('303', 'Three Bed Room', 1, 8);

insert into HasRoom
values('304', 'Three Bed Room', 0, 8);

insert into HasRoom
values('305', 'Three Bed Room', 1, 8);

insert into HasRoom
values('401', 'Four Bed Room', 0, 10);

insert into HasRoom
values('402', 'Four Bed Room', 1, 10);

insert into HasRoom
values('403', 'Four Bed Room', 1, 10);

insert into HasRoom
values('404', 'Four Bed Room', 0, 10);

insert into HasRoom
values('405', 'Four Bed Room', 0, 10);

insert into HasRoom
values('406', 'Big Room', 1, 12);

insert into HasRoom
values('407', 'Big Room', 1, 12);

insert into HasRoom
values('408', 'Big Room', 1, 12);

insert into HasRoom
values('409', 'Big Room', 0, 12);

insert into HasRoom
values('410', 'Super Room', 0, 14);

insert into HasRoom
values('411', 'Super Room', 1, 14);

insert into HasRoom
values('412', 'Super Room', 1, 14);

insert into HasRoom
values('500', 'King', 1, 15);

insert into Reservation
values ('B000000001', (date '2018-01-01'), (date '2018-01-03'), 200, 'C000000001', '101', 1);

insert into Reservation
values ('B000000002', (date '2018-01-28'), (date '2018-02-03'), 200, 'C000056272', '100', 1);

insert into Reservation
values ('B000000003', (date '2018-12-01'), (date '2018-12-03'), 200, 'C000000002', '200', 1);

insert into Reservation
values ('B000000004', (date '2018-03-02'), (date '2018-03-03'), 200, 'C000000003', '408', 1);

insert into Reservation
values ('B000000005', (date '2018-08-12'), (date '2018-08-28'), 200, 'C000000004', '205', 1);

insert into Reservation
values ('B000000006', (date '2018-07-28'), (date '2018-08-02'), 200, 'C000000005', '206', 1);

insert into Reservation
values ('B000000007', (date '2018-12-11'), (date '2018-12-23'), 200, 'C000000006', '210', 1);

insert into Reservation
values ('B000000008', (date '2018-02-14'), (date '2018-02-19'), 200, 'C000000007', '301', 1);

insert into Reservation
values ('B000000009', (date '2018-02-01'), (date '2018-02-03'), 200, 'C000000008', '109', 1);

insert into Reservation
values ('B000000010', (date '2018-11-29'), (date '2018-12-13'), 200, 'C000000009', '302', 1);

insert into Reservation
values ('B000000011', (date '2018-11-11'), (date '2018-11-13'), 200, 'C000000010', '411', 1);

insert into Reservation
values ('B000000012', (date '2018-03-20'), (date '2018-03-26'), 200, 'C000000011', '305', 1);

insert into Reservation
values ('B000000013', (date '2018-01-11'), (date '2018-01-13'), 200, 'C000000012', '406', 1);

insert into Reservation
values ('B000000014', (date '2018-12-28'), (date '2019-02-03'), 200, 'C000000013', '412', 1);

insert into Reservation
values ('B000000020', (date '2018-11-11'), (date '2018-11-20'), 500, 'C000000050', '500', 1);

Insert into CheckIn 
Values('B000000001', '101', 'E234000119');

Insert into CheckIn 
Values('B000000002', '100', 'E234000119');

Insert into CheckIn 
Values('B000000003', '200', 'E234000119');

Insert into CheckIn 
Values('B000000004', '408', 'E234000002');

Insert into CheckIn 
Values('B000000005', '205', 'E234000002');

Insert into CheckIn 
Values('B000000006', '206', 'E234000005');

Insert into CheckIn 
Values('B000000007', '210', 'E234000005');

Insert into CheckIn 
Values('B000000008', '301', 'E234000005');

Insert into CheckIn 
Values('B000000009', '109', 'E234000005');

Insert into CheckIn 
Values('B000000010', '302', 'E234000010');

Insert into CheckIn 
Values('B000000011', '411', 'E234000011');

Insert into CheckIn 
Values('B000000012', '305', 'E234000011');

Insert into CheckIn 
Values('B000000013', '406', 'E234000119');

Insert into CheckIn 
Values('B000000014', '412', 'E234000012');

insert into RoomService 
values('Breakfast' , 50);

insert into RoomService 
values('Dinner' , 100);

insert into RoomService 
values('Dinner2' , 150);

insert into RoomService 
values('SPA' , 200);

insert into RoomService 
values('Massage' , 130);

insert into RoomService 
values('Sport TV' , 30);

insert into RoomService 
values('Wine Night' , 50);

insert into RoomService 
values('Manicures' , 65);

insert into RoomService 
values('Bike Rent' , 40);

insert into RoomService 
values('Night Club' , 100);

insert into RoomService 
values('HotPot' , 200);

insert into PayBill
values ('BI00000002', 'B000000001', 420, (date '2018-01-01'));

insert into PayBill
values ('BI00000000', 'B000000002', 200, (date '2018-01-28'));

insert into PayBill
values ('BI00000001', 'B000000003', 400, (date '2018-12-01'));

insert into PayBill
values ('BI00000003', 'B000000004', 700, (date '2018-03-02'));

insert into PayBill
values ('BI00000004', 'B000000005', 400, (date '2018-08-12'));

insert into PayBill
values ('BI00000005', 'B000000006', 400, (date '2018-07-28'));

insert into PayBill
values ('BI00000006', 'B000000007', 400, (date '2018-12-11'));

insert into PayBill
values ('BI00000007', 'B000000008', 700, (date '2018-02-14'));

insert into PayBill
values ('BI00000008', 'B000000009', 755, (date '2018-02-01'));

insert into PayBill
values ('BI00000009', 'B000000010', 500, (date '2018-11-29'));

insert into PayBill
values ('BI00000010', 'B000000011', 1045 , (date '2018-11-11'));

insert into PayBill
values ('BI00000011', 'B000000012', 500, (date '2018-03-20'));

insert into PayBill
values ('BI00000012', 'B000000013', 700, (date '2018-01-11'));

insert into PayBill
values ('BI00000013', 'B000000014', 800, (date '2018-12-28'));

insert into Process  
values ('S000000001','Breakfast', 'C000000001', 'E234000119');

---
insert into Process  
values ('S000000022','Dinner', 'C000000001', 'E234000119');
insert into Process  
values ('S000000023','Dinner2', 'C000000001', 'E234000119');
insert into Process  
values ('S000000024','SPA', 'C000000001', 'E234000119');
insert into Process  
values ('S000000025','Massage', 'C000000001', 'E234000119');
insert into Process  
values ('S000000026','Sport TV', 'C000000001', 'E234000119');
insert into Process  
values ('S000000027','Wine Night', 'C000000001', 'E234000119');
insert into Process  
values ('S000000028','Manicures', 'C000000001', 'E234000119');
insert into Process  
values ('S000000029','Bike Rent', 'C000000001', 'E234000119');
insert into Process  
values ('S000000030','Night Club', 'C000000001', 'E234000119');
insert into Process  
values ('S000000032','HotPot', 'C000000001', 'E234000119');



insert into Process 
values ('S000000002','Massage', 'C000000001', 'E234000119');

insert into Process  
values ('S000000003','Bike Rent', 'C000000001', 'E234000119');

insert into Process  
values ('S000000004','Manicures', 'C000000008', 'E234000005');

insert into Process  
values ('S000000005','Bike Rent', 'C000000008', 'E234000005');

insert into Process  
values ('S000000006', 'Sport TV','C000000008', 'E234000005');

insert into Process  
values ('S000000007', 'Dinner2','C000000008', 'E234000005');

insert into Process  
values ('S000000008', 'SPA','C000000008', 'E234000005');

insert into Process  
values ('S000000009','Manicures', 'C000000010', 'E234000011');

insert into Process  
values ('S000000010', 'Sport TV','C000000010', 'E234000011');

insert into Process  
values ('S000000011', 'Breakfast','C000000010', 'E234000011');

insert into Process  
values ('S000000012', 'Dinner','C000000010', 'E234000011');

insert into Process  
values ('S000000013','SPA' , 'C000000010', 'E234000011');

insert into Process  
values ('S000000014','Night Club', 'C000000010', 'E234000011');

insert into Process  
values ('S000000015', 'SPA','C000000006', 'E234000005');

commit;




