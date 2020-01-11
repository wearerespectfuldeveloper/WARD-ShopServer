package com.ward.wardshop.goods.domain;

import com.ward.wardshop.common.audit.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
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

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> childCategories = new ArrayList<>();


    public Category(String name) {
        this.name = name;
    }

    public Long getIdx() {
        return idx;
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

    public void changeName(String categoryName) {
        this.name = categoryName;
    }

    public void addChildCategory(Category newChild, int sequence) {
        this.childCategories.stream()
                .filter(c -> c.getSequence() >= sequence)
                .forEach(c -> c.addSequence(1));

        newChild.changeParent(this);
        newChild.changeSequence(sequence);
        childCategories.add(newChild);
    }

    public void deleteParent() {
        if (Objects.nonNull(parentCategory)) {
            parentCategory.deleteChildCategory(this);
        }
    }

    public void deleteChildCategory(Category deleteChild) {
        this.childCategories.stream()
                .filter(c -> c.getSequence() > deleteChild.getSequence())
                .forEach(c -> c.addSequence(-1));

        childCategories.remove(deleteChild);
        deleteChild.changeParent(null);
    }

    private void changeParent(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
