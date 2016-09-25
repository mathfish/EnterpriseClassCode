
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

INSERT INTO branchbook(bookid, branchid, checkedout, reserved, intransit, currentlocation)
       VALUES (1,1,false,false,false,1),
              (1,1,true,true,false,1),
              (1,2,true,false,false,2),
              (2,1,false,false,false,1),
              (2,2,false,false,false,2);

INSERT INTO branchcd (cdid, branchid, checkedout, reserved, intransit, currentlocation)
       VALUES (1,1,false,false,false,1),
              (1,1,true,true,false,1),
              (1,2,true,false,false,2),
              (2,1,false,false,false,1),
              (2,2,false,false,false,2);

INSERT INTO branchdvd (dvdid, branchid, checkedout, reserved, intransit, currentlocation)
       VALUES (1,1,false,false,false,1),
              (1,1,true,true,false,1),
              (1,2,true,false,false,2),
              (2,1,false,false,false,1),
              (2,2,false,false,false,2);

INSERT INTO patron (firstname, lastname, city, state, zipcode, streetaddress, phone, password) 
       VALUES ('john','doe','baltimore','MD',20000,'123 5th street',1112223333,'1234qwer'),
              ('mary','jane','laurel','MD',30000,'33 downtown dr',2222222222,'121212');

INSERT INTO reservation (patronid, branchbookid, branchcdid, branchdvdid, forbranchid, date, fulfilled)
       VALUES (1, 1, null, null, 2, '2016-06-01 08:00:00',true),
              (2,2,null,null, 1,'2016-09-20 08:30:00',false),
              (1,null,2,null,1, '2016-09-21 08:45:00', false),
              (1,null,null,2,1, '2016-09-24 09:00:00', false);

INSERT INTO checkout(patronid, checkoutdate, numberofitems, overdue, itemsreturned)
       VALUES (1, '2016-09-15 08:00:00',3, false, false),
              (2, '2016-09-16 08:15:00',3,false,false),
              (1, '2016-06-03 08:30:00',1,true,true);

INSERT INTO bookcheckout(branchbookid, checkoutid, overdue, duedate, returned, returndate)
       VALUES (2,1,false, '2016-10-15',false,null),
              (3,1,false,'2016-10-15',false,null),
              (1,3,true, '2016-07-03',true,'2016-07-04');

INSERT INTO cdcheckout(branchcdid, checkoutid, overdue, duedate, returned)
       VALUES (2,2,false, '2016-10-16',false),
              (3,2,false, '2016-10-16',false);

INSERT INTO dvdcheckout(branchdvdid, checkoutid, overdue, duedate, returned)  
       VALUES (2,1,false,'2016-10-15',false),
              (3,2,false,'2016-10-16',false);

INSERT INTO transfer(tobranchid, frombranchid, reservationid, patronid, branchbookid, branchcdid, branchdvdid, transferdate)
       VALUES (2,1,1,1,1,null,null, '2016-06-01 10:00:00'),
              (1,2,1,1,1,null,null, '2016-07-05 10:00:00');

INSERT INTO fine(patronid, checkoutid, datepaid, amountpaid, paid)
       VALUES (1,3,'2016-07-10 14:00:00',1.00, true);
 
