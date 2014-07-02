DROP TABLE IF EXISTS `to_do_item_table;
CREATE TABLE `to_do_item_table` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(50) NULL DEFAULT NULL,
	`description` VARCHAR(50) NULL DEFAULT NULL,
	`dueDate` DATE NULL DEFAULT NULL,
	`createdDate` DATE NULL DEFAULT NULL,
	`updatedDate` DATE NULL DEFAULT NULL,
	`priority` ENUM('CRITICAL','RUSH','NORMAL','LOW') NULL DEFAULT NULL,
	INDEX `id` (`id`)
)
ENGINE=InnoDB;