CREATE DATABASE management_system;

USE management_system;

CREATE TABLE
    teams (
        ID int primary key auto_increment,
        name varchar(75)
    );

CREATE TABLE
    employees (
        ID int primary key auto_increment,
        first_name varchar(50),
        last_name varchar(50),
        email varchar(100),
        phone varchar(15),
        hire_date date,
        role varchar(50),
        team_id int,
        FOREIGN KEY (team_id) REFERENCES teams (ID) ON DELETE CASCADE
    );

-- CREATE TABLE
--     avatars (
--         employe_ID int primary key,
--         path varchar(255),
--         FOREIGN KEY (employe_ID) REFERENCES employees (ID) ON DELETE CASCADE
--     );
-- Example values

INSERT INTO
    teams (name)
VALUES
    ('Development Team'),
    ('Marketing Team'),
    ('Sales Team'),
    ('HR Team'),
    ('Design Team');

INSERT INTO
    employees (
        first_name,
        last_name,
        email,
        phone,
        hire_date,
        role,
        team_id
    )
VALUES
    (
        'John',
        'Doe',
        'john.doe@example.com',
        '123-456-7890',
        '2023-01-15',
        'Developer',
        1
    ),
    (
        'Jane',
        'Smith',
        'jane.smith@example.com',
        '234-567-8901',
        '2023-02-20',
        'Marketing Specialist',
        2
    ),
    (
        'Emily',
        'Johnson',
        'emily.johnson@example.com',
        '345-678-9012',
        '2023-03-10',
        'Sales Associate',
        3
    ),
    (
        'Michael',
        'Brown',
        'michael.brown@example.com',
        '456-789-0123',
        '2023-04-05',
        'HR Manager',
        4
    ),
    (
        'Sarah',
        'Davis',
        'sarah.davis@example.com',
        '567-890-1234',
        '2023-05-12',
        'UI/UX Designer',
        5
    );

-- admin
CREATE USER 'admin'@'%' IDENTIFIED VIA mysql_native_password USING 'QEC8u';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' 
REQUIRE NONE WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;