insert into listOfGenres
(mId, listGenres)
select gim.movieId, group_concat(g.name separator ', ') as listOfGenres from genres_in_movies gim
join genres g on gim.genreId=g.id
group by gim.movieId;

insert into listOfStars
(movieId, listStars)
select sim.movieId, group_concat(s.name separator ', ') as listOfStars from stars_in_movies sim
join stars s on sim.starId = s.id
group by sim.movieId;

CREATE TABLE movieList
select m.id, m.title, m.year, m.director, g.listGenres, s.listStars, r.rating
from movies m join listOfGenres g on m.id = g.mId join listOfStars s on m.id = s.movieId join ratings r on m.id=r.movieId;

create fulltext index title_ft1_idx on movieList(title);

CREATE TABLE movieList2
select m.id, m.title, m.year, m.director, g.listGenres, s.listStars, r.rating
from movies m join listOfGenres g on m.id = g.mId join listOfStars s on m.id = s.movieId left join ratings r on m.id=r.movieId ;

create fulltext index title_ft2_idx on movieList2(title);

CREATE TABLE Action
select * from movieList2 where listGenres LIKE '%Action%';

CREATE TABLE Adult
select * from movieList2 where listGenres LIKE '%Adult%';

CREATE TABLE Adventure
select * from movieList2 where listGenres LIKE '%Adventure%';

CREATE TABLE Animation
select * from movieList2 where listGenres LIKE '%Animation%';

CREATE TABLE Biography
select * from movieList2 where listGenres LIKE '%Biography%';

CREATE TABLE Comedy
select * from movieList2 where listGenres LIKE '%Comedy%';

CREATE TABLE Crime
select * from movieList2 where listGenres LIKE '%Crime%';

CREATE TABLE Documentary
select * from movieList2 where listGenres LIKE '%Documentary%';

CREATE TABLE Drama
select * from movieList2 where listGenres LIKE '%Drama%';

CREATE TABLE Family
select * from movieList2 where listGenres LIKE '%Family%';

CREATE TABLE Fantasy
select * from movieList2 where listGenres LIKE '%Fantasy%';

CREATE TABLE History
select * from movieList2 where listGenres LIKE '%History%';

CREATE TABLE Horror
select * from movieList2 where listGenres LIKE '%Horror%';

CREATE TABLE Music
select * from movieList2 where listGenres LIKE '%Music%';

CREATE TABLE Musical
select * from movieList2 where listGenres LIKE '%Musical%';

CREATE TABLE Mystery
select * from movieList2 where listGenres LIKE '%Mystery%';

CREATE TABLE `Reality-TV`
select * from movieList2 where listGenres LIKE '%Reality-TV%';

CREATE TABLE Romance
select * from movieList2 where listGenres LIKE '%Romance%';

CREATE TABLE `Sci-Fi`
select * from movieList2 where listGenres LIKE '%Sci-Fi%';

CREATE TABLE Sport
select * from movieList2 where listGenres LIKE '%Sport%';

CREATE TABLE Thriller
select * from movieList2 where listGenres LIKE '%Thriller%';

CREATE TABLE War
select * from movieList2 where listGenres LIKE '%War%';

CREATE TABLE `moviedb`.`employees` (
  `email` VARCHAR(50) NOT NULL DEFAULT '',
  `password` VARCHAR(20) NOT NULL DEFAULT '',
  `fullname` VARCHAR(100),
  PRIMARY KEY (`email`));

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_movie`(IN genreee varchar(32))
BEGIN
	truncate listOfGenres;
    truncate listOfStars;
    drop table if exists movieList2;
    insert into listOfGenres (mId, listGenres) select gim.movieId, group_concat(g.name separator ', ') as listOfGenres 
    from genres_in_movies gim join genres g on gim.genreId=g.id group by gim.movieId;
    insert into listOfStars (movieId, listStars) select sim.movieId, group_concat(s.name separator ', ') as listOfStars 
    from stars_in_movies sim join stars s on sim.starId = s.id group by sim.movieId;
    CREATE TABLE movieList2 select m.id, m.title, m.year, m.director, g.listGenres, s.listStars, r.rating from 
    movies m join listOfGenres g on m.id = g.mId join listOfStars s on m.id = s.movieId left join ratings r on m.id=r.movieId;
END$$
DELIMITER ;

insert into employees values('classta@email.edu', 'classta', 'TA CS122');
