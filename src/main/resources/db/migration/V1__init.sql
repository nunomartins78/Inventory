CREATE TABLE items (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            min_stock INTEGER NOT NULL
);

CREATE TABLE stock_movements (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            item_id INTEGER NOT NULL,
            type TEXT NOT NULL,
            quantity INTEGER NOT NULL,
            created_at INTEGER NOT NULL,
            FOREIGN KEY (item_id) REFERENCES items(id)
);
