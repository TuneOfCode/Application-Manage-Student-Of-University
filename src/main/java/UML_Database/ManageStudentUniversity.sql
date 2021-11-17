CREATE DATABASE ManageStudentUniversity character set UTF8 collate utf8_vietnamese_ci;
USE ManageStudentUniversity;

CREATE TABLE department (
	id varchar(10) PRIMARY KEY,
    name varchar(50) NOT NULL,
    phone varchar(15) NOT NULL
);

CREATE TABLE class(
	id varchar(10) PRIMARY KEY,
    name varchar(50) NOT NULL,
    school_year int NOT NULL,
    department_id varchar(10) NOT NULL,
    CONSTRAINT FK_class_department
	FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE student(
	id varchar(15) PRIMARY KEY,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    gender varchar(10) NOT NULL,
    academic_year int NOT NULL,
    address varchar(250) NOT NULL,
    class_id varchar(10) NOT NULL,
    CONSTRAINT FK_student_class
	FOREIGN KEY (class_id) REFERENCES class(id)
);

CREATE TABLE subject(
	id varchar(20) PRIMARY KEY,
    name varchar(50) NOT NULL,
    credits int NOT NULL,
    lessons int NOT NULL
);

CREATE TABLE score(
	subject_id varchar(20) NOT NULL,
    student_id varchar(15) NOT NULL,
    PRIMARY KEY(subject_id, student_id),
    score_midterm double NULL,
    score_endterm double NULL,
	Coefficient_10 double NULL,
	Coefficient_4 double NULL,
    CONSTRAINT FK_score_subject_student
	FOREIGN KEY (subject_id) REFERENCES subject(id),
    FOREIGN KEY (student_id) REFERENCES student(id)
);


