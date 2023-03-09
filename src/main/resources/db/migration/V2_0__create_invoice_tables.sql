CREATE TABLE IF NOT EXISTS aws_project01.invoice(
	id SERIAL NOT NULL,
	number VARCHAR(32) NOT NULL,
	customer_name VARCHAR(32) NOT NULL,
	total_value DECIMAL(10, 2) NOT NULL,
	product_id INT NOT NULL,
	quantity INT NOT NULL
);