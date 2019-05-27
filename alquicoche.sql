-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-05-2019 a las 12:07:19
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
(108, 280, 'ES7620770024003102575766', '11111111111111111111111', 77, 'reserva'),
(109, 224, '233443554656565767687878', 'ES7620770024003102575766', 77, 'cancelacion'),
(110, 108, 'ES7620770024003102575766', '231542353454364235', 78, 'reserva'),
(111, 86.4, '233443554656565767687878', 'ES7620770024003102575766', 78, 'cancelacion'),
(112, 86.4, '233443554656565767687878', 'ES7620770024003102575766', 78, 'cancelacion'),
(120, 30, 'ES7620770024003102575766', '231542353454364235', 86, 'reserva'),
(121, 720, 'ES7620770024003102575766', '231542353454364235', 87, 'reserva'),
(122, 24, '233443554656565767687878', 'ES7620770024003102575766', 86, 'cancelacion'),
(123, 60, 'ES7620770024003102575766', '231542353454364235', 88, 'reserva');

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
(77, '2019-05-30', '2019-05-22', 1001, 280, 10, 6, b'1', NULL, b'0'),
(78, '2019-05-25', '2019-05-22', 1014, 108, 11, 12, b'1', NULL, b'1'),
(86, '2019-06-02', '2019-06-01', 1118, 30, 9, 12, b'1', NULL, b'0'),
(85, '2019-05-22', '2019-05-15', 1105, 240, 9, 12, b'1', 'faro roto', b'0'),
(87, '2019-05-31', '2019-05-23', 1131, 720, 12, 12, b'0', NULL, b'1'),
(88, '2019-06-08', '2019-06-06', 1144, 60, 9, 12, b'0', NULL, b'0');

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
(6, 'Cadiz', 'admin', '1996-01-15', 'root@root.es', 'root', b'1', 'root', '$2a$11$2ykQ9b1mFNsXy0nflVPVDekN5kcXpauk1hhvFnsFUsNKtbcLouQzi', 'src\\webapp\\VAADIN\\img\\85-91.-Pau-Gasol-muestra-su-liderazgo-y-da-la-primera-victoria-a-los-Bulls.jpg', '633907611', 'root', '233443554656565767687878', '11111111111111111111111', 0, 1),
(12, 'calle', 'gua', '2019-05-15', 'mail@mail.com', 'manolo', b'0', 'manolo', '$2a$11$MCnGJRmQxC/DAlSaaGLlVOr3UQqqNcnKNYQpM19ZjujuAuqwpL5bq', 'src\\webapp\\VAADIN\\img\\85-91.-Pau-Gasol-muestra-su-liderazgo-y-da-la-primera-victoria-a-los-Bulls.jpg', '214214214', 'man', '233443554656565767687878', '231542353454364235', 1, 0);

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
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id`, `matricula`, `marca`, `estado`, `climatizador`, `gps`, `numero_de_plazas`, `tipo_transmision`, `carroceria`, `precio_dia`, `oficina`, `galeria`, `usuario_id`) VALUES
(9, '1234', 'Opel', 'disponible', 1, 1, 5, 'automatico', 'okey', 30, 'cadiz', 'src\\webapp\\VAADIN\\img\\Opel-Corsa.jpg', 12),
(10, '2881', 'Renault', 'disponible', 1, 1, 5, 'manual', 'tres volumenes', 35, 'Cádiz', 'src\\webapp\\VAADIN\\img\\auto-97-renault-kadjar-1-5-dci-zen.png', 12),
(11, '3445', 'Seat', 'disponible', 1, 1, 7, 'Manual', 'monovolumen', 60, 'Chiclana', 'src\\webapp\\VAADIN\\img\\imageCrop1.resizeViewPort.crop.thumbnailAspectRatio.noScale.fileReference.jpg', 12),
(12, '6887', 'Audi', 'disponible', 1, 0, 5, 'manual', 'turismo', 75, 'San Fernando', 'src\\webapp\\VAADIN\\img\\174732687_g.jpg', 12),
(13, '4560', 'Volkswagen', 'averiado', 1, 1, 5, 'automatico', 'monovolumen', 45, 'Cádiz', 'src\\webapp\\VAADIN\\img\\412alOEi3NL._SR500,500_.jpg', 12);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
