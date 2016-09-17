insert into game values
(default, 'Fifa 16', 499.99, 'sport', 'ps', '2015-10-10', 8, 50, 'medium'),
(default, 'World of Warcraft', 300.99, 'adventure', 'pc', '1998-10-10', 12, 100, 'low'),
(default, 'Company of Heroes', 200, 'shooter', 'pc', '2013-10-10', 12, 50, 'medium'),
(default, 'Xcom 2', 299.99, 'adventure', 'pc', '2005-10-10', 8, 50, 'low'),
(default, 'heartsone', 1.99, 'sport', 'pc', '2008-10-10', 8, 50, 'low'),
(default, 'Warhammer 4k invasion', 399.99, 'shooter', 'ps', '2003-10-10', 8, 50, 'high'),
(default, 'Fifa 15', 399.99, 'sport', 'ps', '2015-10-10', 8, 50, 'low'),
(default, 'Halo', 399.99, 'shooter', 'xbox', '2015-10-10', 8, 50, 'medium'),
(default, 'Diablo II', 199.99, 'rpg', 'ps', '2000-10-10', 8, 50, 'low');

insert into customer values
(default, 'john', 'ham'),
(default, 'collin', 'comma');

insert into staff values
(default, 'dogaru', 'andrei', 'mysql'),
(default, 'mac', 'tilmann', 'badgrandpa'),
(default, 'collin', 'hillarious', 'collinhilarious'),
(default, 'saxo', 'merrild', 'saxon751');

insert into cart values
(default, 1, 1),
(default, 2, 2);

insert into games_by_card_id values
(1, 3, 1),
(1, 4, 1),
(1, 5, 2),
(2, 7, 3),
(2, 8, 1);

select * from game;

select * from customer;

select * from staff;

select * from cart;

select * from games_by_card_id;

SELECT 
    g.name, g.game_id,gc.game_qty, g.price * gc.game_qty AS total_price, c.cart_id, cu.first_name as customer, s.first_name as employee
FROM
    game g
        JOIN
    games_by_card_id gc ON g.game_id = gc.game_id
        JOIN
    cart c ON gc.cart_id = c.cart_id
        JOIN
    customer cu ON cu.customer_id = c.customer_id
		join
	staff s on c.staff_id = s.staff_id;
    
    
SELECT 
    SUM(g.price * gc.game_qty) AS total, c.cart_id
FROM
    game g
        JOIN
    games_by_card_id gc ON g.game_id = gc.game_id
        JOIN
    cart c ON gc.cart_id = c.cart_id
        JOIN
    customer cu ON cu.customer_id = c.customer_id where c.customer_id = 1
GROUP BY c.cart_id;

SELECT 
    g.name, gc.game_qty, g.price * gc.game_qty AS total_price
FROM
    game g
        JOIN
    games_by_card_id gc ON g.game_id = gc.game_id
        JOIN
    cart c ON gc.cart_id = c.cart_id
        JOIN
    customer cu ON cu.customer_id = c.customer_id
WHERE
    c.cart_id = (SELECT 
            MAX(cart_id)
        FROM
            cart);
            
SELECT 
    SUM(g.price * gc.game_qty) AS total
FROM
    game g
        JOIN
    games_by_card_id gc ON g.game_id = gc.game_id
        JOIN
    cart c ON gc.cart_id = c.cart_id
        JOIN
    customer cu ON cu.customer_id = c.customer_id
WHERE
    c.cart_id = (SELECT 
            MAX(cart_id)
        FROM
            cart)
GROUP BY c.cart_id;


