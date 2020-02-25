package com.ward.wardshop.goods.domain.product;

import com.ward.wardshop.common.audit.BaseEntity;
import com.ward.wardshop.goods.api.model.ProductUpdateForm;
import com.ward.wardshop.goods.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    private String imageResource;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

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

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void createImageResource(String filePath) {
        this.imageResource = filePath;
    }

    public void changeStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void updateData(ProductUpdateForm form) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
        this.productStatus = form.getStatus();
    }

    public void rearrangeSequenceAfter(Integer sequence) {

    }

    public void addOption(ProductOption option) {
        this.options.add(option);
        option.setProduct(this);
    }

    public void deleteOption(Long productOptionId) {
        options.removeIf(option -> option.getIdx().equals(productOptionId));
    }

    public void rearrangeOption() {
        int i = 0;
        this.options.sort(Comparator.comparing(ProductOption::getSequence));

        for (ProductOption option : options) {
            option.changeSequence(i++);
        }
    }
}
