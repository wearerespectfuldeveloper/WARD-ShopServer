package com.ward.wardshop;

import com.ward.wardshop.goods.domain.Category;
import com.ward.wardshop.goods.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Profile("local")
@Component
public class LocalCommandLineRunner implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    /*
    a
        b
            c
            d
        e
            f
            g
    h
        i
        j
     */
    @Override
    public void run(String... args) throws Exception {
        Category a = new Category("a");
        Category b = new Category("b");
        Category c = new Category("c");
        Category d = new Category("d");

        Category e = new Category("e");
        Category f = new Category("f");
        Category g = new Category("g");

        Category h = new Category("h");
        Category i = new Category("i");
        Category j = new Category("j");

        a.changeSequence(1);
        h.changeSequence(2);

        a.addChildCategory(b, 1);
        b.addChildCategory(c, 1);
        b.addChildCategory(d, 2);

        a.addChildCategory(e, 2);
        e.addChildCategory(f, 1);
        e.addChildCategory(g, 2);

        h.addChildCategory(i, 1);
        h.addChildCategory(j, 2);

        categoryRepository.save(a);
        categoryRepository.save(h);
    }
}
