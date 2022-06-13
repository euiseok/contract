package com.kakao.contract.dto;

import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.ContractCoverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContractRequest {

    private LocalDate insuranceStartDate;

    private Long period;

    public Contract toEntity(final Product product, final LocalDate insuranceEndDate){

        Contract contract = Contract.builder()
                .product(product)
                .insuranceStartDate(insuranceStartDate)
                .insuranceEndDate(insuranceEndDate)
                .period(period)
                .build();

        for(ProductCoverage productCoverage : product.getProductCoverages()){
            contract.addContractCoverage(new ContractCoverage(productCoverage));
        }

        return contract;
    }
}
