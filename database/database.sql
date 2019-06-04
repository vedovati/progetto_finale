
CREATE TABLE medicinale (
  id_medicinale int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  descrizione TEXT NOT NULL,
  foglietto_illustrativo TEXT NOT NULL,
  img_path varchar(50) NOT NULL,
  PRIMARY KEY (id_medicinale)
);