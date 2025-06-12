-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `bebida_mas_comprada_por_cliente`
--

DROP TABLE IF EXISTS `bebida_mas_comprada_por_cliente`;
/*!50001 DROP VIEW IF EXISTS `bebida_mas_comprada_por_cliente`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bebida_mas_comprada_por_cliente` AS SELECT 
 1 AS `idCliente`,
 1 AS `Cliente`,
 1 AS `idProducto`,
 1 AS `Producto`,
 1 AS `TotalComprado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `bebida_menos_comprada_por_cliente`
--

DROP TABLE IF EXISTS `bebida_menos_comprada_por_cliente`;
/*!50001 DROP VIEW IF EXISTS `bebida_menos_comprada_por_cliente`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bebida_menos_comprada_por_cliente` AS SELECT 
 1 AS `idCliente`,
 1 AS `Cliente`,
 1 AS `idProducto`,
 1 AS `Producto`,
 1 AS `TotalComprado`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) DEFAULT NULL,
  `Direccion` varchar(200) DEFAULT NULL,
  `RFC` varchar(15) DEFAULT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Requiere_Factura` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Juan Pérez','Av. Revolución 123, CDMX','JUPR900101ABC','5512345678','juan.perez@email.com',1),(2,'María Lópe','Calle Hidalgo 456, Guadalajara','MALO850203XYZ','3339876543','maria.lopez@email.com',1),(3,'Carlos Sánchez','Blvd. Morelos 789, Monterrey','CASA780315DEF','8187654321','carlos.sanchez@email.com',1),(4,'Ana Torres','Av. Juárez 321, Puebla','mars112312','2223456789','ana.torres@email.com',1),(6,'Carlos Martín','Calle 123','123','123','1223',1),(8,'Cliente Default','Sin dirección','DEFAULT000000','0000000000','default@cliente.com',0);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `idCompras` int NOT NULL AUTO_INCREMENT,
  `Proveedor_idProveedor` int DEFAULT NULL,
  `Total` decimal(10,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idCompras`),
  KEY `Proveedor_idProveedor` (`Proveedor_idProveedor`),
  CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`Proveedor_idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (15,2,36478.00,'2025-06-11');
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_compra`
--

DROP TABLE IF EXISTS `detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_compra` (
  `Compras_idCompras` int NOT NULL,
  `Producto_idProducto` int NOT NULL,
  `Cantidad` int DEFAULT NULL,
  `Precio_Compra` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Compras_idCompras`,`Producto_idProducto`),
  KEY `Producto_idProducto` (`Producto_idProducto`),
  CONSTRAINT `fk_detalle_compra_compras` FOREIGN KEY (`Compras_idCompras`) REFERENCES `compras` (`idCompras`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compra`
--

LOCK TABLES `detalle_compra` WRITE;
/*!40000 ALTER TABLE `detalle_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_venta` (
  `Producto_idProducto` int NOT NULL,
  `Venta_idVenta` int NOT NULL,
  `Cantidad_Producto` int DEFAULT NULL,
  `Total_Producto` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Producto_idProducto`,`Venta_idVenta`),
  KEY `Venta_idVenta` (`Venta_idVenta`),
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`Venta_idVenta`) REFERENCES `venta` (`idVenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES (1,1,5,60.00),(1,3,13,13.00),(2,2,3,45.00),(4,1,6,60.00),(7,3,20,20.00);
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `idPedido` int NOT NULL,
  `Producto_idProducto` int DEFAULT NULL,
  `Proveedor_idProveedor` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idPedido`),
  KEY `Producto_idProducto` (`Producto_idProducto`),
  KEY `Proveedor_idProveedor` (`Proveedor_idProveedor`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`Proveedor_idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,1,1,50,'2025-05-18'),(2,4,2,100,'2025-05-19');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) DEFAULT NULL,
  `Presentacion` varchar(50) DEFAULT NULL,
  `Costo` decimal(10,2) DEFAULT NULL,
  `Cantidad_Actual` int DEFAULT NULL,
  `Cantidad_Minima` int DEFAULT NULL,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Coca-Cola','Lata 355ml',13.00,200,50),(2,'Pepsi','Botella 600ml',15.00,150,40),(3,'Fanta','Lata 355ml',12.00,100,30),(4,'Agua Ciel','Botella 500ml',200.00,300,70),(5,'Fanta de fresa','600',20.00,59,50),(6,'Prueba','200',30.00,31,30),(7,'Prueba2','123',20.00,251,200);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promocion`
--

DROP TABLE IF EXISTS `promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promocion` (
  `idPromocion` int NOT NULL AUTO_INCREMENT,
  `Cliente_idCliente` int DEFAULT NULL,
  `Producto_idProducto` int DEFAULT NULL,
  `Valor/Modificador` int DEFAULT NULL,
  `Fecha_Inicio` date DEFAULT NULL,
  `Fecha_Fin` date DEFAULT NULL,
  PRIMARY KEY (`idPromocion`),
  KEY `Cliente_idCliente` (`Cliente_idCliente`),
  KEY `Producto_idProducto` (`Producto_idProducto`),
  CONSTRAINT `promocion_ibfk_2` FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promocion`
--

LOCK TABLES `promocion` WRITE;
/*!40000 ALTER TABLE `promocion` DISABLE KEYS */;
INSERT INTO `promocion` VALUES (1,1,1,10,'2025-05-20','2025-06-10'),(7,1,4,11,'2025-05-10','2025-05-17');
/*!40000 ALTER TABLE `promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `idProveedor` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `RFC` varchar(15) DEFAULT NULL,
  `Direccion` varchar(200) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `Correo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'Distribuidora Bebidas MX','DBMX990101AAA','Calle Central 100','5551112222','ventas@bebidasmx.com'),(2,'Surtidora Refrescos S.A.','SRS950505BBB','Av. Comercial 200','5553334444','contacto@srs.com'),(4,'Surtidora Refrescos S.A.','SRS950505BBB','Av. Comercial 200, Monterrey','5553334444','contacto@srs.com'),(5,'Bebidas y Más','BYM880808CCC','Blvd. Central 150, Guadalajara','5554445555','info@bebidasyms.com'),(6,'Suministros Refrescos','SUMR920303DDD','Av. Las Americas 50, León','5556667777','ventas@suministrosrefrescos.com'),(7,'Distribuciones del Norte','DDN900101EEE','Calle Norte 30, Monterrey','5558889999','contacto@distribnorte.com'),(8,'Refrescos del Bajío','RDB850707FFF','Av. Bajío 400, León','5557776666','ventas@refrescosbajio.com'),(9,'Surtidora Tropical','SUTR930909GGG','Calle Tropical 123, Cancún','5552223333','ventas@surtidoratropical.com'),(10,'Bebidas Selectas','BESX900101HHH','Av. Selecta 60, Puebla','5559990000','contacto@bebidasselectas.com'),(11,'Proveedores Unidos','PRUN920202III','Calle Unión 75, Guadalajara','5554441111','info@proveedoresunidos.com'),(12,'Importadora de Refrescos','IMDR951010JJJ','Av. Importación 80, CDMX','5551234567','ventas@importadora.com'),(13,'Leonardo','09187230971208','ashdk','12313','12313');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `apellidoPaterno` varchar(50) DEFAULT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Seth','Ramírez','Morales','seth','password123',1),(2,'Laura','Gómez','Ruiz','laura','pass456',2),(3,'Carlos','Ramírez','González','carlos.rg','12345Segura',1),(4,'asd','asd','asd','asd','asd',1),(5,'Juan','Perez','Gonzales','Juan','12345',2),(6,'Leo','Or','Teo','li0','081205',1),(7,'Prueba','Proband','Sistema','Prueba','123',2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `idVenta` int NOT NULL AUTO_INCREMENT,
  `Cliente_idCliente` int DEFAULT NULL,
  `Total` decimal(10,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idVenta`),
  KEY `Cliente_idCliente` (`Cliente_idCliente`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (1,1,120.00,'2025-05-21'),(2,2,45.00,'2025-05-21'),(3,1,0.00,'2025-06-12');
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_producto_mas_vendido`
--

DROP TABLE IF EXISTS `vista_producto_mas_vendido`;
/*!50001 DROP VIEW IF EXISTS `vista_producto_mas_vendido`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_producto_mas_vendido` AS SELECT 
 1 AS `Producto`,
 1 AS `TotalVendida`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_producto_menos_vendido`
--

DROP TABLE IF EXISTS `vista_producto_menos_vendido`;
/*!50001 DROP VIEW IF EXISTS `vista_producto_menos_vendido`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_producto_menos_vendido` AS SELECT 
 1 AS `Producto`,
 1 AS `TotalVendida`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_productos_no_vendidos_por_cliente`
--

DROP TABLE IF EXISTS `vista_productos_no_vendidos_por_cliente`;
/*!50001 DROP VIEW IF EXISTS `vista_productos_no_vendidos_por_cliente`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_productos_no_vendidos_por_cliente` AS SELECT 
 1 AS `idCliente`,
 1 AS `NombreCliente`,
 1 AS `idProducto`,
 1 AS `NombreProducto`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_total_ventas_por_producto`
--

DROP TABLE IF EXISTS `vista_total_ventas_por_producto`;
/*!50001 DROP VIEW IF EXISTS `vista_total_ventas_por_producto`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_total_ventas_por_producto` AS SELECT 
 1 AS `idProducto`,
 1 AS `NombreProducto`,
 1 AS `TotalVendido`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_ventas_cliente_factura`
--

DROP TABLE IF EXISTS `vista_ventas_cliente_factura`;
/*!50001 DROP VIEW IF EXISTS `vista_ventas_cliente_factura`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_ventas_cliente_factura` AS SELECT 
 1 AS `idVenta`,
 1 AS `fecha`,
 1 AS `cliente`,
 1 AS `total_venta`,
 1 AS `Requiere_Factura`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `bebida_mas_comprada_por_cliente`
--

/*!50001 DROP VIEW IF EXISTS `bebida_mas_comprada_por_cliente`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `bebida_mas_comprada_por_cliente` AS select `t1`.`idCliente` AS `idCliente`,`t1`.`Cliente` AS `Cliente`,`t1`.`idProducto` AS `idProducto`,`t1`.`Producto` AS `Producto`,`t1`.`TotalComprado` AS `TotalComprado` from ((select `c`.`idCliente` AS `idCliente`,`c`.`Nombre` AS `Cliente`,`p`.`idProducto` AS `idProducto`,`p`.`Nombre` AS `Producto`,sum(`dv`.`Cantidad_Producto`) AS `TotalComprado` from (((`cliente` `c` join `venta` `v` on((`c`.`idCliente` = `v`.`Cliente_idCliente`))) join `detalle_venta` `dv` on((`v`.`idVenta` = `dv`.`Venta_idVenta`))) join `producto` `p` on((`dv`.`Producto_idProducto` = `p`.`idProducto`))) group by `c`.`idCliente`,`p`.`idProducto`) `t1` join (select `t`.`idCliente` AS `idCliente`,max(`t`.`TotalComprado`) AS `MaxComprado` from (select `c`.`idCliente` AS `idCliente`,`p`.`idProducto` AS `idProducto`,sum(`dv`.`Cantidad_Producto`) AS `TotalComprado` from (((`cliente` `c` join `venta` `v` on((`c`.`idCliente` = `v`.`Cliente_idCliente`))) join `detalle_venta` `dv` on((`v`.`idVenta` = `dv`.`Venta_idVenta`))) join `producto` `p` on((`dv`.`Producto_idProducto` = `p`.`idProducto`))) group by `c`.`idCliente`,`p`.`idProducto`) `t` group by `t`.`idCliente`) `t2` on(((`t1`.`idCliente` = `t2`.`idCliente`) and (`t1`.`TotalComprado` = `t2`.`MaxComprado`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `bebida_menos_comprada_por_cliente`
--

/*!50001 DROP VIEW IF EXISTS `bebida_menos_comprada_por_cliente`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `bebida_menos_comprada_por_cliente` AS select `t1`.`idCliente` AS `idCliente`,`t1`.`Cliente` AS `Cliente`,`t1`.`idProducto` AS `idProducto`,`t1`.`Producto` AS `Producto`,`t1`.`TotalComprado` AS `TotalComprado` from ((select `c`.`idCliente` AS `idCliente`,`c`.`Nombre` AS `Cliente`,`p`.`idProducto` AS `idProducto`,`p`.`Nombre` AS `Producto`,sum(`dv`.`Cantidad_Producto`) AS `TotalComprado` from (((`cliente` `c` join `venta` `v` on((`c`.`idCliente` = `v`.`Cliente_idCliente`))) join `detalle_venta` `dv` on((`v`.`idVenta` = `dv`.`Venta_idVenta`))) join `producto` `p` on((`dv`.`Producto_idProducto` = `p`.`idProducto`))) group by `c`.`idCliente`,`p`.`idProducto`) `t1` join (select `t`.`idCliente` AS `idCliente`,min(`t`.`TotalComprado`) AS `MinComprado` from (select `c`.`idCliente` AS `idCliente`,`p`.`idProducto` AS `idProducto`,sum(`dv`.`Cantidad_Producto`) AS `TotalComprado` from (((`cliente` `c` join `venta` `v` on((`c`.`idCliente` = `v`.`Cliente_idCliente`))) join `detalle_venta` `dv` on((`v`.`idVenta` = `dv`.`Venta_idVenta`))) join `producto` `p` on((`dv`.`Producto_idProducto` = `p`.`idProducto`))) group by `c`.`idCliente`,`p`.`idProducto`) `t` group by `t`.`idCliente`) `t2` on(((`t1`.`idCliente` = `t2`.`idCliente`) and (`t1`.`TotalComprado` = `t2`.`MinComprado`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_producto_mas_vendido`
--

/*!50001 DROP VIEW IF EXISTS `vista_producto_mas_vendido`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_producto_mas_vendido` AS select `p`.`Nombre` AS `Producto`,sum(`dv`.`Cantidad_Producto`) AS `TotalVendida` from (`detalle_venta` `dv` join `producto` `p` on((`dv`.`Producto_idProducto` = `p`.`idProducto`))) group by `p`.`idProducto`,`p`.`Nombre` order by `TotalVendida` desc limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_producto_menos_vendido`
--

/*!50001 DROP VIEW IF EXISTS `vista_producto_menos_vendido`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_producto_menos_vendido` AS select `p`.`Nombre` AS `Producto`,sum(`dv`.`Cantidad_Producto`) AS `TotalVendida` from (`detalle_venta` `dv` join `producto` `p` on((`dv`.`Producto_idProducto` = `p`.`idProducto`))) group by `p`.`idProducto`,`p`.`Nombre` order by `TotalVendida` limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_productos_no_vendidos_por_cliente`
--

/*!50001 DROP VIEW IF EXISTS `vista_productos_no_vendidos_por_cliente`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_productos_no_vendidos_por_cliente` AS select `c`.`idCliente` AS `idCliente`,`c`.`Nombre` AS `NombreCliente`,`p`.`idProducto` AS `idProducto`,`p`.`Nombre` AS `NombreProducto` from (`cliente` `c` join `producto` `p`) where exists(select 1 from (`venta` `v` join `detalle_venta` `dv` on((`v`.`idVenta` = `dv`.`Venta_idVenta`))) where ((`v`.`Cliente_idCliente` = `c`.`idCliente`) and (`dv`.`Producto_idProducto` = `p`.`idProducto`))) is false union select NULL AS `idCliente`,'Ningún cliente' AS `NombreCliente`,`p`.`idProducto` AS `idProducto`,`p`.`Nombre` AS `NombreProducto` from `producto` `p` where exists(select 1 from `detalle_venta` `dv` where (`dv`.`Producto_idProducto` = `p`.`idProducto`)) is false */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_total_ventas_por_producto`
--

/*!50001 DROP VIEW IF EXISTS `vista_total_ventas_por_producto`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_total_ventas_por_producto` AS select `p`.`idProducto` AS `idProducto`,`p`.`Nombre` AS `NombreProducto`,coalesce(sum(`dv`.`Cantidad_Producto`),0) AS `TotalVendido` from (`producto` `p` left join `detalle_venta` `dv` on((`p`.`idProducto` = `dv`.`Producto_idProducto`))) group by `p`.`idProducto`,`p`.`Nombre` order by `TotalVendido` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_ventas_cliente_factura`
--

/*!50001 DROP VIEW IF EXISTS `vista_ventas_cliente_factura`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_ventas_cliente_factura` AS select `v`.`idVenta` AS `idVenta`,`v`.`fecha` AS `fecha`,`c`.`Nombre` AS `cliente`,`v`.`Total` AS `total_venta`,`c`.`Requiere_Factura` AS `Requiere_Factura` from (`venta` `v` left join `cliente` `c` on((`v`.`Cliente_idCliente` = `c`.`idCliente`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-12 10:48:19
