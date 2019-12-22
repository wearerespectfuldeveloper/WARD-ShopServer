package com.ward.wardshop.goods.domain;

import com.ward.wardshop.common.audit.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50)
    private String name;

    @Embedded
    private CategoryGroup categoryGroup = new CategoryGroup();

    public Category(String name) {
        this.name = name;
    }

    public void setRootGroupAfter(Integer preSiblingOrder) {
        this.categoryGroup =
                CategoryGroup.builder()
                        .group_idx(this.getIdx())
                        .level(1)
                        .ordering(preSiblingOrder + 1)
                        .build();
    }

    public void moveGroupAfter(CategoryGroup preSibling) {
        this.categoryGroup = CategoryGroup.builder()
                .group_idx(preSibling.getGroup_idx())
                .level(preSibling.getLevel())
                .ordering(preSibling.getOrdering())
                .build();

        this.categoryGroup.addOrder(1);
    }

    public void addOrder(int val) {
        this.categoryGroup.addOrder(val);
    }

    public void changeName(String categoryName) {
        this.name = categoryName;
    }
}
