package com.ward.wardshop.goods.domain.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ward.wardshop.goods.domain.Category;

import lombok.Builder;

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

    @Builder
    public ProductDetailComponent(String data,
                Integer sequence) {
        this.data = data;
        this.sequence = sequence;
    }

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

    public void changeSequence(int sequence) {
        this.sequence = sequence;
    }
}
