CREATE DATABASE PROJECTE;

USE PROJECTE;

CREATE TABLE Jugador(nom_cognoms varchar(255) UNIQUE KEY,equip varchar(255),posicio varchar(255), gols int,partits_jugats int);

CREATE TABLE Equip(nom varchar(255) PRIMARY KEY,partits_guanyats int,partits_perduts int,partits_empatats int,punts int,jornada int);
