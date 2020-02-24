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

    //todo 파라미터로 받는 productIdx 는 클라이언트로부터 받는 URI 값으로 주입받기 위해서는 @PathVariable 애너테이션을 이용해야 합니다!
    //todo ProductDetailComponent 를 만들 때는 data 또는 업로드 파일, sequence 값이 필요하지 않을까 싶습니다.
    // 상품 상세 정보 등록하기 - 2020-02-22 최인선
/*
    @PostMapping(path="/product/{productIdx}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long createComponent(@Valid Long productIdx, ProductDetailForm productDetailForm, MultipartFile imgFile) throws IOException {

        return productDetailComponentService.createComponent(productIdx, productDetailForm, imgFile);
    }
*/

/*  저는 컨트롤러 부분을 이렇게 작성하고 싶어요! post 요청에서 마지막에 components uri 까지 입력 받음으로써 api 사용자는 컴포넌트를 만들어내는
    api 라는 것을 명시적으로 알 수 있게 될 것 같아요.
    @PostMapping(path="/product/{productIdx}/components", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long createComponent(@PathVariable Long productIdx, ProductDetailForm productDetailForm, MultipartFile imgFile) throws IOException {

        return productDetailComponentService.createComponent(productIdx, productDetailForm, imgFile);
    }
*/

    @PutMapping("/product/{productIdx}/components/{componentIdx}")
    public ResponseEntity<String> moveComponent(@PathVariable Long productIdx,
                                                @PathVariable Long componentIdx,
                                                SequenceRequest sequenceRequest) {
        productDetailComponentService.moveComponent(productIdx, componentIdx, sequenceRequest.getSequence());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 상품 상세 정보 수정하기 - 2020-02-22 최인선
/*
    @PutMapping(value = "/product/{productIdx}/components/{componentIdx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long updateComponent(
            @PathVariable Long productIdx,
            @PathVariable Long componentIdx,
            @Valid ProductUpdateForm updateForm,
            MultipartFile imgFile) throws IOException {
        return productDetailComponentService.updateComponent(componentIdx, productIdx, updateForm, imgFile);
    }
*/

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
