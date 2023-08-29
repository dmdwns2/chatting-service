CREATE DATABASE chatting;

CREATE TABLE `chatting`.`chat_room` (
  `chatroom` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`chatroom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chatting`.`user` (
  `user` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `chatting`.`chatting` (
  `chatting` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `message` text,
  `chatroom` bigint DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  PRIMARY KEY (`chatting`),
  KEY `FKdgo21mjg66q9p4ohmj7lx88fm` (`chatroom`),
  KEY `FKn5t28v7sqnga39uxhwqcjtxu8` (`user`),
  CONSTRAINT `FKdgo21mjg66q9p4ohmj7lx88fm` FOREIGN KEY (`chatroom`) REFERENCES `chat_room` (`chatroom`),
  CONSTRAINT `FKn5t28v7sqnga39uxhwqcjtxu8` FOREIGN KEY (`user`) REFERENCES `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `chatting`.`user_chat_room` (
  `user_chatting` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `chatroom` bigint DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  PRIMARY KEY (`user_chatting`),
  KEY `FKr5vgy1wp8ldg8c8jq5ugye0a0` (`chatroom`),
  KEY `FK55ucaaqddy20i0h81ogywfxv5` (`user`),
  CONSTRAINT `FK55ucaaqddy20i0h81ogywfxv5` FOREIGN KEY (`user`) REFERENCES `user` (`user`),
  CONSTRAINT `FKr5vgy1wp8ldg8c8jq5ugye0a0` FOREIGN KEY (`chatroom`) REFERENCES `chat_room` (`chatroom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

