-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-05-2019 a las 16:13:04
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
(17, 'Chiclana', 'buen piloto', '1990-12-11', 'manutru@gmail.com', 'Manuel', b'0', 'Trujillo', '$2a$11$M1e2KsfNNG1vJxOG9lt1JeFAOEgYw3jC/BJfwDyuMX8C9sVdE1r6m', NULL, '623423542', 'manutru', NULL, '325235325H3H5423', 0, 0),
(16, 'san fernando', 'conductor', '1965-05-16', 'juan@gmail.com', 'Juan', b'0', 'Sanchez', '$2a$11$HmPBH2wmvhaU2NP9bBjn7OktLcj6VpNbEuR5jDUxOITrUrYWwu6Ji', NULL, '352352356236', 'juan', NULL, '2342353253SADSAF', 0, 0),
(14, 'cadiz', 'gestor', '1980-05-11', 'gestor@gmail.com', 'Gestor', b'0', 'Gestor', '$2a$11$3belA3WPwAn6U1IAUDzzZeLgl9vkSUylunSEMj4tfLowchv/sLlGW', NULL, '345678654', 'gestor', NULL, '35326457586867979789', 0, 1),
(15, 'cadiz', 'gerente', '1977-05-30', 'gerente@gmail.com', 'gerente', b'0', 'gerente', '$2a$11$TRmUrqLjZ9J.pwTZZVoQP.kD0vumxPBhSg0eSRRRcI0xcCoYEwmoK', NULL, '325235235', 'gerente', NULL, '2354235356346436', 1, 0),
(6, 'Cadiz', 'admin', '1996-01-15', 'root@root.es', 'root', b'1', 'root', '$2a$11$2ykQ9b1mFNsXy0nflVPVDekN5kcXpauk1hhvFnsFUsNKtbcLouQzi', 'src\\webapp\\VAADIN\\img\\85-91.-Pau-Gasol-muestra-su-liderazgo-y-da-la-primera-victoria-a-los-Bulls.jpg', '633907611', 'root', '233443554656565767687878', '11111111111111111111111', 0, 0),
(18, 'Conil', 'Hola', '1996-09-12', 'anto@gmail.com', 'Antonio', b'0', 'Garcia', '$2a$11$3YlzbghXzY5wy.OQ3iR1aujuhWfqNpVcS.Q9jpAYfsA/FK3z4JQqa', NULL, '6432423423', 'anto', NULL, '4365436586543436436', 0, 0),
(12, 'calle', 'gua', '2019-05-15', 'mail@mail.com', 'manolo', b'0', 'manolo', '$2a$11$MCnGJRmQxC/DAlSaaGLlVOr3UQqqNcnKNYQpM19ZjujuAuqwpL5bq', 'src\\webapp\\VAADIN\\img\\85-91.-Pau-Gasol-muestra-su-liderazgo-y-da-la-primera-victoria-a-los-Bulls.jpg', '214214214', 'man', '233443554656565767687878', '231542353454364235', 1, 0),
(13, 'CALLE CADIZ', 'buen conductor', '1996-05-15', 'MAZG@GMAIL.COM', 'Miguel', b'0', 'Zara', '$2a$11$Ugt2pJ7NAyhzJLUFnHWaYutLMfN4PobuCh5SbzaI0aqBqropMySKK', 'src\\webapp\\VAADIN\\img\\a270d97b-b0a8-4cd3-b21a-0202655f2c0a.jpg', '654786543', 'MAZG', '233443554656565767687878', '31224235236325365', 0, 0);

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
(9, '1234', 'Opel', 'disponible', 1, 1, 5, 'automatico', 'okey', 30, 'cadiz', 'src\\webapp\\VAADIN\\img\\Opel-Corsa.jpg', 14, '2019-05-19', '2019-05-31'),
(10, '2881', 'Renault', 'disponible', 1, 1, 5, 'manual', 'tres volumenes', 35, 'Cádiz', 'src\\webapp\\VAADIN\\img\\auto-97-renault-kadjar-1-5-dci-zen.png', 12, NULL, NULL),
(11, '3445', 'Seat', 'disponible', 1, 1, 7, 'Manual', 'monovolumen', 60, 'Chiclana', 'src\\webapp\\VAADIN\\img\\imageCrop1.resizeViewPort.crop.thumbnailAspectRatio.noScale.fileReference.jpg', 12, NULL, NULL),
(12, '6887', 'Audi', 'disponible', 1, 0, 5, 'manual', 'turismo', 75, 'San Fernando', 'src\\webapp\\VAADIN\\img\\174732687_g.jpg', 12, NULL, NULL),
(13, '4560', 'Volkswagen', 'averiado', 1, 1, 5, 'automatico', 'monovolumen', 45, 'Cádiz', 'src\\webapp\\VAADIN\\img\\412alOEi3NL._SR500,500_.jpg', 12, NULL, NULL),
(15, '4654', 'Opel', 'disponible', 1, 1, 6, 'automatico', 'monovolumen', 67, 'cadiz', 'src\\webapp\\VAADIN\\img\\auto-97-renault-kadjar-1-5-dci-zen.png', 14, '2019-05-29', '2019-05-27');

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
