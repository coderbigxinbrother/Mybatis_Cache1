create table student(
    sid number primary key,
    sname varchar2(20),
    sage number,
    sex number,
    address varchar2(100)
)

create sequence seq_student_sid;

drop sequence seq_student_sid;