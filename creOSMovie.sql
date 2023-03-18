/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/3/17 22:53:36                           */
/*==============================================================*/


drop table if exists Company;

drop table if exists Country;

drop table if exists Genre;

drop table if exists Keyword;

drop table if exists Movie;

drop table if exists Movie_Company;

drop table if exists Movie_Country;

drop table if exists Movie_Genre;

drop table if exists Movie_Keyword;

drop table if exists User;

drop table if exists Vote;

/*==============================================================*/
/* Table: Company                                               */
/*==============================================================*/
create table Company
(
   id                   numeric(8,0) not null,
   name                 varchar(200),
   deleted              numeric(8,0),
   primary key (id)
);

/*==============================================================*/
/* Table: Country                                               */
/*==============================================================*/
create table Country
(
   id                   numeric(8,0) not null,
   name                 varchar(200),
   deleted              numeric(8,0),
   primary key (id)
);

/*==============================================================*/
/* Table: Genre                                                 */
/*==============================================================*/
create table Genre
(
   id                   numeric(8,0) not null,
   name                 varchar(200) not null,
   deleted              numeric(8,0),
   primary key (id)
);

/*==============================================================*/
/* Table: Keyword                                               */
/*==============================================================*/
create table Keyword
(
   id                   numeric(8,0) not null,
   name                 varchar(0),
   deleted              numeric(8,0),
   primary key (id)
);

/*==============================================================*/
/* Table: Movie                                                 */
/*==============================================================*/
create table Movie
(
   id                   numeric(8,0) not null,
   adult                numeric(8,0),
   budget               numeric(8,0),
   homepage             varchar(200),
   imgb_id              numeric(8,0),
   original_language    varchar(500),
   original_title       varchar(500),
   overview             varchar(500),
   popularity           numeric(8,0),
   poster_path          varchar(500),
   release_date         varchar(500),
   revenue              numeric(8,0),
   runtime              numeric(8,0),
   status               numeric(8,0),
   tagline              varchar(500),
   title                varchar(500),
   vote_average         numeric(8,0),
   vote_count           numeric(8,0),
   allCrew              varchar(500),
   deleted              numeric(8,0),
   primary key (id)
);

/*==============================================================*/
/* Table: Movie_Company                                         */
/*==============================================================*/
create table Movie_Company
(
   id                   numeric(8,0),
   Com_id               numeric(8,0),
   deleted              numeric(8,0)
);

/*==============================================================*/
/* Table: Movie_Country                                         */
/*==============================================================*/
create table Movie_Country
(
   id                   numeric(8,0),
   Cou_id               numeric(8,0),
   deleted              numeric(8,0)
);

/*==============================================================*/
/* Table: Movie_Genre                                           */
/*==============================================================*/
create table Movie_Genre
(
   id                   numeric(8,0),
   Gen_id               numeric(8,0),
   deleted              numeric(8,0)
);

/*==============================================================*/
/* Table: Movie_Keyword                                         */
/*==============================================================*/
create table Movie_Keyword
(
   id                   numeric(8,0),
   Key_id               numeric(8,0),
   deleted              numeric(8,0)
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   id                   int not null auto_increment,
   username             varchar(20),
   telephone                varchar(20),
   password               varchar(35),
   deleted              numeric(8,0),
   primary key (id)
);

/*==============================================================*/
/* Table: Vote                                                  */
/*==============================================================*/
create table Vote
(
   user_id              numeric(8,0) not null,
   movie_id             numeric(8,0) not null,
   rating               numeric(8,0),
   timestamp            numeric(8,0),
   deleted              numeric(8,0),
   primary key (user_id, movie_id)
);

alter table Movie_Company add constraint FK_Movie_Company1 foreign key (id)
      references Movie (id) on delete restrict on update restrict;

alter table Movie_Company add constraint FK_Movie_Company2 foreign key (Com_id)
      references Company (id) on delete restrict on update restrict;

alter table Movie_Country add constraint FK_Movie_Country1 foreign key (id)
      references Movie (id) on delete restrict on update restrict;

alter table Movie_Country add constraint FK_Movie_Country2 foreign key (Cou_id)
      references Country (id) on delete restrict on update restrict;

alter table Movie_Genre add constraint FK_Movie_Genre1 foreign key (id)
      references Movie (id) on delete restrict on update restrict;

alter table Movie_Genre add constraint FK_Reference_4 foreign key (Gen_id)
      references Genre (id) on delete restrict on update restrict;

alter table Movie_Keyword add constraint FK_Movie_Keyword1 foreign key (id)
      references Movie (id) on delete restrict on update restrict;

alter table Movie_Keyword add constraint FK_Movie_Keyword2 foreign key (Key_id)
      references Keyword (id) on delete restrict on update restrict;

