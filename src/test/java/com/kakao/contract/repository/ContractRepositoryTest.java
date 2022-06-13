package com.kakao.contract.repository;

import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class ContractRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Test
    public void makeContractTest(){

        Product product = givenMakeProduct();

        Long period = 6L;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(period);
        Contract contract = new Contract(product, startDate, endDate, period);

        contractRepository.save(contract);

        System.out.println(">>>>>>>>>>" + contractRepository.findAll());

    }

    /*
    상품정보 생성
     */
    private Product givenMakeProduct(){

        Product product = new Product("핸드폰보험",12);
        product.addProductCoverage(new ProductCoverage("담보1", new BigDecimal(100000), new BigDecimal(200)));
        product.addProductCoverage(new ProductCoverage("담보2", new BigDecimal(175000), new BigDecimal(230)));

        return productRepository.save(product);
    }

}
