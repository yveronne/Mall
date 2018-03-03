/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de cr�ation :  03/03/2018 10:45:12                      */
/*==============================================================*/

drop database if exists SHOPDB;

create database SHOPDB;

use SHOPDB;

drop table if exists CATEGORIE;

drop table if exists FACTURE;

drop table if exists GESTIONNAIRE;

drop table if exists GESTIONSTOCK;

drop table if exists LISTE_FACTURE;

drop table if exists PHOTO;

drop table if exists PRODUIT;

/*==============================================================*/
/* Table : CATEGORIE                                            */
/*==============================================================*/
create table CATEGORIE
(
   IDCATEGORIE          integer unsigned not null auto_increment,
   NOMCATEGORIE         varchar(50) not null,
   primary key (IDCATEGORIE)
);

/*==============================================================*/
/* Table : FACTURE                                              */
/*==============================================================*/
create table FACTURE
(
   IDFACTURE            integer unsigned not null auto_increment,
   IDGEST               integer unsigned not null,
   DATEFACTURE          datetime,
   REMISE               decimal(2,2) not null default 0,
   MONTANT              decimal(10,2) not null,
   TYPEFACT             bool,
   primary key (IDFACTURE)
);

/*==============================================================*/
/* Table : GESTIONNAIRE                                         */
/*==============================================================*/
create table GESTIONNAIRE
(
   IDGEST               integer unsigned not null auto_increment,
   NOM                  varchar(30) not null,
   TYPEGEST             bool,
   USERNAME             varchar(30) not null,
   PASSWORD             varchar(30) not null,
   ACTIF                bool,
   TELEPHONE            varchar(12) not null,
   EMAIL                varchar(100),
   primary key (IDGEST)
);

/*==============================================================*/
/* Table : GESTIONSTOCK                                         */
/*==============================================================*/
create table GESTIONSTOCK
(
   IDSTOCK              integer unsigned not null auto_increment,
   IDGEST               integer unsigned not null,
   CODEPRODUIT          integer unsigned not null,
   QUANTITE             integer unsigned not null,
   DATESTOCK            datetime,
   TYPEGEST             bool,
   primary key (IDSTOCK)
);

/*==============================================================*/
/* Table : LISTE_FACTURE                                        */
/*==============================================================*/
create table LISTE_FACTURE
(
   CODEPRODUIT          integer unsigned not null,
   IDFACTURE            integer unsigned not null,
   IDLFACTURE			integer unsigned not null auto_increment unique,
   QUANTITE             integer unsigned not null default 0,
   PRIX                 decimal(8,2) not null,
   primary key (CODEPRODUIT, IDFACTURE)
);

/*==============================================================*/
/* Table : PHOTO                                                */
/*==============================================================*/
create table PHOTO
(
   IDPHOTO              integer unsigned not null auto_increment,
   CODEPRODUIT          integer unsigned not null,
   LIEN                 varchar(150),
   primary key (IDPHOTO)
);

/*==============================================================*/
/* Table : PRODUIT                                              */
/*==============================================================*/
create table PRODUIT
(
   CODEPRODUIT          integer unsigned not null auto_increment ,
   /*IDSTOCK              integer unsigned not null,*/
   IDCATEGORIE          integer unsigned not null,
   PRIX                 decimal(8,2) not null,
   QUANTITE             integer unsigned not null,
   DESCRIPTIONS         varchar(100) not null,
   NOM                  varchar(100) not null default 'ND',
   CODEFOURNISSEUR      varchar(12),
   ACTIF                bool,
   primary key (CODEPRODUIT)
);

alter table PHOTO auto_increment=120000;

alter table CATEGORIE auto_increment=200000;

alter table FACTURE auto_increment=1000000;

alter table GESTIONNAIRE auto_increment=120;

alter table GESTIONSTOCK auto_increment=300000;

alter table LISTE_FACTURE auto_increment=500000;

alter table FACTURE add constraint FK_EMETTRE foreign key (IDGEST)
      references GESTIONNAIRE (IDGEST) on delete restrict on update restrict;

alter table GESTIONSTOCK add constraint FK_AJOURNER foreign key (IDGEST)
      references GESTIONNAIRE (IDGEST) on delete restrict on update restrict;

alter table GESTIONSTOCK add constraint FK_ASSOCIER foreign key (CODEPRODUIT)
      references PRODUIT (CODEPRODUIT) on delete restrict on update restrict;

alter table LISTE_FACTURE add constraint FK_LISTE_FACTURE foreign key (CODEPRODUIT)
      references PRODUIT (CODEPRODUIT) on delete restrict on update restrict;

alter table LISTE_FACTURE add constraint FK_LISTE_FACTURE2 foreign key (IDFACTURE)
      references FACTURE (IDFACTURE) on delete restrict on update restrict;

alter table PHOTO add constraint FK_LIER foreign key (CODEPRODUIT)
      references PRODUIT (CODEPRODUIT) on delete restrict on update restrict;

alter table PRODUIT add constraint FK_ASSOCIATION foreign key (IDCATEGORIE)
      references CATEGORIE (IDCATEGORIE) on delete restrict on update restrict;