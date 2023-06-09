## 댓글 테이블 설정
CREATE TABLE reply(
                      reply_id int primary key auto_increment,
                      blog_id int not null,
                      reply_writer varchar(40) not null,
                      reply_content varchar(200) not null,
                      published_at datetime default now(),
                      updated_at datetime default now()
);
# 외래키 설정
# blog_id에는 기존에 존재하는 글의 blog_id만 들어가야 한다.
alter table reply add constraint fk_reply foreign key (blog_id) references blog(blog_id);

# 더미 데이터 입력
INSERT INTO reply VALUES
                      (null, 2, "댓글쓴사람", "1빠댓글이다~~",now(),now()),
                      (null, 2, "강아지", "월월이다~~",now(),now()),
                      (null, 2, "고양이", "미야옹이다~~",now(),now()),
                      (null, 3, "호랑이", "어흥이다~~",now(),now()),
                      (null, 3, "돼지", "밥줘라~",now(),now());