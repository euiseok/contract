package com.kakao.contract.repository;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.dto.ProductRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.model.Coverage;
import com.kakao.contract.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@DataJpaTest
public class ContractRepositoryTest {

    @Autowired
    private ContractRepository ContractRepository;

    @Test
    @DisplayName("contract save")
    public void makeContractTest(){

        // given
        Product product = makeProduct();
        ContractRequest contractRequest = ContractRequest.builder()
                                            .insuranceStartDate(LocalDate.now())
                                            .period(6L)
                                            .coverages(product.getCoverage())
                                            .build();

        // when
        Contract contract = contractRequest.toEntity(product);
        Contract saveContract = ContractRepository.save(contract);

        // then
        System.out.println(">>>" + saveContract);
        Assertions.assertEquals(contract, saveContract);
        Assertions.assertNotNull(saveContract.getContractId());
        Assertions.assertTrue(ContractRepository.count()==1);

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
