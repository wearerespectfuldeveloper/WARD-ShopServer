package com.ward.wardshop.goods.service;

import com.ward.wardshop.common.module.ImageManager;
import com.ward.wardshop.goods.domain.product.ComponentType;
import com.ward.wardshop.goods.domain.product.ProductDetailComponent;
import com.ward.wardshop.goods.repository.ProductDetailComponentRepository;
import com.ward.wardshop.goods.service.dto.ComponentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ProductDetailComponentService {

    private final ProductDetailComponentRepository repository;
    private final ImageManager imageManager;

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

    @Transactional
    public void removeComponent(Long productIdx, Long componentIdx) throws IOException {
        List<ProductDetailComponent> components = repository.findComponentsByProductIdx(productIdx);

        ProductDetailComponent deletedComponent = repository.findById(componentIdx)
                .orElseThrow(IllegalArgumentException::new);

        raiseComponentsBelow(deletedComponent.getSequence(), components);

        repository.delete(deletedComponent);

        if (deletedComponent.getComponentType().equals(ComponentType.IMAGE)) {
            imageManager.deleteImg(deletedComponent.getData());
        }
    }

    private void raiseComponentsBelow(int sequence, List<ProductDetailComponent> components) {
        components.stream()
                .filter(c -> c.getSequence() > sequence)
                .forEach(c -> c.addSequence(-1));
    }
}
