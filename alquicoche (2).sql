-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-05-2019 a las 02:20:43
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `alquicoche`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentageneral`
--

CREATE TABLE `cuentageneral` (
  `id` bigint(20) NOT NULL,
  `cuenta_bancaria` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cuentageneral`
--

INSERT INTO `cuentageneral` (`id`, `cuenta_bancaria`) VALUES
(1, 'ES7620770024003102575766');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` bigint(20) NOT NULL,
  `cantidad` double NOT NULL,
  `cuenta_destino` varchar(34) DEFAULT NULL,
  `tarjeta_origen` varchar(34) DEFAULT NULL,
  `reserva_id` bigint(20) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id`, `cantidad`, `cuenta_destino`, `tarjeta_origen`, `reserva_id`, `tipo`) VALUES
(138, 326.3999938964844, 'ES7620770024003102575766', '4365436586543436436', 102, 'reserva'),
(139, 472.5, 'ES7620770024003102575766', '4365436586543436436', 103, 'reserva'),
(140, 561.5999755859375, 'ES7620770024003102575766', '4365436586543436436', 104, 'reserva'),
(141, 3042, 'ES7620770024003102575766', '4365436586543436436', 105, 'reserva'),
(142, 331.20001220703125, 'ES7620770024003102575766', '4365436586543436436', 106, 'reserva'),
(143, 270, 'ES7620770024003102575766', '2342353253SADSAF', 107, 'reserva'),
(144, 3622.5, 'ES7620770024003102575766', '2342353253SADSAF', 108, 'reserva'),
(145, 2070, 'ES7620770024003102575766', '2342353253SADSAF', 109, 'reserva'),
(146, 234, 'ES7620770024003102575766', '2342353253SADSAF', 110, 'reserva'),
(147, 1035, 'ES7620770024003102575766', '2342353253SADSAF', 111, 'reserva'),
(148, 441.6000061035156, 'ES7620770024003102575766', '2342353253SADSAF', 112, 'reserva'),
(149, 489.6000061035156, 'ES7620770024003102575766', '325235325H3H5423', 113, 'reserva'),
(150, 8517.599609375, 'ES7620770024003102575766', '325235325H3H5423', 114, 'reserva'),
(151, 1521, '34751612376240976795', 'ES7620770024003102575766', 105, 'fianza'),
(152, 561.5999755859375, '34751612376240976795', 'ES7620770024003102575766', 104, 'cancelacion'),
(153, 231.84000854492186, '34752612376240976795', 'ES7620770024003102575766', 106, 'cancelacion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `id` bigint(20) NOT NULL,
  `fechafin` date DEFAULT NULL,
  `fechaini` date DEFAULT NULL,
  `numero` bigint(20) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `vehiculo_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `is_cancelada` bit(1) NOT NULL,
  `problema` varchar(255) DEFAULT NULL,
  `seguro` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`id`, `fechafin`, `fechaini`, `numero`, `precio`, `vehiculo_id`, `usuario_id`, `is_cancelada`, `problema`, `seguro`) VALUES
(102, '2018-06-06', '2018-05-29', 1326, 326.4, 23, 18, b'1', '', b'1'),
(103, '2019-01-09', '2019-01-02', 1339, 472.5, 21, 18, b'1', 'Faro estropeado', b'0'),
(104, '2019-07-11', '2019-07-05', 1352, 561.6, 16, 18, b'0', NULL, b'1'),
(105, '2018-06-27', '2018-06-01', 1365, 3042, 16, 18, b'1', '', b'0'),
(106, '2019-06-14', '2019-06-08', 1378, 331.2, 17, 18, b'1', NULL, b'1'),
(107, '2019-07-11', '2019-07-05', 1391, 270, 9, 16, b'0', NULL, b'0'),
(108, '2019-08-23', '2019-06-15', 1404, 3622.5, 10, 16, b'0', NULL, b'0'),
(109, '2019-10-25', '2019-10-02', 1417, 2070, 12, 16, b'0', NULL, b'1'),
(110, '2020-08-08', '2020-08-06', 1430, 234, 16, 16, b'0', NULL, b'0'),
(111, '2019-07-04', '2019-06-19', 1443, 1035, 17, 16, b'0', NULL, b'0'),
(112, '2019-07-24', '2019-07-16', 1456, 441.6, 17, 16, b'0', NULL, b'1'),
(113, '2019-06-07', '2019-06-01', 1469, 489.6, 18, 17, b'0', NULL, b'1'),
(114, '2019-10-11', '2019-07-12', 1482, 8517.6, 16, 17, b'0', NULL, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `bio` varchar(525) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_admin` bit(1) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_pic` varchar(255) DEFAULT NULL,
  `tlf` varchar(12) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `cuenta_bancaria` varchar(255) DEFAULT NULL,
  `tarjeta` varchar(255) NOT NULL,
  `is_Gerente` tinyint(1) NOT NULL DEFAULT '0',
  `is_Gestor` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `addr`, `bio`, `birth`, `email`, `first_name`, `is_admin`, `last_name`, `password`, `profile_pic`, `tlf`, `username`, `cuenta_bancaria`, `tarjeta`, `is_Gerente`, `is_Gestor`) VALUES
(17, 'Chiclana', 'buen piloto', '1990-12-11', 'manutru@gmail.com', 'Manuel', b'0', 'Trujillo', '$2a$11$M1e2KsfNNG1vJxOG9lt1JeFAOEgYw3jC/BJfwDyuMX8C9sVdE1r6m', NULL, '623423542', 'manutru', '34751612376240976795', '325235325H3H5423', 0, 0),
(16, 'san fernando', 'conductor', '1965-05-16', 'juan@gmail.com', 'Juan', b'0', 'Sanchez', '$2a$11$HmPBH2wmvhaU2NP9bBjn7OktLcj6VpNbEuR5jDUxOITrUrYWwu6Ji', NULL, '352352356236', 'juan', '34751612376248976795', '2342353253SADSAF', 0, 0),
(14, 'cadiz', 'gestor', '1980-05-11', 'gestor@gmail.com', 'Gestor', b'0', 'Gestor', '$2a$11$3belA3WPwAn6U1IAUDzzZeLgl9vkSUylunSEMj4tfLowchv/sLlGW', NULL, '345678654', 'gestor', '12341612376240976795', '35326457586867979789', 0, 1),
(15, 'cadiz', 'gerente', '1977-05-30', 'gerente@gmail.com', 'gerente', b'0', 'gerente', '$2a$11$TRmUrqLjZ9J.pwTZZVoQP.kD0vumxPBhSg0eSRRRcI0xcCoYEwmoK', NULL, '325235235', 'gerente', '22231612456240976795', '2354235356346436', 1, 0),
(6, 'Cadiz', 'admin', '1996-01-15', 'root@root.es', 'root', b'1', 'root', '$2a$11$2ykQ9b1mFNsXy0nflVPVDekN5kcXpauk1hhvFnsFUsNKtbcLouQzi', 'src\\webapp\\VAADIN\\img\\85-91.-Pau-Gasol-muestra-su-liderazgo-y-da-la-primera-victoria-a-los-Bulls.jpg', '633907611', 'root', '23344355465656576768', '11111111111111111111111', 0, 0),
(18, 'Conil', 'Hola', '1996-09-12', 'anto@gmail.com', 'Antonio', b'0', 'Garcia', '$2a$11$3YlzbghXzY5wy.OQ3iR1aujuhWfqNpVcS.Q9jpAYfsA/FK3z4JQqa', NULL, '6432423423', 'anto', '12345678906240976795', '4365436586543436436', 0, 0),
(12, 'calle', 'gua', '2019-05-15', 'mail@mail.com', 'manolo', b'0', 'manolo', '$2a$11$MCnGJRmQxC/DAlSaaGLlVOr3UQqqNcnKNYQpM19ZjujuAuqwpL5bq', 'src\\webapp\\VAADIN\\img\\85-91.-Pau-Gasol-muestra-su-liderazgo-y-da-la-primera-victoria-a-los-Bulls.jpg', '214214214', 'man', '23344355465656576768', '231542353454364235', 1, 0),
(13, 'CALLE CADIZ', 'buen conductor', '1996-05-15', 'MAZG@GMAIL.COM', 'Miguel', b'0', 'Zara', '$2a$11$Ugt2pJ7NAyhzJLUFnHWaYutLMfN4PobuCh5SbzaI0aqBqropMySKK', 'src\\webapp\\VAADIN\\img\\a270d97b-b0a8-4cd3-b21a-0202655f2c0a.jpg', '654786543', 'MAZG', '23366655465656576768', '31224235236325365', 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `id` int(32) NOT NULL,
  `matricula` varchar(10) CHARACTER SET utf8 NOT NULL,
  `marca` varchar(32) CHARACTER SET utf8 NOT NULL,
  `estado` varchar(32) CHARACTER SET utf8 NOT NULL,
  `climatizador` int(32) NOT NULL,
  `gps` int(32) NOT NULL,
  `numero_de_plazas` int(32) NOT NULL,
  `tipo_transmision` varchar(255) CHARACTER SET utf8 NOT NULL,
  `carroceria` varchar(32) NOT NULL,
  `precio_dia` int(11) NOT NULL,
  `oficina` varchar(255) NOT NULL,
  `galeria` varchar(255) CHARACTER SET utf8 NOT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `disponibilidad_ini` date DEFAULT NULL,
  `disponibilidad_fin` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id`, `matricula`, `marca`, `estado`, `climatizador`, `gps`, `numero_de_plazas`, `tipo_transmision`, `carroceria`, `precio_dia`, `oficina`, `galeria`, `usuario_id`, `disponibilidad_ini`, `disponibilidad_fin`) VALUES
