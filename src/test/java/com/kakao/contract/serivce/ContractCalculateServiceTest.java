package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import com.kakao.contract.repository.ContractRepository;
import com.kakao.contract.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContractCalculateServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ContractCalculateService contractCalculateService;



    @Test
    public void testMakeContract(){

        //given
        Product product = makeProduct();

        //when
        ContractRequest contractRequest = new ContractRequest();
        contractRequest.setPeriod(10L);
        contractRequest.setInsuranceStartDate(LocalDate.now());

        BigDecimal expectedPremium = contractCalculateService.calculateContract(product, contractRequest);

        //then
        System.out.println(">>>>>>" + expectedPremium);
        //assert isValid;
    }



    /*
    상품정보 생성
     */
    private Product makeProduct(){

        Product product = new Product("여행자 보험",3);
        product.addProductCoverage(new ProductCoverage("상해치료비", new BigDecimal(1000000), new BigDecimal(100)));
        product.addProductCoverage(new ProductCoverage("항공기지연도착시보상금", new BigDecimal(500000), new BigDecimal(100)));

        return product;
    }

}