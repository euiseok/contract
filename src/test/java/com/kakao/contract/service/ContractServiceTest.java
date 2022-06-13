package com.kakao.contract.service;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import com.kakao.contract.repository.ContractRepository;
import com.kakao.contract.repository.ProductRepository;
import com.kakao.contract.serivce.ContractService;
import com.kakao.contract.serivce.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class ContractServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;


    @Test
    public void testMakeContract(){

        //given
        Product product = makeProduct();

        //when
        ContractRequest contractRequest = new ContractRequest();
        contractRequest.setPeriod(10L);
        contractRequest.setInsuranceStartDate(LocalDate.now());

        Contract contract = contractService.makeContract(product, contractRequest);

        //then
        System.out.println(">>>>>>" + contract);
        //assert isValid;
    }



    /*
    상품정보 생성
     */
    private Product makeProduct(){

        Product product = new Product("핸드폰보험",12);
        product.addProductCoverage(new ProductCoverage("담보1", new BigDecimal(100000), new BigDecimal(200)));
        product.addProductCoverage(new ProductCoverage("담보2", new BigDecimal(175000), new BigDecimal(230)));

        return productRepository.save(product);
    }
}
