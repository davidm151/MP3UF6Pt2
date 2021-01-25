CREATE DATABASE equip;

USE equip;

CREATE TABLE jugador(ID int AUTO_INCREMENT PRIMARY KEY,nom varchar(255) UNIQUE,equip varchar(255),posicio varchar(255), gols int,partits_jugats int, id_equip int);

CREATE TABLE equip( nom varchar(255) UNIQUE,gols_en_contra int,gols_afavor int,partits_guanyats int,partits_perduts int,partits_empatats int,punts int,jornada int,ID int AUTO_INCREMENT PRIMARY KEY);
