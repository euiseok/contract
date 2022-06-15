package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractModifyRequest;
import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.ContractCoverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.exception.BusinessException;
import com.kakao.contract.exception.ContractExpirationException;
import com.kakao.contract.exception.ErrorCode;
import com.kakao.contract.model.Coverage;
import com.kakao.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ProductService productService;
    private final ContractCalculateService contractCalculateService;
    private final ContractSearchService contractSearchService;

    public Contract makeContract(Product product, final ContractRequest contractRequest){

        Contract contract = contractRequest.toEntity(product);
        contract.setTotalPremium(contractCalculateService.calculateContract(contractRequest));

        return contractRepository.save(contract);
    }

    public Contract modifyContract(final ContractModifyRequest contractModifyRequest) {

        boolean isReCalculate = false;

        Contract contract = contractSearchService.getContract(contractModifyRequest.getContractId());

        if(contract.isExpiration()){
            throw new ContractExpirationException();
        }

        // 보험기간 수정
        if(contractModifyRequest.getPeriod() != null && contractModifyRequest.getPeriod() > 0){
            if(productService.isValidPeriod(contract.getProduct().getProdId(), contractModifyRequest.getPeriod())){
                contract.modifyInsurancePeriod(contractModifyRequest.getPeriod());
                isReCalculate = true;
            }else{
                throw new BusinessException(ErrorCode.INVALID_INSURANCE_PERIOD);
            }
        }

        // 계약상태 수정
        if(contractModifyRequest.getContractStatus() != null && !contract.getContractStatus().equals(contractModifyRequest.getContractStatus())){
            contract.setContractStatus(contractModifyRequest.getContractStatus());
        }

        // 계약담보 수정
        // 삭제
        if(contractModifyRequest.getDeleteCoverages() != null) {
            for (Coverage coverage : contractModifyRequest.getDeleteCoverages()) {
                ContractCoverage contractCoverage = new ContractCoverage(coverage);

                System.out.println(">> 담보명1" + coverage + ", " + coverage.hashCode());
                System.out.println(">> 담보명2" + contractCoverage + ", " + contractCoverage.hashCode());

                contract.deleteContractCoverage(contractCoverage);
            }
            isReCalculate = true;
        }
        
        // 추가
        if(contractModifyRequest.getAddCoverages() != null) {
            for (Coverage coverage : contractModifyRequest.getAddCoverages()) {
                contract.addContractCoverage(new ContractCoverage(coverage));
            }
            isReCalculate = true;
        }

        // 담보가 없을 경우 오류
        if(contract.getContractCoverages().isEmpty()){
            throw new BusinessException(ErrorCode.CONTRACT_HAS_NO_COVERAGE);
        }

        // 보험료 계산
        if(isReCalculate) {
            ContractRequest contractRequest = ContractRequest.builder()
                    .productId(contract.getProduct().getProdId())
                    .period(contract.getPeriod())
                    .coverages(contract.getCoverage())
                    .build();
            contract.setTotalPremium(contractCalculateService.calculateContract(contractRequest));
        }

        return contractRepository.save(contract);
    }

}
