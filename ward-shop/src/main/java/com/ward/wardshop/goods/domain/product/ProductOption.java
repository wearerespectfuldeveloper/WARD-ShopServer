package com.ward.wardshop.goods.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
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

    public ProductOption(String name, Integer price, Integer sequence) {
        this.name = name;
        this.price = price;
        this.sequence = sequence;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getIdx() {
        return idx;
    }

    public void changeSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