(9, '1234', 'Opel', 'disponible', 1, 1, 5, 'automatico', 'okey', 30, 'cadiz', 'src\\webapp\\VAADIN\\img\\Opel-Corsa.jpg', 14, '2018-05-19', '2019-08-31'),
(10, '2881', 'Renault', 'disponible', 1, 1, 5, 'manual', 'tres volumenes', 35, 'Cádiz', 'src\\webapp\\VAADIN\\img\\auto-97-renault-kadjar-1-5-dci-zen.png', 12, '2019-06-10', '2019-11-10'),
(11, '3445', 'Seat', 'disponible', 1, 1, 7, 'Manual', 'monovolumen', 60, 'Chiclana', 'src\\webapp\\VAADIN\\img\\imageCrop1.resizeViewPort.crop.thumbnailAspectRatio.noScale.fileReference.jpg', 12, '2019-09-04', '2020-01-01'),
(12, '6887', 'Audi', 'disponible', 1, 0, 5, 'manual', 'turismo', 75, 'San Fernando', 'src\\webapp\\VAADIN\\img\\174732687_g.jpg', 12, '2019-09-04', '2019-12-04'),
(13, '4560', 'Volkswagen', 'averiado', 1, 1, 5, 'automatico', 'monovolumen', 45, 'Cádiz', 'src\\webapp\\VAADIN\\img\\412alOEi3NL._SR500,500_.jpg', 12, '2019-05-14', '2019-06-20'),
(15, '4654', 'Opel', 'disponible', 1, 1, 6, 'automatico', 'monovolumen', 67, 'cadiz', 'src\\webapp\\VAADIN\\img\\auto-97-renault-kadjar-1-5-dci-zen.png', 14, '2019-05-29', '2019-05-27'),
(16, '5668', 'Opel', 'disponible', 1, 1, 5, 'automatico', 'dos volumenes', 78, 'San Fernando', 'src\\webapp\\VAADIN\\img\\opelastra.jpg', 14, '2019-05-30', '2020-09-30'),
(17, '8899', 'Peugeot', 'disponible', 1, 1, 5, 'manual', 'monovolumen', 46, 'Cádiz', 'src\\webapp\\VAADIN\\img\\peugeot.jpg', 14, '2019-05-20', '2019-07-25'),
(18, '4322', 'Seat', 'disponible', 0, 0, 4, 'manual', 'dos volumenes', 68, 'Cádiz', 'src\\webapp\\VAADIN\\img\\seat.jpg', 14, '2019-05-01', '2019-06-09'),
(19, '5609', 'Peugeot', 'disponible', 1, 1, 6, 'manual', 'monovolumen', 78, 'Cádiz', 'src\\webapp\\VAADIN\\img\\peugeot2.jpg', 14, '2018-05-09', '2019-06-08'),
(20, '3412', 'Renault', 'disponible', 1, 1, 5, 'disponible', 'dos volumenes', 23, 'San Fernando', 'src\\webapp\\VAADIN\\img\\cli.jpg', 14, '2016-05-18', '2020-05-08'),
(21, '5687', 'Renault', 'disponible', 1, 0, 5, 'manual', 'dos volumenes', 45, 'Cádiz', 'src\\webapp\\VAADIN\\img\\clio.jpg', 14, '2019-08-01', '2019-08-10'),
(22, '1290', 'Renault', 'averiado', 0, 1, 6, 'manual', 'monovolumen', 56, 'Cádiz', 'src\\webapp\\VAADIN\\img\\megane.jpg', 14, '2019-05-01', '2019-05-31'),
(23, '5633', 'Renault', 'disponible', 1, 1, 5, 'manual', 'dos volumenes', 34, 'San Fernando', 'src\\webapp\\VAADIN\\img\\megane2.jpg', 14, '2020-05-15', '2021-12-16');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cuentageneral`
--
ALTER TABLE `cuentageneral`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjhgsixor5oi0du19icf9xcq7y` (`reserva_id`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKc5mobtjdvcc3eqscg2fl5jyeh` (`vehiculo_id`),
  ADD KEY `FK58db3t751820jr4w9o6mnlic7` (`usuario_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8eqws5ibb8h4ur6879y1xiulu` (`usuario_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuentageneral`
--
ALTER TABLE `cuentageneral`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=154;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=115;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
