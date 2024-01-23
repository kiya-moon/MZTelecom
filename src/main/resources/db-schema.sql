CREATE TABLE IF NOT EXISTS `cust_bas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cust_id` varchar(255) NOT NULL,
  `cust_nm` varchar(255) NOT NULL,
  `cust_password` varchar(255) DEFAULT NULL,
  `cust_idfy_no` varchar(255) NOT NULL,
  `cust_birth` varchar(255) DEFAULT NULL,
  `cust_no` varchar(255) NOT NULL,
  `cust_sex` varchar(255) DEFAULT NULL,
  `cust_address` varchar(255) DEFAULT NULL,
  `cust_email` varchar(255) NOT NULL,
  `intm_pur_stus_yn` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pd6580xkqecmk3dq9h66621q6` (`cust_email`),
  UNIQUE KEY `UK_6xtijx1mpkh4eqx1vue4tt942` (`cust_id`),
  UNIQUE KEY `UK_2hm0lvud61ac9gts2o4cm62jd` (`cust_idfy_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `pur_rev_board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `intm_nm` varchar(255) NOT NULL,
  `board_title` varchar(100) NOT NULL,
  `board_date` varchar(255) NOT NULL,
  `board_detail` varchar(1000) NOT NULL,
  `writer` varchar(255) DEFAULT NULL,
  `cust_bas_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq1dnr4jbud5h2bfk38abajbb5` (`cust_bas_id`),
  CONSTRAINT `FKq1dnr4jbud5h2bfk38abajbb5` FOREIGN KEY (`cust_bas_id`) REFERENCES `cust_bas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `intm_bas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `intm_model_color` varchar(255) DEFAULT NULL,
  `intm_nm` varchar(255) NOT NULL,
  `intm_kor_nm` varchar(255) NOT NULL,
  `intmgb` varchar(255) NOT NULL,
  `intm_price` varchar(255) NOT NULL,
  `is_liked` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `intm_img` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `intm_nm` varchar(255) NOT NULL,
  `img_name` varchar(255) NOT NULL,
  `img_path` varchar(255) NOT NULL,
  `intm_bas_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq8mtrd1yrdr54xfr5jkcfcshg` (`intm_bas_id`),
  CONSTRAINT `FKq8mtrd1yrdr54xfr5jkcfcshg` FOREIGN KEY (`intm_bas_id`) REFERENCES `intm_bas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `intm_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rep_intm_model_id` varchar(255) NOT NULL,
  `intm_seq` varchar(255) DEFAULT NULL,
  `intm_idfy_no` varchar(255) NOT NULL,
  `intm_sales_status` varchar(255) DEFAULT NULL,
  `intm_buyer_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r7cjf4f49udks6k4u4sadnxxy` (`intm_idfy_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `intm_kor_nm` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `order_uid` varchar(255) NOT NULL,
  `cust_bas_id` bigint DEFAULT NULL,
  `intm_product_id` bigint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `payment_uid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhha00abakf1ylafopwl6xx9sp` (`cust_bas_id`),
  KEY `FKgv007dj0f97yhax5wd0fmfun` (`intm_product_id`),
  CONSTRAINT `FKgv007dj0f97yhax5wd0fmfun` FOREIGN KEY (`intm_product_id`) REFERENCES `intm_product` (`id`),
  CONSTRAINT `FKhha00abakf1ylafopwl6xx9sp` FOREIGN KEY (`cust_bas_id`) REFERENCES `cust_bas` (`id`),
  CONSTRAINT `orders_chk_1` CHECK ((`status` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `pur_rev_attachment` (
  `id` bigint NOT NULL,
  `orig_file_name` varchar(255) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `pur_rev_board_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7xdipbforoap800fq2f4h8jr7` (`pur_rev_board_id`),
  CONSTRAINT `FK7xdipbforoap800fq2f4h8jr7` FOREIGN KEY (`pur_rev_board_id`) REFERENCES `pur_rev_board` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `spring_seq_generator` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;