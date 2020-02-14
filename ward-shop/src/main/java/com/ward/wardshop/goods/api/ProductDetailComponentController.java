package com.ward.wardshop.goods.api;

import com.ward.wardshop.goods.service.ProductDetailComponentService;
import com.ward.wardshop.goods.service.dto.ComponentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductDetailComponentController {

    private final ProductDetailComponentService productDetailComponentService;

    @GetMapping("/product/{productIdx}/components")
    public List<ComponentDto> getComponents(@PathVariable Long productIdx) {
        return productDetailComponentService.getProductDetailComponents(productIdx);
    }

    @PutMapping("/product/{productIdx}/components/{componentIdx}")
    public ResponseEntity<String> moveComponent(@PathVariable Long productIdx,
                                                @PathVariable Long componentIdx,
                                                SequenceRequest sequenceRequest) {
        productDetailComponentService.moveComponent(productIdx, componentIdx, sequenceRequest.getSequence());
        return new ResponseEntity<>("success", HttpStatus.OK);
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
