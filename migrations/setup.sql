USE test;
CREATE TABLE IF NOT EXISTS be21131_Credentials
(
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(50)
);

INSERT INTO be21131_Credentials(Username, PASSWORD)
VALUES ("be21123", "abcd");

INSERT INTO be21131_Credentials(Username, PASSWORD)
VALUES ("be21124", "wxyz");