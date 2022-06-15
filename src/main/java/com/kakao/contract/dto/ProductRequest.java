package com.kakao.contract.dto;

import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import com.kakao.contract.model.Coverage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String productName;
    private Long insurancePeriod;
    private Set<Coverage> coverages;

    public Product toEntity(){

        Product product = Product.builder()
                .productName(productName)
                .insurancePeriod(insurancePeriod)
                .build();

        for(Coverage coverage : coverages){
            product.addProductCoverage(new ProductCoverage(coverage));
        }

        return product;
    }

}
