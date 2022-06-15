package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.dto.ProductRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.Product;
import com.kakao.contract.model.Coverage;
import com.kakao.contract.repository.ContractRepository;
import com.kakao.contract.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class ContractSearchServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractSearchService contractSearchService;

    @Autowired
    private ContractService contractService;

    private Product product;

    @BeforeEach
    private void setUp(){
        // 상품저장
        product = productRepository.save(makeProduct());
    }


    @Test
    @DisplayName("contract information search test")
    public void contractSearchTest(){

        // given
        ContractRequest contractRequest = ContractRequest.builder()
                                            .insuranceStartDate(LocalDate.now())
                                            .period(11L)
                                            .coverages(product.getCoverage())
                                            .productId(product.getProdId())
                                            .build();
        Contract contract = contractService.makeContract(product, contractRequest);

        // when
        Contract contractResult = contractSearchService.getContract(contract.getContractId());

        System.out.println(">>>"+contract);
        Assertions.assertNotNull(contractResult.getTotalPremium());
        Assertions.assertNotNull(contractResult.getContractId());

    }


    /*
    상품정보 생성
     */
    private Product makeProduct(){

        Set<Coverage> coverageSet = new HashSet<>();
        coverageSet.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));
        coverageSet.add(new Coverage("전체손실", new BigDecimal(1570000), new BigDecimal(40)));
        ProductRequest productRequest = new ProductRequest("휴대폰 보험",12L,coverageSet);
        return productRequest.toEntity();
    }

}