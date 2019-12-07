package com.ward.wardshop.goods.domain;

import javax.persistence.*;

@Entity
public class ProductDetailComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    /**
     * TEXT, IMAGE
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ComponentType componentType;

    @Column(nullable = false)
    private String data;

    @Column(nullable = false)
    private Integer ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    private Product product;
}
