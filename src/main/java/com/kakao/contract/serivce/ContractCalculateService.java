package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.exception.BusinessException;
import com.kakao.contract.exception.ErrorCode;
import com.kakao.contract.model.Coverage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractCalculateService {

    private final ProductService productService;

    public BigDecimal calculateContract(final ContractRequest contractRequest) {

        //상품에 맞는 보험기간
        if(!productService.isValidPeriod(contractRequest.getProductId(), contractRequest.getPeriod())){
            throw new BusinessException(ErrorCode.INVALID_INSURANCE_PERIOD);
        }

        BigDecimal expectPremium = BigDecimal.ZERO;

        for(Coverage coverage : contractRequest.getCoverages()){
            expectPremium = expectPremium.add(coverage.getEntryAmount().divide(coverage.getBasePremium(), 2, RoundingMode.DOWN));
        }

        return expectPremium.multiply(new BigDecimal(contractRequest.getPeriod())).setScale(2, RoundingMode.DOWN);
    }
}
