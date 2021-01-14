

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=latin1;

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) DEFAULT CHARSET=latin1;

CREATE TABLE `asset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `asset_category` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `ticker_exchange` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc2ryc74cegxqe4sfvxrox86gx` (`ticker`,`asset_category`)
) DEFAULT CHARSET=latin1;

CREATE TABLE `wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hgee4p1hiwadqinr0avxlq4eb` (`user_id`),
  CONSTRAINT `FKbs4ogwiknsup4rpw8d47qw9dx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) DEFAULT CHARSET=latin1;

CREATE TABLE `contribution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `exchange` varchar(255) DEFAULT NULL,
  `total_price` decimal(19,2) DEFAULT NULL,
  `unit_price` decimal(19,2) DEFAULT NULL,
  `asset_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `wallet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3i305tgv1451w6jk355c6u5uf` (`asset_id`),
  KEY `FKl12pusqvyg876q8p3glvovwo8` (`user_id`),
  KEY `FK95ibubcyutd2i0s3pseg3lr40` (`wallet_id`),
  CONSTRAINT `FK3i305tgv1451w6jk355c6u5uf` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`),
  CONSTRAINT `FK95ibubcyutd2i0s3pseg3lr40` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`),
  CONSTRAINT `FKl12pusqvyg876q8p3glvovwo8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) DEFAULT CHARSET=latin1;