# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table summoner_result (
  summoner_id               bigint,
  league_points             integer,
  match_id                  bigint,
  win                       boolean,
  date                      timestamp)
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists summoner_result;

SET REFERENTIAL_INTEGRITY TRUE;

