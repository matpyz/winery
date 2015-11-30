-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 30 Lis 2015, 09:35
-- Wersja serwera: 5.6.27-0ubuntu0.14.04.1
-- Wersja PHP: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `zespolowe`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `documents`
--

CREATE TABLE IF NOT EXISTS `documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `document` blob,
  `creatorId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `documents_creator` (`creatorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `creatorId` int(10) unsigned NOT NULL,
  `eventTypeId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `event_event_type` (`eventTypeId`),
  KEY `event_creator` (`creatorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `eventType`
--

CREATE TABLE IF NOT EXISTS `eventType` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `group`
--

CREATE TABLE IF NOT EXISTS `group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `group2event`
--

CREATE TABLE IF NOT EXISTS `group2event` (
  `groupId` int(10) unsigned NOT NULL,
  `eventId` int(10) unsigned NOT NULL,
  `access` int(10) unsigned NOT NULL,
  KEY `group2event_event` (`eventId`),
  KEY `group2event_group` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `group2permission`
--

CREATE TABLE IF NOT EXISTS `group2permission` (
  `groupId` int(10) unsigned DEFAULT NULL,
  `permissionId` int(10) unsigned DEFAULT NULL,
  `access` int(11) NOT NULL,
  KEY `group2permission_permission` (`permissionId`),
  KEY `group2permission_group` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `migrations`
--

CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `run_on` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Zrzut danych tabeli `migrations`
--

INSERT INTO `migrations` (`id`, `name`, `run_on`) VALUES
(9, '/20151123195417-create-docs', '2015-11-30 09:30:31'),
(10, '/20151124180938-create-rbac', '2015-11-30 09:30:31'),
(11, '/20151124184751-document2user', '2015-11-30 09:30:31'),
(12, '/20151129204858-user-fix1', '2015-11-30 09:30:31'),
(13, '/20151130065408-calendar', '2015-11-30 09:30:31');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user2event`
--

CREATE TABLE IF NOT EXISTS `user2event` (
  `userId` int(10) unsigned NOT NULL,
  `eventId` int(10) unsigned NOT NULL,
  `access` int(10) unsigned NOT NULL,
  KEY `user2event_event` (`eventId`),
  KEY `user2event_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user2group`
--

CREATE TABLE IF NOT EXISTS `user2group` (
  `userId` int(10) unsigned DEFAULT NULL,
  `groupId` int(10) unsigned DEFAULT NULL,
  KEY `user2group_group` (`groupId`),
  KEY `user2group_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `documents_creator` FOREIGN KEY (`creatorId`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_creator` FOREIGN KEY (`creatorId`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `event_event_type` FOREIGN KEY (`eventTypeId`) REFERENCES `eventType` (`id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `group2event`
--
ALTER TABLE `group2event`
  ADD CONSTRAINT `group2event_event` FOREIGN KEY (`eventId`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group2event_group` FOREIGN KEY (`groupId`) REFERENCES `group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `group2permission`
--
ALTER TABLE `group2permission`
  ADD CONSTRAINT `group2permission_group` FOREIGN KEY (`groupId`) REFERENCES `group` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `group2permission_permission` FOREIGN KEY (`permissionId`) REFERENCES `permission` (`id`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `user2event`
--
ALTER TABLE `user2event`
  ADD CONSTRAINT `user2event_event` FOREIGN KEY (`eventId`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user2event_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `user2group`
--
ALTER TABLE `user2group`
  ADD CONSTRAINT `user2group_group` FOREIGN KEY (`groupId`) REFERENCES `group` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user2group_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
