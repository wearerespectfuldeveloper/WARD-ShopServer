package com.ward.wardshop.goods.api;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.ward.wardshop.goods.api.model.ProductDetailForm;
import com.ward.wardshop.goods.api.model.ProductUpdateForm;
import com.ward.wardshop.goods.service.ProductDetailComponentService;
import com.ward.wardshop.goods.service.dto.ComponentDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductDetailComponentController {

    private final ProductDetailComponentService productDetailComponentService;

    @GetMapping("/product/{productIdx}/components")
    public List<ComponentDto> getComponents(@PathVariable Long productIdx) {
        return productDetailComponentService.getProductDetailComponents(productIdx);
    }

    // 상품 상세 정보 등록하기 - 2020-02-22 최인선
    @PostMapping(path="/product/{productIdx}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long createComponent(@Valid Long productIdx, ProductDetailForm productDetailForm, MultipartFile imgFile) throws IOException {

        return productDetailComponentService.createComponent(productIdx, productDetailForm, imgFile);
    }

    @PutMapping("/product/{productIdx}/components/{componentIdx}")
    public ResponseEntity<String> moveComponent(@PathVariable Long productIdx,
                                                @PathVariable Long componentIdx,
                                                SequenceRequest sequenceRequest) {
        productDetailComponentService.moveComponent(productIdx, componentIdx, sequenceRequest.getSequence());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 상품 상세 정보 수정하기 - 2020-02-22 최인선
    @PutMapping(value = "/product/{productIdx}/components/{componentIdx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long updateComponent(
            @PathVariable Long productIdx,
            @PathVariable Long componentIdx,
            @Valid ProductUpdateForm updateForm,
            MultipartFile imgFile) throws IOException {
        return productDetailComponentService.updateComponent(componentIdx, productIdx, updateForm, imgFile);
    }

    @DeleteMapping("/product/{productIdx}/components/{componentIdx}")
    public ResponseEntity<String> deleteComponent(@PathVariable Long productIdx,
                                                  @PathVariable Long componentIdx) throws IOException {
        productDetailComponentService.removeComponent(productIdx, componentIdx);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Getter
    @NoArgsConstructor
    private static class SequenceRequest {
        private Integer sequence;
    }
}
