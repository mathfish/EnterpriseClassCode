
INSERT INTO author(firstname, lastname, country) 
       VALUES ('earnest','hemmingway','usa'),
              ('oscar','wilde','ireland');
                                       
INSERT INTO artist(firstname, lastname) 
       VALUES ('tom','petty'),
              ('jon', 'bon jovi');

INSERT INTO band(name, country) 
       VALUES ('bon jovi','usa'); 

INSERT INTO thespian(firstname, lastname, country)
       VALUES ('tom','hanks','usa'),
              ('audrey','hepburn','british');

INSERT INTO book(title,bookyear,description) 
       VALUES ('the old man and the sea',1952, 
               'The Old Man and the Sea is a classic that tells the story of a battle between an aging, experienced fisherman, Santiago, and a large marlin'),
              ('de profundis',1890,
               'A letter written by Oscar Wilde during his imprisonment in Reading Gaol, to "Bosie" (Lord Alfred Douglas)'                            );


INSERT INTO cd(title, numoftracks, cdyear) 
       VALUES ('wildflowers',15,1994),
              ('slippery when wet',10,1986);

INSERT INTO dvd(title, filmyear, rating, timelength) 
       VALUES ('big',1988, 'PG','02:10:00'),
              ('my fair lady',1964, 'G','02:55:00');

INSERT INTO bookbyauthor(authorid, bookid) 
       VALUes (1,1),
              (2,2);

INSERT INTO cdbyartistorband(artistid, bandid, cdid) 
       VALUES (1,null,1),
              (2,1,2);

INSERT INTO dvdbythespian(dvdid, thespianid) 
       VALUES (1,1),
              (2,2);

INSERT INTO branch(name,city,zipcode,streetaddress,state) 
       VALUES ('north branch', 'north city', 11111, '100 main street', 'MD'),
              ('south branch', 'south city', 22222, '20th ave', 'MD');

INSERT INTO branchitem(branchid,checkedout, reserved, intransit, currentlocation)
       VALUES (1,false,false,false,1),
              (1,true,true,false,1),
              (2,true,false,false,2),
              (1,false,false,false,1),
              (2,false,false,false,2),
              (1,false,false,false,1),
              (1,true,true,false,1),
              (2,true,false,false,2),
              (1,false,false,false,1),
              (2,false,false,false,2),
              (1,false,false,false,1),
              (1,true,true,false,1),
              (2,true,false,false,2),
              (1,false,false,false,1),
              (2,false,false,false,2);

 
INSERT INTO branchbook(branchitemid, bookid)
       VALUES (1,1),
              (2,1),
              (3,1),
              (4,2),
              (5,2);

INSERT INTO branchcd (branchitemid, cdid)
       VALUES (6,1),
              (7,1),
              (8,1),
              (9,2),
              (10,2);

INSERT INTO branchdvd (branchitemid, dvdid)
       VALUES (11,1),
              (12,1),
              (13,1),
              (14,2),
              (15,2);

INSERT INTO patron (firstname, lastname, city, state, zipcode, streetaddress, phone, password,email) 
       VALUES ('john','doe','baltimore','MD',20000,'123 5th street',1112223333,'1234qwer','jd@email.com'),
              ('mary','jane','laurel','MD',30000,'33 downtown dr',2222222222,'121212','mj@email.com');

INSERT INTO reservation (patronid, branchitemid, forbranchid, reservdate, fulfilled)
       VALUES (1, 1, 2, '2016-06-01 08:00:00',true),
              (2, 2, 1,'2016-09-20 08:30:00',false),
              (1, 7, 1, '2016-09-21 08:45:00', false),
              (1, 12,1, '2016-09-24 09:00:00', false);

INSERT INTO checkout(patronid, checkoutdate, numberofitems, overdue, itemsreturned)
       VALUES (1, '2016-09-15 08:00:00',3, false, false),
              (2, '2016-09-16 08:15:00',3,false,false),
              (1, '2016-06-03 08:30:00',1,true,true);

INSERT INTO branchitemcheckout(branchitemid, checkoutid, overdue, duedate, returned, returndate)
       VALUES (2,1,false, '2016-10-15',false,null),
              (3,1,false,'2016-10-15',false,null),
              (1,3,true, '2016-07-03',true,'2016-07-04'),
              (7, 2,false, '2016-10-16',false, null),
              (8, 2,false, '2016-10-16',false, null),
              (11,1, false,'2016-10-15',false, null),
              (12,2, false,'2016-10-16',false, null);


INSERT INTO transfer(tobranchid, frombranchid, reservationid, patronid, branchitemid, transferdate)
       VALUES (2,1,1,1,1,'2016-06-01 10:00:00'),
              (1,2,1,1,1,'2016-07-05 10:00:00');

INSERT INTO fine(patronid, checkoutid, datepaid, amountpaid, paid)
       VALUES (1,3,'2016-07-10 14:00:00',1.00, true);
 
