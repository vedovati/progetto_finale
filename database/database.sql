
CREATE TABLE medicinale (
  id_medicinale int NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL unique,
  descrizione TEXT NOT NULL,
  foglietto_illustrativo TEXT NOT NULL,
  img_path varchar(50) NOT NULL,
  PRIMARY KEY (id_medicinale)
);
