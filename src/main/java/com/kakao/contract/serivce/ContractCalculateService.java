package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import com.kakao.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractCalculateService {

    private final ContractRepository contractRepository;

    private final ProductService productService;


    public BigDecimal calculateContract(Product product, ContractRequest contractRequest){

        //상품에 맞는 보험기간
        if(!productService.isValidPeriod(product.getProdId(), contractRequest.getPeriod())){
            throw new RuntimeException("상품에서 허용하지 않는 보험기간입니다." + contractRequest.getPeriod());
        }

        BigDecimal expectPremium = BigDecimal.ZERO;

        for(ProductCoverage productCoverage : product.getProductCoverages()){
            expectPremium.add(productCoverage.getEntryAmount().divide(productCoverage.getBasePremium(), 3, BigDecimal.ROUND_DOWN));
        }

        return expectPremium.multiply(new BigDecimal(contractRequest.getPeriod()));
    }
}
