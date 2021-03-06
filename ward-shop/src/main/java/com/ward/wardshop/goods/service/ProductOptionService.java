package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.product.Product;
import com.ward.wardshop.goods.domain.product.ProductOption;
import com.ward.wardshop.goods.repository.ProductOptionRepository;
import com.ward.wardshop.goods.repository.ProductRepository;
import com.ward.wardshop.goods.service.dto.request.CreateOptionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductOptionService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public Long createOption(Long productIdx, CreateOptionForm form) {
        Product product = productRepository.findProductByIdFetchJoinOptions(productIdx);
        product.rearrangeSequenceAfter(form.getSequence());

        ProductOption newOption = new ProductOption(form.getName(), form.getPrice(), form.getSequence());
        product.addOption(newOption);

        return newOption.getIdx();
    }

    @Transactional
    public void deleteOption(Long productId, Long productOptionId) {
        Product product = productRepository.findProductByIdFetchJoinOptions(productId);
        product.deleteOption(productOptionId);
        product.rearrangeOption();
    }
}
