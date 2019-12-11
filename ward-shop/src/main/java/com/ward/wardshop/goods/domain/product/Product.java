package com.ward.wardshop.goods.domain.product;

import com.ward.wardshop.common.audit.BaseEntity;
import com.ward.wardshop.goods.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String name;

    private String desc;

    private Integer price;

    private Integer stockQuantity;

    private String imagePath;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private Category category;
}
