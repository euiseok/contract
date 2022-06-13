package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.ContractCoverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final ProductService productService;

    public Contract makeContract(Product product, ContractRequest contractRequest){

        //상품에 맞는 보험기간
        if(!productService.isValidPeriod(product.getProdId(), contractRequest.getPeriod())){
            throw new RuntimeException("상품에서 허용하지 않는 보험기간입니다." + contractRequest.getPeriod());
        }

        LocalDate startDate = contractRequest.getInsuranceStartDate();
        LocalDate endDate = LocalDate.now().plusMonths(contractRequest.getPeriod());

        return contractRepository.save(contractRequest.toEntity(product, endDate));
    }

    public Contract modifyContractPeriod(){

        return null;
    }




}
