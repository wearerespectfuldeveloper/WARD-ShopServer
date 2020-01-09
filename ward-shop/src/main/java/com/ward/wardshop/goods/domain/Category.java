package com.ward.wardshop.goods.domain;

import com.ward.wardshop.common.audit.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    private Integer sequence = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parentCategoryIdx")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childrenCategories = new ArrayList<>();


    public Category(String name) {
        this.name = name;
    }

    public void addSequence(int val) {
        this.sequence += val;
    }

    public void changeName(String categoryName) {
        this.name = categoryName;
    }

    public void moveToParentCategory(Category parentCategory, int sequence) {
        subSequenceAfterSibling();

        if (Objects.nonNull(parentCategory)) {
            this.parentCategory = parentCategory;
            parentCategory.getChildrenCategories().stream()
                    .filter(c -> c.getSequence() >= sequence)
                    .forEach(c -> c.addSequence(1));
        } else {
            this.parentCategory = null;
        }

        this.sequence = sequence;
    }

    private void subSequenceAfterSibling() {
        this.parentCategory.getChildrenCategories().stream()
                .filter(c -> c.sequence > this.sequence)
                .forEach(c -> c.addSequence(-1));
    }
}
