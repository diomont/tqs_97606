CREATE TABLE books (
  id BIGSERIAL PRIMARY KEY,
  name varchar(255) not null,
  author varchar(255) not null
);

INSERT INTO books (name, author) VALUES ('Livro do Desassossego', 'Bernardo Soares');
INSERT INTO books (name, author) VALUES ('Os Maias', 'Eça de Quirós');
INSERT INTO books (name, author) VALUES ('Os Lusíadas', 'Luís de Camões');
