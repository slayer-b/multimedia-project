INSERT INTO `users` (`id`,`name`,`email`,`login`,`password`,`last_accessed`) VALUES
(1,'admin','admin@download-multimedia.com','admin','7815696ecbf1c96e6894b779456d330e','2010-12-30 21:11:21');

INSERT INTO `roles` (`id`,`id_users`,`role`) VALUES
(2,1,'admin'),
(3,1,'user');