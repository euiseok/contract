package com.kakao.contract.service;

import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import com.kakao.contract.repository.ProductRepository;
import com.kakao.contract.serivce.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testProductPeriodInRange(){

        //given
        Product product = makeProduct();

        //when
        Long period = 12L;
        boolean isValid = productService.isValidPeriod(product.getProdId(), period);

        //then
        System.out.println(">>>>>>" + isValid);
        //assert isValid;

    }

    @Test
    public void testProductPeriodOutRange(){

        //given
        Product product = makeProduct();

        //when
        Long period = 13L;
        boolean isValid = productService.isValidPeriod(product.getProdId(), period);

        //then
        System.out.println(">>>>>>" + isValid);
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
