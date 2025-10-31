-- Insertar los datos
INSERT INTO equipos (id, nombre, liga, pais) VALUES
(1, 'Real Madrid', 'La Liga', 'España'),
(2, 'FC Barcelona', 'La Liga', 'España'),
(3, 'Manchester United', 'Premier League', 'Inglaterra'),
(4, 'Liverpool FC', 'Premier League', 'Inglaterra'),
(5, 'Juventus FC', 'Serie A', 'Italia'),
(6, 'AC Milan', 'Serie A', 'Italia'),
(7, 'Bayern Munich', 'Bundesliga', 'Alemania'),
(8, 'Borussia Dortmund', 'Bundesliga', 'Alemania'),
(9, 'Paris Saint-Germain', 'Ligue 1', 'Francia'),
(10, 'Olympique de Marseille', 'Ligue 1', 'Francia'),
(11, 'FC Porto', 'Primera Liga', 'Portugal'),
(12, 'Sporting CP', 'Primera Liga', 'Portugal'),
(13, 'Ajax Amsterdam', 'Eredivisie', 'Países Bajos'),
(14, 'Feyenoord', 'Eredivisie', 'Países Bajos'),
(15, 'Celtic FC', 'Scottish Premiership', 'Escocia'),
(16, 'Rangers FC', 'Scottish Premiership', 'Escocia'),
(17, 'Galatasaray SK', 'Süper Lig', 'Turquía'),
(18, 'Fenerbahçe SK', 'Süper Lig', 'Turquía'),
(19, 'FC Zenit Saint Petersburg', 'Premier League Rusa', 'Rusia'),
(20, 'Spartak Moscow', 'Premier League Rusa', 'Rusia'),
(21, 'SL Benfica', 'Primera Liga', 'Portugal'),
(22, 'Besiktas JK', 'Süper Lig', 'Turquía'),
(23, 'SSC Napoli', 'Serie A', 'Italia'),
(24, 'Atlético Madrid', 'La Liga', 'España');

ALTER TABLE EQUIPOS ALTER COLUMN ID RESTART WITH 25;

INSERT INTO users (id, username, password, nombre, apellido, pais, role) VALUES
(1, 'test', '$2a$10$LjDdUvmBf4g/KkQNq1CVc.4wRYumGoVrlw7ZXoGu/GXA7iaksGpfG', 'hernan', 'arias', 'argentina', 'USER');

ALTER TABLE users ALTER COLUMN ID RESTART WITH 2;