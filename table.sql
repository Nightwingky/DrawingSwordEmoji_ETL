CREATE TABLE `tb_dialogue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` varchar(10) DEFAULT NULL,
  `variance` double DEFAULT NULL,
  `average` double DEFAULT NULL,
  `min` double DEFAULT NULL,
  `words` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32193 DEFAULT CHARSET=utf8

select * from tb_dialogue;

select min(id) as minid, max(id) as maxid, words as words, count(*) as count
from tb_dialogue
group by words
having count(*) > 1;

create table tb_dialogue_temp(select min(id) as minid, max(id) as maxid, words as words, count(*) as count
    from tb_dialogue
    group by words
    having count(*) > 1
);

select * from tb_dialogue_temp;

select dia.*
from tb_dialogue dia, tb_dialogue_temp diat
where dia.words = diat.words
and dia.id != diat.minid;

create table dia_repeat (select dia.*
    from tb_dialogue dia, tb_dialogue_temp diat
    where dia.words = diat.words
    and dia.id != diat.minid
);

select * from dia_repeat;

delete from tb_dialogue
where id in (select id from dia_repeat);