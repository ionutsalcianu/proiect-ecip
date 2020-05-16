package ro.ecip.proiect.stockimporter.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products_stock")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @Column(name = "p_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "p_price")
    private Long price;

    @Column(name = "p_name")
    private String name;

    @Column(name = "p_quantity")
    private Long quantity;
}
