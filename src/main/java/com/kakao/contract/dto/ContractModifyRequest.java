package com.kakao.contract.dto;

import com.kakao.contract.entity.ContractStatus;
import com.kakao.contract.model.Coverage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ContractModifyRequest {

    private Long contractId;
    private Long period;
    private ContractStatus contractStatus;
    private Set<Coverage> deleteCoverages;
    private Set<Coverage> addCoverages;

    @Builder
    public ContractModifyRequest(Long contractId, Long period, ContractStatus contractStatus, Set<Coverage> deleteCoverages, Set<Coverage> addCoverages){
        this.contractId = contractId;
        this.period = period;
        this.contractStatus = contractStatus;
        this.deleteCoverages = deleteCoverages;
        this.addCoverages = addCoverages;
    }

}
