INSERT INTO department(topic, default_department) VALUES
    ('Cartões', false),
    ('Empréstimos', false),
    ('Outros Assuntos', true);

insert into department_member(department_id, name, email) VALUES
    (1, 'Arthur', 'arthur@gmail.com'),
    (2, 'Carlos', 'carlos@gmail.com'),
    (3, 'Dourado', 'dourado@gmail.com');