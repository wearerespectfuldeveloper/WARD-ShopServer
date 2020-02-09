package com.ward.wardshop.goods.domain.product;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    private Integer price;

    @Column(nullable = false)
    private Integer sequence = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_idx")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }
}
