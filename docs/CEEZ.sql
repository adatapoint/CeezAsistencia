DROP DATABASE scaceez_db;
CREATE DATABASE IF NOT EXISTS scaceez_db  CHARACTER SET utf8 COLLATE utf8_spanish2_ci;
USE scaceez_db;

CREATE TABLE Departamento(
DEP_Codigo INT(4) PRIMARY KEY, 
DEP_Nombre VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Municipio(
MUN_Codigo INT(4) PRIMARY KEY, 
MUN_Nombre VARCHAR(20) NOT NULL, 
MUN_DEP_Codigo INT(4) NOT NULL,
FOREIGN KEY (MUN_DEP_Codigo) REFERENCES Departamento(DEP_Codigo)
);

CREATE TABLE Barrio(
BAR_Codigo INT(4) PRIMARY KEY, 
BAR_Nombre VARCHAR(20) NOT NULL, 
BAR_MUN_Codigo INT(4) NOT NULL,
FOREIGN KEY (BAR_MUN_Codigo) REFERENCES Municipio(MUN_Codigo)
);

CREATE TABLE Ocupacion(
OCU_Codigo INT(4) PRIMARY KEY, 
OCU_Nombre VARCHAR(20) NOT NULL
);

CREATE TABLE Genero(
GEN_Codigo INT(1) PRIMARY KEY, 
GEN_Descripcion VARCHAR(20) NOT NULL
);

CREATE TABLE Edad(
EDA_Codigo INT(1) PRIMARY KEY, 
EDA_Descripcion VARCHAR(20) NOT NULL,
EDA_EdadInicio INT(3) NOT NULL,
EDA_EdadFin INT(3)
);

CREATE TABLE Usuario(
USR_Codigo INT(15) PRIMARY KEY, 
USR_Nombre1 VARCHAR(20) NOT NULL,
USR_Nombre2 VARCHAR(20),
USR_Apellido1 VARCHAR(20) NOT NULL,
USR_Apellido2 VARCHAR(20), 
USR_Telefono VARCHAR(20) NOT NULL,
USR_Correo VARCHAR(30),
USR_GEN_Codigo INT(1) NOT NULL,
USR_EDA_Codigo INT(1),
USR_OCU_Codigo INT(4),
USR_BAR_Codigo INT(4),
USR_MUN_Codigo INT(4),
USR_FechaIngreso DATE NOT NULL,	
FOREIGN KEY (USR_EDA_Codigo) REFERENCES Edad(EDA_Codigo),
FOREIGN KEY (USR_GEN_Codigo) REFERENCES Genero(GEN_Codigo),
FOREIGN KEY (USR_OCU_Codigo) REFERENCES Ocupacion(OCU_Codigo),
FOREIGN KEY (USR_MUN_Codigo) REFERENCES Municipio(MUN_Codigo),
FOREIGN KEY (USR_BAR_Codigo) REFERENCES Barrio(BAR_Codigo)
);

CREATE TABLE Entidad(
ENT_Codigo INT(15) PRIMARY KEY, 
ENT_Nombre VARCHAR(50) NOT NULL,
ENT_Sigla VARCHAR(10), 
ENT_Telefono VARCHAR(20),
ENT_Correo VARCHAR(30),
ENT_Web VARCHAR(50),
ENT_Direccion VARCHAR(100) NOT NULL,
ENT_USR_Codigo INT(15),
FOREIGN KEY (ENT_USR_Codigo) REFERENCES Usuario(USR_Codigo)
);

CREATE TABLE Estado(
EST_Codigo INT(1) PRIMARY KEY, 
EST_Descripcion VARCHAR(20) NOT NULL
);

CREATE TABLE Proyecto(
PRO_Codigo INT(4) PRIMARY KEY AUTO_INCREMENT, 
PRO_Nombre VARCHAR(50) NOT NULL, 
PRO_Descripcion VARCHAR(200) NOT NULL,
PRO_FechaInicio DATE,
PRO_FechaFin DATE,
PRO_EST_Codigo INT(1),
FOREIGN KEY (PRO_EST_Codigo) REFERENCES Estado(EST_Codigo)
);

CREATE TABLE Alianza(
ALI_Codigo INT(4) PRIMARY KEY AUTO_INCREMENT, 
ALI_ENT_Codigo INT(15) NOT NULL, 
ALI_PRO_Codigo INT(4) NOT NULL,
ALI_FechaIni DATE,
ALI_FechaFin DATE,
FOREIGN KEY (ALI_ENT_Codigo) REFERENCES Entidad(ENT_Codigo),
FOREIGN KEY (ALI_PRO_Codigo) REFERENCES Proyecto(PRO_Codigo)
);

CREATE TABLE Ciclo(
CIC_Codigo INT(4) PRIMARY KEY AUTO_INCREMENT, 
CIC_PRO_Codigo INT(4) NOT NULL,
CIC_Nombre VARCHAR(50) NOT NULL,
CIC_FechaInicio DATE,
CIC_FechaFin DATE,
CIC_Descripcion VARCHAR(200) NOT NULL,
FOREIGN KEY (CIC_PRO_Codigo) REFERENCES Proyecto(PRO_Codigo)
);

CREATE TABLE Sesion(
SES_Codigo INT(4) PRIMARY KEY AUTO_INCREMENT, 
SES_Nombre VARCHAR(50) NOT NULL,
SES_Fecha DATE NOT NULL,
SES_Lugar VARCHAR(50) NOT NULL,
SES_Observacion VARCHAR(1000) NOT NULL,
SES_CIC_Codigo INT(4) NOT NULL,
SES_TSES_Codigo INT(4) NOT NULL,
FOREIGN KEY (SES_CIC_Codigo) REFERENCES Ciclo(CIC_Codigo),
FOREIGN KEY (SES_TSES_Codigo) REFERENCES TipoSesion(TSES_Codigo)
);

CREATE TABLE Asistencia(
ASI_USR_Codigo INT(15),
ASI_SES_Codigo INT(4),
PRIMARY KEY(ASI_USR_Codigo,ASI_SES_Codigo),
FOREIGN KEY (ASI_USR_Codigo) REFERENCES Usuario(USR_Codigo),
FOREIGN KEY (ASI_SES_Codigo) REFERENCES Sesion(SES_Codigo)
);

CREATE TABLE TipoSesion(
TSES_Codigo INT(15) PRIMARY KEY AUTO_INCREMENT,
TSES_Descripcion VARCHAR(50) NOT NULL
);

INSERT INTO Departamento VALUES (00, 'Desconocido');
INSERT INTO Departamento VALUES (05, 'Antioquia');

INSERT INTO Municipio VALUES (000,'Desconocido',0);
INSERT INTO Municipio VALUES (001,'Medellín',5);
INSERT INTO Municipio VALUES (002,'Abejorral',5);
INSERT INTO Municipio VALUES (004,'Abriaquí',5);
INSERT INTO Municipio VALUES (021,'Alejandría',5);
INSERT INTO Municipio VALUES (030,'Amagá',5);
INSERT INTO Municipio VALUES (031,'Amalfi',5);
INSERT INTO Municipio VALUES (034,'Andes',5);
INSERT INTO Municipio VALUES (036,'Angelópolis',5);
INSERT INTO Municipio VALUES (038,'Angostura',5);
INSERT INTO Municipio VALUES (040,'Anorí',5);
INSERT INTO Municipio VALUES (042,'Santafé De Antioquia',5);
INSERT INTO Municipio VALUES (044,'Anza',5);
INSERT INTO Municipio VALUES (045,'Apartadó',5);
INSERT INTO Municipio VALUES (051,'Arboletes',5);
INSERT INTO Municipio VALUES (055,'Argelia',5);
INSERT INTO Municipio VALUES (059,'Armenia',5);
INSERT INTO Municipio VALUES (079,'Barbosa',5);
INSERT INTO Municipio VALUES (086,'Belmira',5);
INSERT INTO Municipio VALUES (088,'Bello',5);
INSERT INTO Municipio VALUES (091,'Betania',5);
INSERT INTO Municipio VALUES (093,'Betulia',5);
INSERT INTO Municipio VALUES (101,'Ciudad Bolívar',5);
INSERT INTO Municipio VALUES (107,'Briceño',5);
INSERT INTO Municipio VALUES (113,'Buriticá',5);
INSERT INTO Municipio VALUES (120,'Cáceres',5);
INSERT INTO Municipio VALUES (125,'Caicedo',5);
INSERT INTO Municipio VALUES (129,'Caldas',5);
INSERT INTO Municipio VALUES (134,'Campamento',5);
INSERT INTO Municipio VALUES (138,'Cañasgordas',5);
INSERT INTO Municipio VALUES (142,'Caracolí',5);
INSERT INTO Municipio VALUES (145,'Caramanta',5);
INSERT INTO Municipio VALUES (147,'Carepa',5);
INSERT INTO Municipio VALUES (148,'El Carmen De Viboral',5);
INSERT INTO Municipio VALUES (150,'Carolina',5);
INSERT INTO Municipio VALUES (154,'Caucasia',5);
INSERT INTO Municipio VALUES (172,'Chigorodó',5);
INSERT INTO Municipio VALUES (190,'Cisneros',5);
INSERT INTO Municipio VALUES (197,'Cocorná',5);
INSERT INTO Municipio VALUES (206,'Concepcián',5);
INSERT INTO Municipio VALUES (209,'Concordia',5);
INSERT INTO Municipio VALUES (212,'Copacabana',5);
INSERT INTO Municipio VALUES (234,'Dabeiba',5);
INSERT INTO Municipio VALUES (237,'Don Matías',5);
INSERT INTO Municipio VALUES (240,'Ebéjico',5);
INSERT INTO Municipio VALUES (250,'El Bagre',5);
INSERT INTO Municipio VALUES (264,'Entrerríos',5);
INSERT INTO Municipio VALUES (266,'Envigado',5);
INSERT INTO Municipio VALUES (282,'Fredonia',5);
INSERT INTO Municipio VALUES (284,'Frontino',5);
INSERT INTO Municipio VALUES (306,'Giraldo',5);
INSERT INTO Municipio VALUES (308,'Girardota',5);
INSERT INTO Municipio VALUES (310,'Gómez Plata',5);
INSERT INTO Municipio VALUES (313,'Granada',5);
INSERT INTO Municipio VALUES (315,'Guadalupe',5);
INSERT INTO Municipio VALUES (318,'Guarne',5);
INSERT INTO Municipio VALUES (321,'Guatapé',5);
INSERT INTO Municipio VALUES (347,'Heliconia',5);
INSERT INTO Municipio VALUES (353,'Hispania',5);
INSERT INTO Municipio VALUES (360,'Itagüí',5);
INSERT INTO Municipio VALUES (361,'Ituango',5);
INSERT INTO Municipio VALUES (364,'Jardín',5);
INSERT INTO Municipio VALUES (368,'Jericó',5);
INSERT INTO Municipio VALUES (376,'La Ceja',5);
INSERT INTO Municipio VALUES (380,'La Estrella',5);
INSERT INTO Municipio VALUES (390,'La Pintada',5);
INSERT INTO Municipio VALUES (400,'La Unión',5);
INSERT INTO Municipio VALUES (411,'Liborina',5);
INSERT INTO Municipio VALUES (425,'Maceo',5);
INSERT INTO Municipio VALUES (440,'Marinilla',5);
INSERT INTO Municipio VALUES (467,'Montebello',5);
INSERT INTO Municipio VALUES (475,'Murindo',5);
INSERT INTO Municipio VALUES (480,'Mutatá',5);
INSERT INTO Municipio VALUES (483,'Nariño',5);
INSERT INTO Municipio VALUES (490,'Necoclí',5);
INSERT INTO Municipio VALUES (495,'Nechi',5);
INSERT INTO Municipio VALUES (501,'Olaya',5);
INSERT INTO Municipio VALUES (541,'Peñol',5);
INSERT INTO Municipio VALUES (543,'Peque',5);
INSERT INTO Municipio VALUES (576,'Pueblorrico',5);
INSERT INTO Municipio VALUES (579,'Puerto Berrío',5);
INSERT INTO Municipio VALUES (585,'Puerto Nare',5);
INSERT INTO Municipio VALUES (591,'Puerto Triunfo',5);
INSERT INTO Municipio VALUES (604,'Remedios',5);
INSERT INTO Municipio VALUES (607,'Retiro',5);
INSERT INTO Municipio VALUES (615,'Rionegro',5);
INSERT INTO Municipio VALUES (628,'Sabanalarga',5);
INSERT INTO Municipio VALUES (631,'Sabaneta',5);
INSERT INTO Municipio VALUES (642,'Salgar',5);
INSERT INTO Municipio VALUES (647,'San Andrés De Cuerquia',5);
INSERT INTO Municipio VALUES (649,'San Carlos',5);
INSERT INTO Municipio VALUES (652,'San Francisco',5);
INSERT INTO Municipio VALUES (656,'San Jerónimo',5);
INSERT INTO Municipio VALUES (658,'San José De La Montaña',5);
INSERT INTO Municipio VALUES (659,'San Juan De Urabá',5);
INSERT INTO Municipio VALUES (660,'San Luis',5);
INSERT INTO Municipio VALUES (664,'San Pedro',5);
INSERT INTO Municipio VALUES (665,'San Pedro De Urabá',5);
INSERT INTO Municipio VALUES (667,'San Rafael',5);
INSERT INTO Municipio VALUES (670,'San Roque',5);
INSERT INTO Municipio VALUES (674,'San Vicente',5);
INSERT INTO Municipio VALUES (679,'Santa Bárbara',5);
INSERT INTO Municipio VALUES (686,'Santa Rosa De Osos',5);
INSERT INTO Municipio VALUES (690,'Santo Domingo',5);
INSERT INTO Municipio VALUES (697,'El Santuario',5);
INSERT INTO Municipio VALUES (736,'Segovia',5);
INSERT INTO Municipio VALUES (756,'Sonsón',5);
INSERT INTO Municipio VALUES (761,'Sopetrán',5);
INSERT INTO Municipio VALUES (789,'Támesis',5);
INSERT INTO Municipio VALUES (790,'Tarazá',5);
INSERT INTO Municipio VALUES (792,'Tarso',5);
INSERT INTO Municipio VALUES (809,'Titiribí',5);
INSERT INTO Municipio VALUES (819,'Toledo',5);
INSERT INTO Municipio VALUES (837,'Turbo',5);
INSERT INTO Municipio VALUES (842,'Uramita',5);
INSERT INTO Municipio VALUES (847,'Urrao',5);
INSERT INTO Municipio VALUES (854,'Valdivia',5);
INSERT INTO Municipio VALUES (856,'Valparaíso',5);
INSERT INTO Municipio VALUES (858,'Vegachí',5);
INSERT INTO Municipio VALUES (861,'Venecia',5);
INSERT INTO Municipio VALUES (873,'Vigía Del Fuerte',5);
INSERT INTO Municipio VALUES (885,'Yali',5);
INSERT INTO Municipio VALUES (887,'Yarumal',5);
INSERT INTO Municipio VALUES (890,'Yolombo',5);
INSERT INTO Municipio VALUES (893,'Yondo',5);
INSERT INTO Municipio VALUES (895,'Zaragoza',5);

INSERT INTO Barrio VALUES (0,'Desconocido',1);
INSERT INTO Barrio VALUES (1,'Aldea Pablo VI',1);
INSERT INTO Barrio VALUES (2,'Alejandría',1);
INSERT INTO Barrio VALUES (3,'Alejandro Echavarría',1);
INSERT INTO Barrio VALUES (4,'Alfonso López',1);
INSERT INTO Barrio VALUES (5,'Altamira',1);
INSERT INTO Barrio VALUES (6,'Altavista',1);
INSERT INTO Barrio VALUES (7,'Altos del Poblado',1);
INSERT INTO Barrio VALUES (8,'Andalucía',1);
INSERT INTO Barrio VALUES (9,'Antonio Nariño',1);
INSERT INTO Barrio VALUES (10,'Aranjuez',1);
INSERT INTO Barrio VALUES (11,'Asomadera N° 1',1);
INSERT INTO Barrio VALUES (12,'Asomadera N° 2',1);
INSERT INTO Barrio VALUES (13,'Asomadera N° 3',1);
INSERT INTO Barrio VALUES (14,'Astorga',1);
INSERT INTO Barrio VALUES (15,'Aures N° 1',1);
INSERT INTO Barrio VALUES (16,'Aures N° 2',1);
INSERT INTO Barrio VALUES (17,'Barrio Colombia',1);
INSERT INTO Barrio VALUES (18,'Barrio Colón',1);
INSERT INTO Barrio VALUES (19,'Barrio Cristóbal',1);
INSERT INTO Barrio VALUES (20,'Barrio Facultad de Minas',1);
INSERT INTO Barrio VALUES (21,'Barrios de Jesús',1);
INSERT INTO Barrio VALUES (22,'Batallón Girardot',1);
INSERT INTO Barrio VALUES (23,'Belalcazar',1);
INSERT INTO Barrio VALUES (24,'Belén',1);
INSERT INTO Barrio VALUES (25,'Belencito',1);
INSERT INTO Barrio VALUES (26,'Bello Horizonte',1);
INSERT INTO Barrio VALUES (27,'Berlín',1);
INSERT INTO Barrio VALUES (28,'Bermejal - Los Álamos',1);
INSERT INTO Barrio VALUES (29,'Betania',1);
INSERT INTO Barrio VALUES (30,'Blanquizal',1);
INSERT INTO Barrio VALUES (31,'Bolivariana',1);
INSERT INTO Barrio VALUES (32,'Bombona N° 1',1);
INSERT INTO Barrio VALUES (33,'Bombona N° 2',1);
INSERT INTO Barrio VALUES (34,'Bosques de San Pablo',1);
INSERT INTO Barrio VALUES (35,'Boston',1);
INSERT INTO Barrio VALUES (36,'Boyacá',1);
INSERT INTO Barrio VALUES (37,'Brasilia',1);
INSERT INTO Barrio VALUES (38,'Buenos Aires',1);
INSERT INTO Barrio VALUES (39,'Calasanz',1);
INSERT INTO Barrio VALUES (40,'Calasanz Parte Alta',1);
INSERT INTO Barrio VALUES (41,'Calle Nueva',1);
INSERT INTO Barrio VALUES (42,'Campo Alegre',1);
INSERT INTO Barrio VALUES (43,'Campo Amor, Noel',1);
INSERT INTO Barrio VALUES (44,'Campo Valdes N° 1',1);
INSERT INTO Barrio VALUES (45,'Campo Valdes N° 2',1);
INSERT INTO Barrio VALUES (46,'Caribe',1);
INSERT INTO Barrio VALUES (47,'Carlos E. Restrepo',1);
INSERT INTO Barrio VALUES (48,'Carpinelo',1);
INSERT INTO Barrio VALUES (49,'Castilla',1);
INSERT INTO Barrio VALUES (50,'Castropol',1);
INSERT INTO Barrio VALUES (51,'Cataluña',1);
INSERT INTO Barrio VALUES (52,'Cerro El Volador',1);
INSERT INTO Barrio VALUES (53,'Cerro Nutibara',1);
INSERT INTO Barrio VALUES (54,'Corazón de Jesús',1);
INSERT INTO Barrio VALUES (55,'Córdoba',1);
INSERT INTO Barrio VALUES (56,'Cristo Rey',1);
INSERT INTO Barrio VALUES (57,'Cuarta Brigada',1);
INSERT INTO Barrio VALUES (58,'Cucaracho',1);
INSERT INTO Barrio VALUES (59,'Diego Echevarria',1);
INSERT INTO Barrio VALUES (60,'Doce de Octubre N° 1',1);
INSERT INTO Barrio VALUES (61,'Doce de Octubre N° 2',1);
INSERT INTO Barrio VALUES (62,'Eduardo Santos',1);
INSERT INTO Barrio VALUES (63,'El Castillo',1);
INSERT INTO Barrio VALUES (64,'El Chagualo',1);
INSERT INTO Barrio VALUES (65,'El Compromiso',1);
INSERT INTO Barrio VALUES (66,'El Corazón',1);
INSERT INTO Barrio VALUES (67,'El Danubio',1);
INSERT INTO Barrio VALUES (68,'El Diamante',1);
INSERT INTO Barrio VALUES (69,'El Diamante N° 2',1);
INSERT INTO Barrio VALUES (70,'El Nogal - Los Almendros',1);
INSERT INTO Barrio VALUES (71,'El Pesebre',1);
INSERT INTO Barrio VALUES (72,'El Pinal',1);
INSERT INTO Barrio VALUES (73,'El Playón de Los Comuneros',1);
INSERT INTO Barrio VALUES (74,'El Poblado',1);
INSERT INTO Barrio VALUES (75,'El Pomar',1);
INSERT INTO Barrio VALUES (76,'El Progreso',1);
INSERT INTO Barrio VALUES (77,'El Raizal',1);
INSERT INTO Barrio VALUES (78,'El Rincón',1);
INSERT INTO Barrio VALUES (79,'El Salado',1);
INSERT INTO Barrio VALUES (80,'El Salvador',1);
INSERT INTO Barrio VALUES (81,'El Socorro',1);
INSERT INTO Barrio VALUES (82,'El Tesoro',1);
INSERT INTO Barrio VALUES (83,'El Triunfo',1);
INSERT INTO Barrio VALUES (84,'El Velódromo',1);
INSERT INTO Barrio VALUES (85,'Enciso',1);
INSERT INTO Barrio VALUES (86,'Estación Villa',1);
INSERT INTO Barrio VALUES (87,'Estadio',1);
INSERT INTO Barrio VALUES (88,'Fátima',1);
INSERT INTO Barrio VALUES (89,'Ferrini',1);
INSERT INTO Barrio VALUES (90,'Florencia',1);
INSERT INTO Barrio VALUES (91,'Florida Nueva',1);
INSERT INTO Barrio VALUES (92,'Francisco Antonio Zea',1);
INSERT INTO Barrio VALUES (93,'Fuente Clara',1);
INSERT INTO Barrio VALUES (94,'Gerona',1);
INSERT INTO Barrio VALUES (95,'Girardot',1);
INSERT INTO Barrio VALUES (96,'Granada',1);
INSERT INTO Barrio VALUES (97,'Granizal',1);
INSERT INTO Barrio VALUES (98,'Guayabal',1);
INSERT INTO Barrio VALUES (99,'Guayaquil',1);
INSERT INTO Barrio VALUES (100,'Héctor Abad Gómez',1);
INSERT INTO Barrio VALUES (101,'Jesús Nazareno',1);
INSERT INTO Barrio VALUES (102,'Juan Pablo II',1);
INSERT INTO Barrio VALUES (103,'Juan XIII - La Quiebra',1);
INSERT INTO Barrio VALUES (104,'Kennedy',1);
INSERT INTO Barrio VALUES (105,'La Aguacatala',1);
INSERT INTO Barrio VALUES (106,'La América',1);
INSERT INTO Barrio VALUES (107,'La Avanzada',1);
INSERT INTO Barrio VALUES (108,'La Candelaria',1);
INSERT INTO Barrio VALUES (109,'La Castellana',1);
INSERT INTO Barrio VALUES (110,'La Colina',1);
INSERT INTO Barrio VALUES (111,'La Cruz',1);
INSERT INTO Barrio VALUES (112,'La Divisa',1);
INSERT INTO Barrio VALUES (113,'La Esperanza',1);
INSERT INTO Barrio VALUES (114,'La Esperanza N° 2',1);
INSERT INTO Barrio VALUES (115,'La Floresta',1);
INSERT INTO Barrio VALUES (116,'La Florida',1);
INSERT INTO Barrio VALUES (117,'La Francia',1);
INSERT INTO Barrio VALUES (118,'La Frontera',1);
INSERT INTO Barrio VALUES (119,'La Gloria',1);
INSERT INTO Barrio VALUES (120,'La Honda',1);
INSERT INTO Barrio VALUES (121,'La Isla',1);
INSERT INTO Barrio VALUES (122,'La Ladera',1);
INSERT INTO Barrio VALUES (123,'La Libertad',1);
INSERT INTO Barrio VALUES (124,'La Loma de Los Bernal',1);
INSERT INTO Barrio VALUES (125,'La Mansión',1);
INSERT INTO Barrio VALUES (126,'La Milagrosa',1);
INSERT INTO Barrio VALUES (127,'La Mota',1);
INSERT INTO Barrio VALUES (128,'La Palma',1);
INSERT INTO Barrio VALUES (129,'La Paralela',1);
INSERT INTO Barrio VALUES (130,'La Pilarica',1);
INSERT INTO Barrio VALUES (131,'La Piñuela',1);
INSERT INTO Barrio VALUES (132,'La Pradera',1);
INSERT INTO Barrio VALUES (133,'La Rosa',1);
INSERT INTO Barrio VALUES (134,'La Salle',1);
INSERT INTO Barrio VALUES (135,'La Sierra -(Santa Lucía - Las Estancias)',1);
INSERT INTO Barrio VALUES (136,'Lalinde',1);
INSERT INTO Barrio VALUES (137,'Las Acacias',1);
INSERT INTO Barrio VALUES (138,'Las Brisas',1);
INSERT INTO Barrio VALUES (139,'Las Esmeraldas',1);
INSERT INTO Barrio VALUES (140,'Las Estancias',1);
INSERT INTO Barrio VALUES (141,'Las Granjas',1);
INSERT INTO Barrio VALUES (142,'Las Independencias',1);
INSERT INTO Barrio VALUES (143,'Las Lomas N° 1',1);
INSERT INTO Barrio VALUES (144,'Las Lomas N° 2',1);
INSERT INTO Barrio VALUES (145,'Las Mercedes',1);
INSERT INTO Barrio VALUES (146,'Las Palmas',1);
INSERT INTO Barrio VALUES (147,'Las Playas',1);
INSERT INTO Barrio VALUES (148,'Las Violetas',1);
INSERT INTO Barrio VALUES (149,'Laureles',1);
INSERT INTO Barrio VALUES (150,'Llanaditas',1);
INSERT INTO Barrio VALUES (151,'López de Mesa',1);
INSERT INTO Barrio VALUES (152,'Lorena',1);
INSERT INTO Barrio VALUES (153,'Loreto',1);
INSERT INTO Barrio VALUES (154,'Los Alcázares',1);
INSERT INTO Barrio VALUES (155,'Los Alpes',1);
INSERT INTO Barrio VALUES (156,'Los Ángeles',1);
INSERT INTO Barrio VALUES (157,'Los Balsos N° 1',1);
INSERT INTO Barrio VALUES (158,'Los Balsos N° 2',1);
INSERT INTO Barrio VALUES (159,'Los Cerros El Vergel',1);
INSERT INTO Barrio VALUES (160,'Los Colores',1);
INSERT INTO Barrio VALUES (161,'Los Conquistadores',1);
INSERT INTO Barrio VALUES (162,'Los Mangos',1);
INSERT INTO Barrio VALUES (163,'Los Naranjos',1);
INSERT INTO Barrio VALUES (164,'Los Pinos',1);
INSERT INTO Barrio VALUES (165,'Manila',1);
INSERT INTO Barrio VALUES (166,'Manrique Central N° 1',1);
INSERT INTO Barrio VALUES (167,'Manrique Central N° 2',1);
INSERT INTO Barrio VALUES (168,'Manrique Oriental',1);
INSERT INTO Barrio VALUES (169,'Maria Cano - Carambolas',1);
INSERT INTO Barrio VALUES (170,'Metropolitano',1);
INSERT INTO Barrio VALUES (171,'Mirador del Doce',1);
INSERT INTO Barrio VALUES (172,'Miraflores',1);
INSERT INTO Barrio VALUES (173,'Miranda',1);
INSERT INTO Barrio VALUES (174,'Miravalle',1);
INSERT INTO Barrio VALUES (175,'Monteclaro',1);
INSERT INTO Barrio VALUES (176,'Moravia',1);
INSERT INTO Barrio VALUES (177,'Moravia',1);
INSERT INTO Barrio VALUES (178,'Moscú N° 1',1);
INSERT INTO Barrio VALUES (179,'Moscú N° 2',1);
INSERT INTO Barrio VALUES (180,'Naranjal',1);
INSERT INTO Barrio VALUES (181,'Nueva Villa de Aburrá',1);
INSERT INTO Barrio VALUES (182,'Nueva Villa de La Iguaná',1);
INSERT INTO Barrio VALUES (183,'Nuevos Conquistadores',1);
INSERT INTO Barrio VALUES (184,'Ocho de Marzo',1);
INSERT INTO Barrio VALUES (185,'Olaya Herrera',1);
INSERT INTO Barrio VALUES (186,'Oriente',1);
INSERT INTO Barrio VALUES (187,'Pablo VI',1);
INSERT INTO Barrio VALUES (188,'Pajarito',1);
INSERT INTO Barrio VALUES (189,'Palenque',1);
INSERT INTO Barrio VALUES (190,'Palermo',1);
INSERT INTO Barrio VALUES (191,'Parque Juan Pablo II',1);
INSERT INTO Barrio VALUES (192,'Patio Bonito',1);
INSERT INTO Barrio VALUES (193,'Pedregal',1);
INSERT INTO Barrio VALUES (194,'Peñitas',1);
INSERT INTO Barrio VALUES (195,'Perpetuo Socorro',1);
INSERT INTO Barrio VALUES (196,'Picachito',1);
INSERT INTO Barrio VALUES (197,'Picacho',1);
INSERT INTO Barrio VALUES (198,'Popular',1);
INSERT INTO Barrio VALUES (199,'Prado',1);
INSERT INTO Barrio VALUES (200,'Progreso N° 2',1);
INSERT INTO Barrio VALUES (201,'Robledo',1);
INSERT INTO Barrio VALUES (202,'Rodeo Alto-La Hondonada',1);
INSERT INTO Barrio VALUES (203,'Rosales',1);
INSERT INTO Barrio VALUES (204,'San Antonio',1);
INSERT INTO Barrio VALUES (205,'San Benito',1);
INSERT INTO Barrio VALUES (206,'San Bernardo',1);
INSERT INTO Barrio VALUES (207,'San Diego',1);
INSERT INTO Barrio VALUES (208,'San Germán',1);
INSERT INTO Barrio VALUES (209,'San Isidro',1);
INSERT INTO Barrio VALUES (210,'San Javier N° 1',1);
INSERT INTO Barrio VALUES (211,'San Javier N° 2',1);
INSERT INTO Barrio VALUES (212,'San Joaquín',1);
INSERT INTO Barrio VALUES (213,'San José La Cima N° 1',1);
INSERT INTO Barrio VALUES (214,'San José La Cima N° 2',1);
INSERT INTO Barrio VALUES (215,'San Lucas',1);
INSERT INTO Barrio VALUES (216,'San Martín de Porres',1);
INSERT INTO Barrio VALUES (217,'San Miguel',1);
INSERT INTO Barrio VALUES (218,'San Pablo',1);
INSERT INTO Barrio VALUES (219,'San Pablo',1);
INSERT INTO Barrio VALUES (220,'San Pedro',1);
INSERT INTO Barrio VALUES (221,'Santa Cruz',1);
INSERT INTO Barrio VALUES (222,'Santa Fe',1);
INSERT INTO Barrio VALUES (223,'Santa Inés',1);
INSERT INTO Barrio VALUES (224,'Santa Lucía',1);
INSERT INTO Barrio VALUES (225,'Santa Margarita',1);
INSERT INTO Barrio VALUES (226,'Santa María de Los Ángeles',1);
INSERT INTO Barrio VALUES (227,'Santa Mónica',1);
INSERT INTO Barrio VALUES (228,'Santa Rosa de Lima',1);
INSERT INTO Barrio VALUES (229,'Santa Teresita',1);
INSERT INTO Barrio VALUES (230,'Santander',1);
INSERT INTO Barrio VALUES (231,'Santo Domingo Savio N° 1',1);
INSERT INTO Barrio VALUES (232,'Santo Domingo Savio N° 2',1);
INSERT INTO Barrio VALUES (233,'Sevilla',1);
INSERT INTO Barrio VALUES (234,'Shellmar',1);
INSERT INTO Barrio VALUES (235,'Simesa',1);
INSERT INTO Barrio VALUES (236,'Simón Bolívar',1);
INSERT INTO Barrio VALUES (237,'Sucre',1);
INSERT INTO Barrio VALUES (238,'Suramericana',1);
INSERT INTO Barrio VALUES (239,'Tejelo',1);
INSERT INTO Barrio VALUES (240,'Tenche',1);
INSERT INTO Barrio VALUES (241,'Toscana',1);
INSERT INTO Barrio VALUES (242,'Trece de Noviembre',1);
INSERT INTO Barrio VALUES (243,'Tricentenario',1);
INSERT INTO Barrio VALUES (244,'Trinidad',1);
INSERT INTO Barrio VALUES (245,'Veinte de Julio',1);
INSERT INTO Barrio VALUES (246,'Versalles N° 1',1);
INSERT INTO Barrio VALUES (247,'Versalles N° 2',1);
INSERT INTO Barrio VALUES (248,'Villa Carlota',1);
INSERT INTO Barrio VALUES (249,'Villa del Socorro',1);
INSERT INTO Barrio VALUES (250,'Villa Flora',1);
INSERT INTO Barrio VALUES (251,'Villa Guadalupe',1);
INSERT INTO Barrio VALUES (252,'Villa Hermosa',1);
INSERT INTO Barrio VALUES (253,'Villa Lilliam',1);
INSERT INTO Barrio VALUES (254,'Villa Niza',1);
INSERT INTO Barrio VALUES (255,'Villa Nueva',1);
INSERT INTO Barrio VALUES (256,'Villa Turbay',1);
INSERT INTO Barrio VALUES (257,'Villatina',1);
INSERT INTO Barrio VALUES (258,'Zafra',1);

INSERT INTO Ocupacion VALUES (0,'Desconocido');
INSERT INTO Ocupacion VALUES (1,'Estudiante');
INSERT INTO Ocupacion VALUES (2,'Docencia');
INSERT INTO Ocupacion VALUES (3,'Empleado');
INSERT INTO Ocupacion VALUES (4,'Ama de Casa');
INSERT INTO Ocupacion VALUES (5,'Independiente');
INSERT INTO Ocupacion VALUES (6,'Medicina');
INSERT INTO Ocupacion VALUES (7,'Administración');
INSERT INTO Ocupacion VALUES (8,'Terapia');
INSERT INTO Ocupacion VALUES (9,'Asistencia');
INSERT INTO Ocupacion VALUES (10,'Bibliotecario');
INSERT INTO Ocupacion VALUES (11,'Decoración');
INSERT INTO Ocupacion VALUES (12,'Enfermería');
INSERT INTO Ocupacion VALUES (13,'Gestión Cultural');
INSERT INTO Ocupacion VALUES (14,'Vigilancia');
INSERT INTO Ocupacion VALUES (15,'Mensajería');
INSERT INTO Ocupacion VALUES (16,'Pleaneación');
INSERT INTO Ocupacion VALUES (17,'Restauración');
INSERT INTO Ocupacion VALUES (18,'Tesorería');
INSERT INTO Ocupacion VALUES (19,'Psicología');
INSERT INTO Ocupacion VALUES (20,'Jubilación');
INSERT INTO Ocupacion VALUES (21,'Ingeniería');
INSERT INTO Ocupacion VALUES (22,'Empleado');
INSERT INTO Ocupacion VALUES (23,'Comercio');
INSERT INTO Ocupacion VALUES (24,'Comunicación');
INSERT INTO Ocupacion VALUES (25,'Abogacía');
INSERT INTO Ocupacion VALUES (26,'Asesoría');
INSERT INTO Ocupacion VALUES (27,'Contaduría');

INSERT INTO Genero VALUES (0,'Otro');
INSERT INTO Genero VALUES (1,'Hombre');
INSERT INTO Genero VALUES (2,'Mujer');

INSERT INTO Edad VALUES (1,'Entre 0 y 6 años', 0, 6);
INSERT INTO Edad VALUES (2,'Entre 7 y 13 años', 7, 13);
INSERT INTO Edad VALUES (3,'Entre 14 y 18 años', 14, 18);
INSERT INTO Edad VALUES (4,'Entre 19 y 29 años', 19, 29);
INSERT INTO Edad VALUES (5,'Entre 30 y 59 años', 30, 59);
INSERT INTO Edad VALUES (6,'Mayor a 60 años', 60, NULL);

INSERT INTO Usuario VALUES (1128439028,'Vincent','David','Restrepo', NULL,'3044000747','vincentrestrepo@gmail.com', '1', 4, 1, NULL,266,'2016-01-02');
INSERT INTO Usuario VALUES (70059550,'Carlos','Mario','Gonz�lez', 'Restrepo','3004949632','carlosmgonzal@gmail.com', '1', 2, 2, 94,NULL,'2016-05-23');
INSERT INTO Usuario VALUES (1128439914,'Xiomara',NULL,'Meneses', 'Cano','3006487850','xiomy.55@gmail.com', '2', 3, 1, 94, NULL,'2016-04-15');
INSERT INTO Usuario VALUES (42874271,'Martha','Cecilia','Franco', NULL,'3122299740','marthaceciliaf@hotmail.com', '2', 4, 3, NULL,266,'2016-07-30');

INSERT INTO Entidad VALUES (890980150,'Biblioteca P�blica Piloto de Medell�n para Am�rica Latina', 'BPP', '4600590', NULL, 'http://www.bibliotecapiloto.gov.co/', 'Carrera 64 No. 50-32', NULL);
INSERT INTO Entidad VALUES (899999063,'Universidad Nacional de Colombia', 'UNAL', '4309000', 'webmaster_med@unal.edu.co', 'http://http://medellin.unal.edu.co/', 'Calle 59 A N 63-20', 70059550);

INSERT INTO Estado VALUES (0, 'Ninguno');
INSERT INTO Estado VALUES (1, 'Presentado');
INSERT INTO Estado VALUES (2, 'Aprobado');
INSERT INTO Estado VALUES (3, 'En ejecuci�n');
INSERT INTO Estado VALUES (4, 'Terminado');
INSERT INTO Estado VALUES (5, 'Pausado');
INSERT INTO Estado VALUES (6, 'Archivado');


INSERT INTO Proyecto VALUES (0, 'Ninguno', 'Sin descripci�n', '2016-02-02', NULL, 2);
INSERT INTO Proyecto VALUES (0, 'Di�logos en la Ciudad', 'Conferencias p�blicas sobre la vida de pensadores', '2016-02-02', NULL, 1);

INSERT INTO Alianza VALUES (0, 890980150, 1, 02-02-2016, NULL);
INSERT INTO Alianza VALUES (0, 899999063, 1, 02-02-2016, NULL);
INSERT INTO Alianza VALUES (0, 899999063, 2, 02-02-2016, NULL);

INSERT INTO Ciclo VALUES (0, 1, 'Ninguno', '2016-02-02', '2016-11-01', 'Sin descripci�n');
INSERT INTO Ciclo VALUES (0, 2, 'Ciclo 2016', '2016-02-02', '2016-11-01', 'Se abordaron los siguientes pensadores: ');

INSERT INTO Sesion VALUES (0, 'El dolor de amar a las mujeres', '2016-02-02', 'Auditorio Biblioteca P�blica Piloto', 'Evento realizado a las 6:30 PM, o cualquier otra info que se quiera poner aqu�.', 2);
INSERT INTO Sesion VALUES (0, 'La vida a la luz de la muerte', '2016-02-20', 'Auditorio Biblioteca P�blica Piloto', 'Un evento maravilloso', 1);
INSERT INTO Sesion VALUES (0, 'Conquistar el ser propio', '2016-03-15', 'Auditorio Biblioteca P�blica Piloto', 'Cuando la vida te reta a ser el que puedes ser.', 2);
INSERT INTO Sesion VALUES (0, 'El peso del pasado en lo que somos', '2016-07-27', 'Auditorio Biblioteca P�blica Piloto', 'Informaci�n cualquiera del evento.', 2);

INSERT INTO Asistencia VALUES (1128439028, 1);
INSERT INTO Asistencia VALUES (70059550, 1);
INSERT INTO Asistencia VALUES (1128439914, 1);
INSERT INTO Asistencia VALUES (42874271, 1);
INSERT INTO Asistencia VALUES (1128439914, 2);
INSERT INTO Asistencia VALUES (1128439028, 2);
INSERT INTO Asistencia VALUES (42874271, 2);
INSERT INTO Asistencia VALUES (70059550, 2);
INSERT INTO Asistencia VALUES (1128439914, 3);
INSERT INTO Asistencia VALUES (1128439028, 3);
INSERT INTO Asistencia VALUES (70059550, 3);
INSERT INTO Asistencia VALUES (1128439914, 4);
INSERT INTO Asistencia VALUES (1128439028, 4);
INSERT INTO Asistencia VALUES (70059550, 4);