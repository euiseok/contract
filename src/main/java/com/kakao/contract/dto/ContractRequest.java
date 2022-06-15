package com.kakao.contract.dto;

import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.ContractCoverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.model.Coverage;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ContractRequest {

    private Long contractId;
    private Long productId;
    private LocalDate insuranceStartDate;
    private Long period;
    private Set<Coverage> coverages;

    public Contract toEntity(final Product product){

        Contract contract = Contract.builder()
                .product(product)
                .insuranceStartDate(insuranceStartDate)
                .insuranceEndDate(insuranceStartDate.plusMonths(this.period))
                .period(period)
                .build();

        for(Coverage coverage : coverages){
            contract.addContractCoverage(new ContractCoverage(coverage));
        }

        return contract;
    }

    @Builder
    public ContractRequest(LocalDate insuranceStartDate, Long period, Set<Coverage> coverages, Long productId){
        this.insuranceStartDate = insuranceStartDate;
        this.period = period;
        this.coverages = coverages;
        this.productId = productId;
    }
}
