CREATE TABLE IF NOT EXISTS items (
    id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    min_stock INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS stock_movements (
    id BIGINT PRIMARY KEY,
    item_id BIGINT NOT NULL,
    type TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    created_at BIGINT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE INDEX IF NOT EXISTS idx_stock_movements_item_id ON stock_movements(item_id);
CREATE INDEX IF NOT EXISTS idx_stock_movements_created_at ON stock_movements(created_at);
