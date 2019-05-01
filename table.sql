CREATE TABLE `tb_dialogue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` varchar(10) DEFAULT NULL,
  `variance` double DEFAULT NULL,
  `average` double DEFAULT NULL,
  `min` double DEFAULT NULL,
  `words` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32193 DEFAULT CHARSET=utf8