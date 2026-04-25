CREATE TABLE `user` (
                        `id` BIGINT AUTO_INCREMENT COMMENT '主键ID',
                        `name` VARCHAR(50) DEFAULT NULL COMMENT '姓名',
                        `pass_word` VARCHAR(100) DEFAULT NULL COMMENT '密码',
                        `age` INT DEFAULT NULL COMMENT '年龄',
                        `phone_number` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;