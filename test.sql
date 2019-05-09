CREATE TABLE IF NOT EXISTS `t_order` (
  `id` varchar(128) NOT NULL,
  `name` varchar(128),
  `message_id` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `broker_message_log` (
  `message_id` varchar(128) NOT NULL,
  `message` varchar(4000) DEFAULT NULL,
  `try_count` int(4) DEFAULT '0',
  `status` varchar(10) DEFAULT '',
  `next_retry` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY(`message_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;