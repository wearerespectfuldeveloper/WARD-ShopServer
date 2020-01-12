package com.ward.wardshop.goods.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50, nullable = false)
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

    /**
     * 최상위 루트 카테고리를 위한 더미 인스턴스
     * @param children
     * 부모 카테고리가 Null 인 최상위 루트 카테고리 리스트
     */
    private Category(List<Category> children) {
        this.childCategories = children;
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

        if (Objects.nonNull(this.name)) {
            newChild.changeParent(this);
        }
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

    public static class Root {
        public static Category create(List<Category> children) {
            return new Category(children);
        }
    }
}
