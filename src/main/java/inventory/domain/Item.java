package inventory.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "items")

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INTEGER")
    private Long id;
    private String name;

    @Column(name = "min_stock")
    private int minStock;

    protected Item () {}

    public Item (String name, int minStock){
        this.name = name;
        this.minStock = minStock;
    }

    public Long getId (){
        return id;
    }

    public String getName (){
        return name;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setName (String name){
        this.name = name;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }
}
