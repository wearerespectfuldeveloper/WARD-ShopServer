package com.ward.wardshop.goods.domain.product;

import javax.persistence.*;

/**
 * URL : www.comsdaf.com
 */
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
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_idx")
    private Product product;

    public Long getIdx() {
        return idx;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public String getData() {
        return data;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void addSequence(int val) {
        this.sequence += val;
    }
}
