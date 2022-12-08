--시도코드 엑셀파일로 임포트 완료--
DESC AREA;
SELECT * FROM AREA;
SELECT * FROM AREA WHERE AREA_NAME LIKE '%경기%';

-- 카테고리 데이터 insert
desc category;
drop sequence class_sequence;
create SEQUENCE class_sequence start with 1 INCREMENT BY 1;
insert into category values(class_sequence.nextval ,'요리');
insert into category values(class_sequence.nextval ,'수공예');
insert into category values(class_sequence.nextval ,'플라워');
insert into category values(class_sequence.nextval ,'미술');
insert into category values(class_sequence.nextval ,'음악');
insert into category values(class_sequence.nextval ,'액티비티');
insert into category values(class_sequence.nextval ,'뷰티');
insert into category values(class_sequence.nextval ,'라이프스타일');
commit;
select * from category;

--크롤링 class 테이블 데이터 insert 확인
select * from class;









--메인,목록 페이지의 클래스 목록 표시 정보
select CLASS_IMG, CLASS_NAME, AREA_NAME, CLASS_PRICE
    from CLASS join AREA using(AREA_CODE)
    where CLASS_NAME = '키워드' or CLASS_INTRO = '키워드';
--카테고리 선택 시 클래스 정보
select CLASS_IMG, CLASS_NAME, AREA_NAME, CLASS_PRICE
    from CLASS join AREA using(AREA_CODE) join CATEGORY using(category_code)
    where category_name = '카테고리';
--키워드 검색 시 
select CLASS_IMG, CLASS_NAME, AREA_NAME, CLASS_PRICE
    from CLASS join AREA using(AREA_CODE)
    where CLASS_NAME = '키워드';
--유형 선택 시
select CLASS_IMG, CLASS_NAME, AREA_NAME, CLASS_PRICE
    from CLASS join AREA using(AREA_CODE) 
                join CATEGORY using(category_code)
                join CLASS_OPTION using(class_code)
    where area_name = '지역' 
        and category_name = '카테고리' 
        and class_level = '1'
        and cs_day = 1
;
--
