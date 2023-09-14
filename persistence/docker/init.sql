CREATE DATABASE chatting;

CREATE TABLE `chatting`.`chatroom` (
  `chatroom_id` bigint NOT NULL AUTO_INCREMENT,
  `owner` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`chatroom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chatting`.`user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`),
  UNIQUE KEY `UK_n4swgcf30j6bmtb4l4cjryuym` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chatting`.`chatting` (
  `chatroom_id` bigint DEFAULT NULL,
  `chatting_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`chatting_id`),
  KEY `FK9egqjagsko4p1uidnfqol6bta` (`chatroom_id`),
  KEY `FKq6nviowmr99ttn5pcxy1xcrrt` (`user_id`),
  CONSTRAINT `FK9egqjagsko4p1uidnfqol6bta` FOREIGN KEY (`chatroom_id`) REFERENCES `chatroom` (`chatroom_id`),
  CONSTRAINT `FKq6nviowmr99ttn5pcxy1xcrrt` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chatting`.`user_chatroom` (
  `chatroom_id` bigint DEFAULT NULL,
  `user_chatroom_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_chatroom_id`),
  KEY `FKhrufhecf4lthmybck55qddra7` (`chatroom_id`),
  KEY `FK9if7htlurn1a8m287hmla3y6k` (`user_id`),
  CONSTRAINT `FK9if7htlurn1a8m287hmla3y6k` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKhrufhecf4lthmybck55qddra7` FOREIGN KEY (`chatroom_id`) REFERENCES `chatroom` (`chatroom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
