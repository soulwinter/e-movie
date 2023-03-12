/*==============================================================*/
/* DBMS name:      ORACLE Version 12c                           */
/* Created on:     2023/3/12 20:43:18                           */
/*==============================================================*/


alter table "Movie_Company"
   drop constraint FK_MOVIE_CO_MOVIE_COM_MOVIE;

alter table "Movie_Company"
   drop constraint FK_MOVIE_CO_MOVIE_COM_COMPANY;

alter table "Movie_Country"
   drop constraint FK_MOVIE_CO_MOVIE_COU_MOVIE;

alter table "Movie_Country"
   drop constraint FK_MOVIE_CO_MOVIE_COU_COUNTRY;

alter table "Movie_Genre"
   drop constraint FK_MOVIE_GE_MOVIE_GEN_MOVIE;

alter table "Movie_Genre"
   drop constraint FK_MOVIE_GE_REFERENCE_GENRE;

alter table "Movie_Keyword"
   drop constraint FK_MOVIE_KE_MOVIE_KEY_MOVIE;

alter table "Movie_Keyword"
   drop constraint FK_MOVIE_KE_MOVIE_KEY_KEYWORD;

drop table "Company" cascade constraints;

drop table "Country" cascade constraints;

drop table "Genre" cascade constraints;

drop table "Keyword" cascade constraints;

drop table "Movie" cascade constraints;

drop table "Movie_Company" cascade constraints;

drop table "Movie_Country" cascade constraints;

drop table "Movie_Genre" cascade constraints;

drop table "Movie_Keyword" cascade constraints;

drop table "User" cascade constraints;

drop table "Vote" cascade constraints;

/*==============================================================*/
/* Table: "Company"                                             */
/*==============================================================*/
create table "Company" (
   "id"                 NUMBER                not null,
   "name"               VARCHAR2(200),
   "deleted"            NUMBER,
   constraint PK_COMPANY primary key ("id")
);

/*==============================================================*/
/* Table: "Country"                                             */
/*==============================================================*/
create table "Country" (
   "id"                 NUMBER                not null,
   "name"               VARCHAR2(200),
   "deleted"            NUMBER,
   constraint PK_COUNTRY primary key ("id")
);

/*==============================================================*/
/* Table: "Genre"                                               */
/*==============================================================*/
create table "Genre" (
   "id"                 NUMBER                not null,
   "name"               VARCHAR2(200)         not null,
   "deleted"            NUMBER,
   constraint PK_GENRE primary key ("id")
);

/*==============================================================*/
/* Table: "Keyword"                                             */
/*==============================================================*/
create table "Keyword" (
   "id"                 NUMBER                not null,
   "name"               VARCHAR2(0),
   "deleted"            NUMBER,
   constraint PK_KEYWORD primary key ("id")
);

/*==============================================================*/
/* Table: "Movie"                                               */
/*==============================================================*/
create table "Movie" (
   "id"                 NUMBER                not null,
   "adult"              NUMBER,
   "budget"             NUMBER,
   "homepage"           VARCHAR2(200),
   "imgb_id"            NUMBER,
   "original_language"  VARCHAR2(500),
   "original_title"     VARCHAR2(500),
   "overview"           VARCHAR2(500),
   "popularity"         NUMBER,
   "poster_path"        VARCHAR2(500),
   "release_date"       VARCHAR2(500),
   "revenue"            NUMBER,
   "runtime"            NUMBER,
   "status"             NUMBER,
   "tagline"            VARCHAR2(500),
   "title"              VARCHAR2(500),
   "vote_average"       NUMBER,
   "vote_count"         NUMBER,
   "allCrew"            VARCHAR2(500),
   "deleted"            NUMBER,
   constraint PK_MOVIE primary key ("id")
);

/*==============================================================*/
/* Table: "Movie_Company"                                       */
/*==============================================================*/
create table "Movie_Company" (
   "id"                 NUMBER,
   "Com_id"             NUMBER,
   "deleted"            NUMBER
);

/*==============================================================*/
/* Table: "Movie_Country"                                       */
/*==============================================================*/
create table "Movie_Country" (
   "id"                 NUMBER,
   "Cou_id"             NUMBER,
   "deleted"            NUMBER
);

/*==============================================================*/
/* Table: "Movie_Genre"                                         */
/*==============================================================*/
create table "Movie_Genre" (
   "id"                 NUMBER,
   "Gen_id"             NUMBER,
   "deleted"            NUMBER
);

/*==============================================================*/
/* Table: "Movie_Keyword"                                       */
/*==============================================================*/
create table "Movie_Keyword" (
   "id"                 NUMBER,
   "Key_id"             NUMBER,
   "deleted"            NUMBER
);

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table "User" (
   "id"                 INT                   not null,
   "username"           VARCHAR2(200 CHAR),
   "email"              VARCHAR2(200),
   "gender"             NUMBER,
   "deleted"            NUMBER,
   constraint PK_USER primary key ("id")
);

/*==============================================================*/
/* Table: "Vote"                                                */
/*==============================================================*/
create table "Vote" (
   "user_id"            NUMBER                not null,
   "movie_id"           NUMBER,
   "rating"             NUMBER,
   "timestamp"          NUMBER,
   "deleted"            NUMBER,
   constraint PK_VOTE primary key ("user_id")
);

alter table "Movie_Company"
   add constraint FK_MOVIE_CO_MOVIE_COM_MOVIE foreign key ("id")
      references "Movie" ("id");

alter table "Movie_Company"
   add constraint FK_MOVIE_CO_MOVIE_COM_COMPANY foreign key ("Com_id")
      references "Company" ("id");

alter table "Movie_Country"
   add constraint FK_MOVIE_CO_MOVIE_COU_MOVIE foreign key ("id")
      references "Movie" ("id");

alter table "Movie_Country"
   add constraint FK_MOVIE_CO_MOVIE_COU_COUNTRY foreign key ("Cou_id")
      references "Country" ("id");

alter table "Movie_Genre"
   add constraint FK_MOVIE_GE_MOVIE_GEN_MOVIE foreign key ("id")
      references "Movie" ("id");

alter table "Movie_Genre"
   add constraint FK_MOVIE_GE_REFERENCE_GENRE foreign key ("Gen_id")
      references "Genre" ("id");

alter table "Movie_Keyword"
   add constraint FK_MOVIE_KE_MOVIE_KEY_MOVIE foreign key ("id")
      references "Movie" ("id");

alter table "Movie_Keyword"
   add constraint FK_MOVIE_KE_MOVIE_KEY_KEYWORD foreign key ("Key_id")
      references "Keyword" ("id");

