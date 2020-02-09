package com.ward.wardshop.goods.service;

import com.ward.wardshop.goods.domain.product.ProductDetailComponent;
import com.ward.wardshop.goods.repository.ProductDetailComponentRepository;
import com.ward.wardshop.goods.service.dto.ComponentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ProductDetailComponentService {

    private final ProductDetailComponentRepository repository;

    @Transactional(readOnly = true)
    public List<ComponentDto> getProductDetailComponents(Long productIdx) {
        List<ProductDetailComponent> components =
                repository.findComponentsByProductIdx(productIdx);

        return components.stream()
                .map(component -> new ComponentDto(
                                component.getIdx(),
                                component.getComponentType(),
                                component.getData(),
                                component.getSequence()
                        )
                ).collect(toList());
    }
}
