package com.ward.wardshop.goods.domain.product;

import com.ward.wardshop.common.audit.BaseEntity;
import com.ward.wardshop.goods.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer price;

    private Integer stockQuantity;

    private ImageResource imageResource;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private Category category;

    @Builder
    public Product(String name,
                   String description,
                   Integer price,
                   Integer stockQuantity,
                   Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public void createImageResource(String fileName) {
        this.imageResource = ImageResource.generateFileName(fileName);
    }

    public void changeStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }
}
