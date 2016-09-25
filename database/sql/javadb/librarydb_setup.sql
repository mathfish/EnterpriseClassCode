connect 'jdbc:derby:memory:librarydb;create=true';

CREATE TABLE branch(
		    branchid INT NOT NULL 
                             PRIMARY KEY 
                             GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                    name varchar(50) NOT NULL,
                    city varchar(50) NOT NULL,
                    zipCode INT NOT NULL CONSTRAINT branch_zipcode_not_5_digits CHECK(zipcode > 9999 AND zipcode < 100000),
                    streetaddress varchar(150) NOT NULL,
                    state VARCHAR(2) NOT NULL CONSTRAINT branch_state_not_2_chars CHECK(LENGTH(state)=2)
		   );      

CREATE TABLE author(
		   authorid INT NOT NULL
			    PRIMARY KEY
			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
		   firstname VARCHAR(50) NOT NULL, 
                   lastname VARCHAR(50) NOT NULL,
                   country VARCHAR(50)
                   );

CREATE TABLE artist(
                   artistid INT NOT NULL 
                            PRIMARY KEY
	                    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                   firstname VARCHAR(50),
                   lastname VARCHAR(50)
                   );

CREATE TABLE band(
                 bandid INT NOT NULL
                        PRIMARY KEY
			GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                 name VARCHAR(50) NOT NULL,
                 country VARCHAR(50)
                 );

CREATE TABLE thespian(
                     thespianid INT NOT NULL
                                 PRIMARY KEY
			         GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                     firstname VARCHAR(50) NOT NULL, 
                     lastname VARCHAR(50) NOT NULL,
                     country VARCHAR(50)
                     );

CREATE TABLE BOOK(
                  bookid INT NOT NULL
                          PRIMARY KEY
			  GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                  title VARCHAR(100) NOT NULL,
                  bookyear INT NOT NULL CONSTRAINT book_year_not_4_digits CHECK(bookyear > 999 AND bookyear <10000),
                  description VARCHAR(255)
                  );

CREATE TABLE bookbyauthor(
                         authorid INT NOT NULL,
                         bookid INT NOT NULL,
                         PRIMARY KEY(authorid, bookid),
                         CONSTRAINT fk_authorid FOREIGN KEY(authorid) REFERENCES author(authorid),
                         CONSTRAINT fk_bookid FOREIGN KEY(bookid) REFERENCES book(bookid)
                         );

CREATE TABLE cd(
                cdid INT NOT NULL
                PRIMARY KEY
                GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                title VARCHAR(50) NOT NULL,
                numoftracks INT NOT NULL,
                cdyear INT NOT NULL CONSTRAINT cd_year_not_4_digits CHECK(cdyear > 999 AND cdyear < 10000)
                );

CREATE TABLE cdbyartistorband(
                             cdartistbandid INT NOT NULL 
                                            PRIMARY KEY
                                            GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                             artistid INT,
                             bandid INT,
                             cdid INT NOT NULL,
                             CONSTRAINT artist_or_band_id_not_null CHECK(artistid IS NOT NULL OR bandid IS NOT NULL),
                             CONSTRAINT fk_bandid FOREIGN KEY(bandid) REFERENCES band(bandid),
                             CONSTRAINT fk_artistid FOREIGN KEY(artistid) REFERENCES artist(artistid),
                             CONSTRAINT fk_cdid FOREIGN KEY(cdid) REFERENCES cd(cdid)
                             );
 
CREATE TABLE dvd(
                dvdid INT NOT NULL
                PRIMARY KEY
                GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                title VARCHAR(50) NOT NULL,
                filmyear INT NOT NULL CONSTRAINT dvd_year_not_4_digits CHECK(filmyear > 999 AND filmyear < 10000),         
                rating VARCHAR(4) NOT NULL CONSTRAINT rating_not_correct CHECK(rating in ('G','PG','PG13','R','NR')),
                timelength TIME
                );
                
CREATE TABLE dvdbythespian(
                          dvdid INT NOT NULL,
                          thespianid INT NOT NULL,
                          PRIMARY KEY(dvdid, thespianid),
                          CONSTRAINT fk_dvdid FOREIGN KEY(dvdid) REFERENCES dvd(dvdid),
                          CONSTRAINT fk_thespianid FOREIGN KEY(thespianid) REFERENCES thespian(thespianid)
                          );

