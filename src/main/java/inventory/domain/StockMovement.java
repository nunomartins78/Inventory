package inventory.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table (name = "stock_movements")

public class StockMovement {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    private int quantity;

    @Column(name = "created_at", nullable = false)
    @Convert(converter = InstantToLongConverter.class)
    private Instant createdAt;

    protected StockMovement() {}

    public StockMovement (Item item, MovementType type, int quantity, Instant createdAt){
        this.item = item;
        this.type = type;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Long getId (){
        return id;
    }

    public Item getItem() {
        return item;
    }

    public MovementType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
