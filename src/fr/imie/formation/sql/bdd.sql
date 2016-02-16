-- Création de la table livre
CREATE TABLE livre (
	livre_id serial,
	nom varchar(255),
	prenom varchar(255),
	date_parution date,
	constraint pk_livre primary key (livre_id)
);

-- Ajout d'un livre
INSERT INTO livre (nom, prenom, date_parution) VALUES ('NOM', 'PRENOM', '2012-03-04');