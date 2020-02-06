BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "trening" (
	"trening_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"dan"	TEXT NOT NULL,
	"pocetak"	TEXT NOT NULL,
	"kraj"	TEXT NOT NULL,
	"objekat_id"	INTEGER NOT NULL,
	FOREIGN KEY("objekat_id") REFERENCES "objekat"("objekat_id")
);
CREATE TABLE IF NOT EXISTS "objekat_disciplina" (
	"objekat_id"	INTEGER,
	"disciplina_id"	INTEGER,
	FOREIGN KEY("objekat_id") REFERENCES "objekat"("objekat_id"),
	FOREIGN KEY("disciplina_id") REFERENCES "disciplina"("disciplina_id")
);
CREATE TABLE IF NOT EXISTS "obavijest" (
	"obavijest_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"tekst"	TEXT,
	"korisnik_id"	INTEGER,
	FOREIGN KEY("korisnik_id") REFERENCES "korisnik"("korisnik_id")
);
CREATE TABLE IF NOT EXISTS "korisnik_aktivnost" (
	"korisnik_id"	INTEGER,
	"aktivnost_id"	INTEGER,
	FOREIGN KEY("korisnik_id") REFERENCES "korisnik"("korisnik_id"),
	FOREIGN KEY("aktivnost_id") REFERENCES "aktivnost"("aktivnost_id")
);
CREATE TABLE IF NOT EXISTS "korisnik" (
	"korisnik_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"osoba_id"	INTEGER,
	"pocetak_clanarine"	TEXT,
	"kraj_clanarine"	TEXT,
	"ukupno_termina"	INTEGER,
	"iskoristeno_termina"	INTEGER,
	"trening_id"	INTEGER,
	FOREIGN KEY("osoba_id") REFERENCES "osoba"("osoba_id"),
	FOREIGN KEY("trening_id") REFERENCES "trening"("trening_id")
);
CREATE TABLE IF NOT EXISTS "admin" (
	"admin_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"osoba_id"	INTEGER NOT NULL,
	FOREIGN KEY("osoba_id") REFERENCES "osoba"("osoba_id")
);
CREATE TABLE IF NOT EXISTS "osoba" (
	"osoba_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ime"	TEXT,
	"prezime"	TEXT,
	"username"	TEXT UNIQUE,
	"password"	TEXT,
	"tip_osobe"	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS "objekat" (
	"objekat_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"naziv"	TEXT,
	"opcina"	TEXT,
	"adresa"	TEXT
);
CREATE TABLE IF NOT EXISTS "disciplina" (
	"disciplina_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"naziv"	TEXT
);
CREATE TABLE IF NOT EXISTS "aktivnost" (
	"aktivnost_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"mjesec"	TEXT,
	"godina"	INTEGER
);
CREATE TABLE IF NOT EXISTS "ocjena" (
	"ocjena_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"iznos"	INTEGER
);
CREATE TABLE IF NOT EXISTS "objekat_ocjena" (
	"objekat_id"	INTEGER,
	"ocjena_id"	INTEGER,
	FOREIGN KEY("objekat_id") REFERENCES "objekat"("objekat_id"),
	FOREIGN KEY("ocjena_id") REFERENCES "ocjena"("ocjena_id")
);
INSERT INTO "trening" VALUES (1,'ponedjeljak','9:00','10:00',1);
INSERT INTO "trening" VALUES (2,'ponedjeljak','12:00','13:00',1);
INSERT INTO "trening" VALUES (3,'utorak','10:00','11:00',1);
INSERT INTO "trening" VALUES (4,'utorak','14:00','15:00',1);
INSERT INTO "trening" VALUES (5,'cetvrtak','9:00','10:00',1);
INSERT INTO "trening" VALUES (6,'petak','11:00','12:00',1);
INSERT INTO "trening" VALUES (7,'petak','13:00','14:00',1);
INSERT INTO "trening" VALUES (8,'subota','9:00','10:00',1);
INSERT INTO "trening" VALUES (9,'ponedjeljak','10:00','11:00',2);
INSERT INTO "trening" VALUES (10,'ponedjeljak','13:00','14:00',2);
INSERT INTO "trening" VALUES (11,'utorak','10:00','11:00',2);
INSERT INTO "trening" VALUES (12,'utorak','16:00','17:00',2);
INSERT INTO "trening" VALUES (13,'srijeda','15:00','16:00',2);
INSERT INTO "trening" VALUES (14,'petak','10:00','11:00',2);
INSERT INTO "trening" VALUES (15,'petak','16:00','17:00',2);
INSERT INTO "trening" VALUES (16,'nedjelja','10:00','11:00',2);
INSERT INTO "trening" VALUES (17,'nedjelja','11:00','12:00',2);
INSERT INTO "trening" VALUES (18,'ponedjeljak','9:00','10:00',3);
INSERT INTO "trening" VALUES (19,'utorak','14:00','15:00',3);
INSERT INTO "trening" VALUES (20,'utorak','18:00','19:00',3);
INSERT INTO "trening" VALUES (21,'srijeda','10:00','11:00',3);
INSERT INTO "trening" VALUES (22,'cetvrtak','19:00','20:00',3);
INSERT INTO "trening" VALUES (23,'petak','13:00','14:00',3);
INSERT INTO "trening" VALUES (24,'nedjelja','9:00','10:00',3);
INSERT INTO "trening" VALUES (25,'ponedjeljak','13:00','14:00',4);
INSERT INTO "trening" VALUES (26,'ponedjeljak','16:00','17:00',4);
INSERT INTO "trening" VALUES (27,'utorak','12:00','13:00',4);
INSERT INTO "trening" VALUES (28,'utorak','17:00','18:00',4);
INSERT INTO "trening" VALUES (29,'srijeda','9:00','10:00',4);
INSERT INTO "trening" VALUES (30,'cetvrtak','11:00','12:00',4);
INSERT INTO "trening" VALUES (31,'cetvrtak','18:00','19:00',4);
INSERT INTO "trening" VALUES (32,'petak','9:00','10:00',4);
INSERT INTO "trening" VALUES (33,'subota','11:00','12:00',4);
INSERT INTO "objekat_disciplina" VALUES (1,1);
INSERT INTO "objekat_disciplina" VALUES (2,1);
INSERT INTO "objekat_disciplina" VALUES (1,2);
INSERT INTO "objekat_disciplina" VALUES (3,2);
INSERT INTO "objekat_disciplina" VALUES (4,2);
INSERT INTO "objekat_disciplina" VALUES (4,3);
INSERT INTO "objekat_disciplina" VALUES (1,3);
INSERT INTO "objekat_disciplina" VALUES (4,4);
INSERT INTO "obavijest" VALUES (1,'Vaša članarina je evidentirana 18.12.2019.',2);
INSERT INTO "obavijest" VALUES (2,'Vaša članarina je evidentirana 7.1.2020.',3);
INSERT INTO "obavijest" VALUES (3,'Vaša članarina je evidentirana 14.1.2020.',4);
INSERT INTO "obavijest" VALUES (4,'Vaša članarina je evidentirana 23.1.2020.',5);
INSERT INTO "korisnik_aktivnost" VALUES (1,1);
INSERT INTO "korisnik_aktivnost" VALUES (1,2);
INSERT INTO "korisnik_aktivnost" VALUES (2,2);
INSERT INTO "korisnik_aktivnost" VALUES (3,1);
INSERT INTO "korisnik_aktivnost" VALUES (3,5);
INSERT INTO "korisnik_aktivnost" VALUES (4,3);
INSERT INTO "korisnik_aktivnost" VALUES (5,3);
INSERT INTO "korisnik" VALUES (1,2,'1.1.2020.','1.2.2020.',12,7,1);
INSERT INTO "korisnik" VALUES (2,3,'18.12.2019.','18.1.2020.',16,4,4);
INSERT INTO "korisnik" VALUES (3,4,'7.1.2020.','7.2.2020.',20,10,5);
INSERT INTO "korisnik" VALUES (4,5,'14.1.2020.','14.2.2020.',16,5,6);
INSERT INTO "korisnik" VALUES (5,6,'23.1.2020.','23.2.2020.',12,8,8);
INSERT INTO "admin" VALUES (1,1);
INSERT INTO "osoba" VALUES (1,'Nađa','Sokolović','nsokolovic','trening11','admin');
INSERT INTO "osoba" VALUES (2,'Omar','Sokolović','osokolovic','trening123','korisnik');
INSERT INTO "osoba" VALUES (3,'Mubina','Kamberović','mubinaK','mubina121','korisnik');
INSERT INTO "osoba" VALUES (4,'Sabina','Alagić','alagicS','sabinaA','korisnik');
INSERT INTO "osoba" VALUES (5,'Ajla','Salispahić','ajlaaS','ajlas123','korisnik');
INSERT INTO "osoba" VALUES (6,'Rea','Memić','reamemic','reamem11','korisnik');
INSERT INTO "objekat" VALUES (1,'Sportska Akademija Respect','Sarajevo - Centar','Alipašina bb');
INSERT INTO "objekat" VALUES (2,'Olimpijski stadion Koševo','Sarajevo - Centar','Patriotske lige 35');
INSERT INTO "objekat" VALUES (3,'Fitness centar Progym','Sarajevo - Centar','Patriotske lige bb');
INSERT INTO "objekat" VALUES (4,'ALL IN Fitness','Novo Sarajevo','Paromlinska 34');
INSERT INTO "disciplina" VALUES (1,'CrossFit');
INSERT INTO "disciplina" VALUES (2,'Fitness');
INSERT INTO "disciplina" VALUES (3,'Borilacke vjestine');
INSERT INTO "disciplina" VALUES (4,'Aerobik');
INSERT INTO "aktivnost" VALUES (1,'Novembar',2019);
INSERT INTO "aktivnost" VALUES (2,'Decembar',2019);
INSERT INTO "aktivnost" VALUES (3,'Januar',2020);
INSERT INTO "aktivnost" VALUES (4,'Februar',2020);
INSERT INTO "aktivnost" VALUES (5,'Oktobar',2019);
INSERT INTO "ocjena" VALUES (1,1);
INSERT INTO "ocjena" VALUES (2,2);
INSERT INTO "ocjena" VALUES (3,3);
INSERT INTO "ocjena" VALUES (4,4);
INSERT INTO "ocjena" VALUES (5,5);
INSERT INTO "objekat_ocjena" VALUES (1,3);
INSERT INTO "objekat_ocjena" VALUES (1,4);
INSERT INTO "objekat_ocjena" VALUES (1,5);
INSERT INTO "objekat_ocjena" VALUES (1,4);
INSERT INTO "objekat_ocjena" VALUES (2,3);
INSERT INTO "objekat_ocjena" VALUES (2,4);
INSERT INTO "objekat_ocjena" VALUES (2,4);
INSERT INTO "objekat_ocjena" VALUES (3,5);
INSERT INTO "objekat_ocjena" VALUES (3,4);
INSERT INTO "objekat_ocjena" VALUES (3,4);
INSERT INTO "objekat_ocjena" VALUES (3,4);
INSERT INTO "objekat_ocjena" VALUES (4,4);
INSERT INTO "objekat_ocjena" VALUES (4,5);
INSERT INTO "objekat_ocjena" VALUES (4,5);
INSERT INTO "objekat_ocjena" VALUES (4,5);
COMMIT;
