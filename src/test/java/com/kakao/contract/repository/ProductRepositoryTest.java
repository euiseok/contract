package com.kakao.contract.repository;

import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@SpringBootTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void test(){

        Product product = new Product("핸드폰보험",12);
        ProductCoverage productCoverage1 = new ProductCoverage("담보1", new BigDecimal(100000), new BigDecimal(200));
        ProductCoverage productCoverage2 = new ProductCoverage("담보2", new BigDecimal(175000), new BigDecimal(230));

        product.addProductCoverage(productCoverage1);
        product.addProductCoverage(productCoverage2);

        productRepository.save(product);

        System.out.println(">>>>>>>>>>" + productRepository.findAll());

    }
}