CREATE TABLE branchbook(
                        branchbookid INT NOT NULL 
                        PRIMARY KEY
                        GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                        bookid INT NOT NULL,
                        branchid INT NOT NULL,
                        checkedout BOOLEAN DEFAULT false,
                        reserved BOOLEAN DEFAULT false,
                        intransit BOOLEAN DEFAULT false,
                        currentlocation INT NOT NULL,
                        CONSTRAINT fk_branchbook_book FOREIGN KEY(bookid) REFERENCES book(bookid),
                        CONSTRAINT fk_branchbook_branch FOREIGN KEY(branchid) REFERENCES branch(branchid),
                        CONSTRAINT fk_branchbook_currentlocation FOREIGN KEY(currentlocation) REFERENCES branch(branchid)
                        );


CREATE TABLE branchcd(
                        branchcdid INT NOT NULL 
                        PRIMARY KEY
                        GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                        cdid INT NOT NULL,
                        branchid INT NOT NULL,
                        checkedout BOOLEAN DEFAULT false,
                        reserved BOOLEAN DEFAULT false,
                        intransit BOOLEAN DEFAULT false,
                        currentlocation INT NOT NULL,
                        CONSTRAINT fk_branchcd_cd FOREIGN KEY(cdid) REFERENCES cd(cdid),
                        CONSTRAINT fk_branchcd_branch FOREIGN KEY(branchid) REFERENCES branch(branchid),
                        CONSTRAINT fk_branchcd_currentlocation FOREIGN KEY(currentlocation) REFERENCES branch(branchid)
                        );


CREATE TABLE branchdvd(
                        branchdvdid INT NOT NULL 
                        PRIMARY KEY
                        GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                        dvdid INT NOT NULL,
                        branchid INT NOT NULL,
                        checkedout BOOLEAN DEFAULT false,
                        reserved BOOLEAN DEFAULT false,
                        intransit BOOLEAN DEFAULT false,
                        currentlocation INT NOT NULL,
                        CONSTRAINT fk_branchdvd_dvd FOREIGN KEY(dvdid) REFERENCES dvd(dvdid),
                        CONSTRAINT fk_branchdvd_branch FOREIGN KEY(branchid) REFERENCES branch(branchid),
                        CONSTRAINT fk_branchdvd_currentlocation FOREIGN KEY(currentlocation) REFERENCES branch(branchid)
                        );

CREATE TABLE patron(
                     patronid INT NOT NULL 
                     PRIMARY KEY
                     GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                     firstname VARCHAR(50) NOT NULL,
                     lastname VARCHAR(50) NOT NULL,
                     city VARCHAR(50) NOT NULL,
                     state VARCHAR(2) NOT NULL CONSTRAINT patron_state_not_2_chars CHECK(LENGTH(state)=2),
                     zipcode INT NOT NULL CONSTRAINT patron_zipcode_not_5_digits CHECK(zipcode > 9999 AND zipcode < 100000),
                     streetaddress VARCHAR(150) NOT NULL,
                     joindate timestamp DEFAULT CURRENT_TIMESTAMP,
                     phone BIGINT NOT NULL CONSTRAINT phone_not_10_digits CHECK(phone > 999999999 AND phone < 10000000000),
                     password VARCHAR(10) NOT NULL,
                     remoteLibrary BOOLEAN DEFAULT false
                    );

CREATE TABLE reservation(
                        reservationid INT NOT NULL
                        PRIMARY KEY
                        GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                        patronid INT NOT NULL,
                        branchbookid INT,
                        branchcdid INT,
                        branchdvdid INT,
                        forbranchid INT NOT NULL,
                        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        fulfilled BOOLEAN DEFAULT false,
                        CONSTRAINT fk_reservation_patron FOREIGN KEY(patronid) REFERENCES patron(patronid),
                        CONSTRAINT fk_reservation_branchbook FOREIGN KEY(branchbookid) REFERENCES branchbook(branchbookid),
                        CONSTRAINT fk_reservation_branchcd FOREIGN KEY(branchcdid) REFERENCES branchcd(branchcdid),
                        CONSTRAINT fk_reservation_branchdvd FOREIGN KEY(branchdvdid) REFERENCES branchdvd(branchdvdid),
                        CONSTRAINT branchcd_branchbook_or_branchdvd_notnull CHECK(branchbookid IS NOT NULL OR branchcdid IS NOT NULL 
                            OR branchdvdid IS NOT NULL),
                        CONSTRAINT fk_reservation_forbranch FOREIGN KEY(forbranchid) REFERENCES branch(branchid)
                        );

