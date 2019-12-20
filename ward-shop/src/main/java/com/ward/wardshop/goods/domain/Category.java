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

    public void setRootGroup(Integer ordering) {
        moveGroupAfter(new CategoryGroup(this.idx, 1, ordering));
    }

    public void moveGroupAfter(CategoryGroup preSibling) {
        this.categoryGroup.changeGroup(preSibling);
    }

    public void changeName(String categoryName) {
        this.name = categoryName;
    }
}
