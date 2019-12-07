package com.ward.wardshop.goods.domain.product;

import com.ward.wardshop.common.audit.BaseEntity;

import javax.persistence.*;

@Entity
public class ProductDetailComponent extends BaseEntity {

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
