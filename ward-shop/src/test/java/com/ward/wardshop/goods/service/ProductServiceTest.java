package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.api.model.ProductForm;
import com.ward.wardshop.goods.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @BeforeEach
    void setup() {
    }

    @Test
    void createTest() {
        //when
        ProductForm productForm = new ProductForm();
    }
}