CREATE TABLE checkout(
                     checkoutid INT NOT NULL
                     PRIMARY KEY
                     GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                     patronid INT NOT NULL,
                     checkoutdate timestamp DEFAULT CURRENT_TIMESTAMP,
                     numberofitems INT NOT NULL,
                     overdue BOOLEAN DEFAULT false,
                     itemsreturned BOOLEAN DEFAULT false,
                     CONSTRAINT fk_checkout_patron FOREIGN KEY(patronid) REFERENCES patron(patronid)
                     );

CREATE TABLE bookcheckout(
                         branchbookid INT NOT NULL,
                         checkoutid INT NOT NULL,
                         overdue BOOLEAN DEFAULT false,
                         duedate DATE NOT NULL,
                         renew BOOLEAN DEFAULT false,
                         renewdate DATE,
                         returned BOOLEAN DEFAULT false,
                         returndate DATE,
                         PRIMARY KEY(branchbookid, checkoutid),
                         CONSTRAINT fk_bookcheckout_branchbook FOREIGN KEY(branchbookid) REFERENCES branchbook(branchbookid),
                         CONSTRAINT fk_bookcheckout_checkout FOREIGN KEY(checkoutid) REFERENCES checkout(checkoutid)
                         );

CREATE TABLE cdcheckout(
                         branchcdid INT NOT NULL,
                         checkoutid INT NOT NULL,
                         overdue BOOLEAN DEFAULT false,
                         duedate DATE NOT NULL,
                         renew BOOLEAN DEFAULT false,
                         renewdate DATE,
                         returned BOOLEAN DEFAULT false,
                         returndate DATE,
                         PRIMARY KEY(branchcdid, checkoutid),
                         CONSTRAINT fk_cdcheckout_branchcd FOREIGN KEY(branchcdid) REFERENCES branchcd(branchcdid),
                         CONSTRAINT fk_cdcheckout_checkout FOREIGN KEY(checkoutid) REFERENCES checkout(checkoutid)
                         );

CREATE TABLE dvdcheckout(
                         branchdvdid INT NOT NULL,
                         checkoutid INT NOT NULL,
                         overdue BOOLEAN DEFAULT false,
                         duedate DATE NOT NULL,
                         renew BOOLEAN DEFAULT false,
                         renewdate DATE,
                         returned BOOLEAN DEFAULT false,
                         returndate DATE,
                         PRIMARY KEY(branchdvdid, checkoutid),
                         CONSTRAINT fk_dvdcheckout_branchdvd FOREIGN KEY(branchdvdid) REFERENCES branchdvd(branchdvdid),
                         CONSTRAINT fk_dvdcheckout_checkout FOREIGN KEY(checkoutid) REFERENCES checkout(checkoutid)
                         );

CREATE TABLE transfer(
                     transferid INT NOT NULL
                                PRIMARY KEY
                                GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                     tobranchid INT NOT NULL,
                     frombranchid INT NOT NULL,
                     reservationid INT NOT NULL,
                     patronid INT NOT NULL,
                     branchbookid INT,
                     branchcdid INT,
                     branchdvdid INT,
                     transferdate timestamp NOT NULL,
                     CONSTRAINT transfer_tobranch FOREIGN KEY(tobranchid) REFERENCES branch(branchid),
                     CONSTRAINT transfer_frombranch FOREIGN KEY(frombranchid) REFERENCES branch(branchid),
                     CONSTRAINT transfer_reservation FOREIGN KEY(reservationid) REFERENCES reservation(reservationid),
                     CONSTRAINT transfer_patron FOREIGN KEY(patronid) REFERENCES patron(patronid),
                     CONSTRAINT transfer_branchbook FOREIGN KEY(branchbookid) REFERENCES branchbook(branchbookid),
                     CONSTRAINT transfer_branchcd FOREIGN KEY(branchcdid) REFERENCES branchcd(branchcdid),
                     CONSTRAINT transfer_branchdvd FOREIGN KEY(branchdvdid) REFERENCES branchdvd(branchdvdid),
                     CONSTRAINT transfer_branchcd_branchbook_or_branchdvd_notnull 
                          CHECK(branchbookid IS NOT NULL OR branchcdid IS NOT NULL OR branchdvdid IS NOT NULL)
                     );

CREATE TABLE fine(
                 fineid INT NOT NULL
                            PRIMARY KEY
                            GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
                 patronid INT NOT NULL,
                 checkoutid INT NOT NULL,
                 datepaid timestamp,
                 amountpaid DECIMAL(10,2),
                 paid BOOLEAN DEFAULT false,
                 CONSTRAINT fine_patronid FOREIGN KEY(patronid) REFERENCES patron(patronid),
                 CONSTRAINT fine_checkoutid FOREIGN KEY(checkoutid) REFERENCES checkout(checkoutid)
                 );

