package com.ward.wardshop.goods.service;

import com.ward.wardshop.common.module.image.ImageManager;
import com.ward.wardshop.goods.domain.product.ComponentType;
import com.ward.wardshop.goods.domain.product.ProductDetailComponent;
import com.ward.wardshop.goods.repository.ProductDetailComponentRepository;
import com.ward.wardshop.goods.repository.ProductRepository;
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

    private final ProductDetailComponentRepository productDetailComponentRepository;
    private final ProductRepository productRepository;
    private final ImageManager imageManager;

    @Transactional(readOnly = true)
    public List<ComponentDto> getProductDetailComponents(Long productIdx) {
        List<ProductDetailComponent> components =
                productDetailComponentRepository.findComponentsByProductIdx(productIdx);

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
        List<ProductDetailComponent> components = productDetailComponentRepository.findComponentsByProductIdx(productIdx);

        ProductDetailComponent deletedComponent = productDetailComponentRepository.findById(componentIdx)
                .orElseThrow(IllegalArgumentException::new);

        raiseComponentsBelow(deletedComponent.getSequence(), components);

        productDetailComponentRepository.delete(deletedComponent);

        if (deletedComponent.getComponentType().equals(ComponentType.IMAGE)) {
            imageManager.deleteImg(deletedComponent.getData());
        }
    }

    @Transactional
    public void moveComponent(Long productIdx, Long componentIdx, Integer sequence) {
        List<ProductDetailComponent> components = productDetailComponentRepository.findComponentsByProductIdx(productIdx);
        ProductDetailComponent targetComponent = productDetailComponentRepository.findById(componentIdx)
                .orElseThrow(IllegalArgumentException::new);

        if (isRaiseRequest(targetComponent.getSequence(), sequence)) {
            addSequenceComponentsArea(components, sequence, targetComponent.getSequence(), 1);
        } else {
            addSequenceComponentsArea(components, targetComponent.getSequence() + 1, sequence, -1);
        }

        targetComponent.changeSequence(sequence);
    }

    // 상품 상세 정보 생성하기 - 2020-02-16 최인선 - 수정중
/*
    @Transactional
    public Long createComponent(Long productIdx, ProductDetailForm productDetailForm, MultipartFile multipartFile) throws IOException {
        List<ProductDetailComponent> components = productDetailComponentRepository.findComponentsByProductIdx(productIdx);

        //todo 메서드를 이용하여 코드에 의미를 부여하는 코드는 가독성을 좋게 만드는 좋은 코드인 것 같아요!
        ProductDetailComponent createProductDetail = createProductDetailWithProduct(productDetailForm, components);
        */
/*
        if (Objects.nonNull(multipartFile)) {
            newProduct.createImageResource(saveImage(multipartFile));
        }

        productRepository.save(newProduct);
        return newProduct.getIdx();*//*

        
        return (long) 1;
    }
*/
    // 상품 상세 정보 수정하기 - 2020-02-16 최인선 - 수정중
/*
    @Transactional
    public Long updateComponent(Long productIdx, Long componentIdx,
                       ProductUpdateForm form,
                       MultipartFile multipartFile) throws IOException {
        Product updatedProduct = productRepository.findById(idx)
                .orElseThrow(EntityNotFoundException::new);

        updatedProduct.updateData(form);

        if (Objects.nonNull(multipartFile)) {
            //deleteImage(updatedProduct);
            //updatedProduct.createImageResource(saveImage(multipartFile));
        }

        return updatedProduct.getIdx();
    }
*/

    private boolean isRaiseRequest(Integer target, Integer dest) {
        return target > dest;
    }

    private void raiseComponentsBelow(int sequence, List<ProductDetailComponent> components) {
        components.stream()
                .filter(c -> c.getSequence() > sequence)
                .forEach(c -> c.addSequence(-1));
    }

    private void addSequenceComponentsArea(List<ProductDetailComponent> components, int start, int end, int val) {
        components.stream()
                .filter(c -> c.getSequence() >= start && c.getSequence() < end)
                .forEach(c -> c.addSequence(val));
    }

/*
    private ProductDetailComponent createProductDetailWithProduct(ProductDetailForm productDetailForm, List<ProductDetailComponent> components){
        ProductDetailForm newProductDetail =  productDetailForm.toEntity();
    }
*/
}
