# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table monitored_summoner (
  summoner_id               bigint not null,
  last_check                timestamp,
  constraint pk_monitored_summoner primary key (summoner_id))
;

create table monitored_summoner_stats (
  match_id                  bigint,
  monitored_summoner_id     bigint,
  win                       boolean,
  league_points             integer,
  tier                      varchar(255),
  division                  varchar(255),
  match_date                timestamp)
;

create table summoner_result (
  summoner_id               bigint,
  league_points             integer,
  match_id                  bigint,
  win                       boolean,
  date                      timestamp)
;

create sequence monitored_summoner_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists monitored_summoner;

drop table if exists monitored_summoner_stats;

drop table if exists summoner_result;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists monitored_summoner_seq;

