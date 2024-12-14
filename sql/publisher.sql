DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS user_group;
DROP TABLE IF EXISTS "group";
DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	username text NOT NULL,
	password text NOT NULL,
	about_myself text NOT NULL,
	roles text[]
);

CREATE TABLE "group" (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name text NOT NULL,
	creator_id bigint REFERENCES "user"(id),
	description text
);

CREATE TABLE user_group (
	user_id bigint REFERENCES "user"(id),
	group_id bigint REFERENCES "group"(id)
);

CREATE TABLE post (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	author_id bigint REFERENCES "user"(id),
	group_id bigint REFERENCES "group"(id),
	title text NOT NULL,
	content text,
	creation_date date NOT NULL,
	tags text[],
	views bigint CHECK(views >= 0),
	likes bigint CHECK(likes >= 0),
	dislikes bigint CHECK(dislikes >= 0)
);

CREATE TABLE comment (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	content text,
	author_id bigint REFERENCES "user"(id),
	post_id bigint REFERENCES post(id),
	parent_id bigint REFERENCES comment(id)
);