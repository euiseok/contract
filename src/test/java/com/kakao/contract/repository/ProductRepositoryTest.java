package com.kakao.contract.repository;

import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCoverageRepository productCoverageRepository;

    @Test
    public void test(){

        Product product = new Product("핸드폰보험",12);
        productRepository.save(product);

        System.out.println(">>>>>>>>>>" + productRepository.findAll());

        ProductCoverage productCoverage1 = new ProductCoverage(product, "담보1", new BigDecimal(100000), new BigDecimal(200));
        productCoverageRepository.save(productCoverage1);

        ProductCoverage productCoverage2 = new ProductCoverage(product, "담보2", new BigDecimal(175000), new BigDecimal(230));
        productCoverageRepository.save(productCoverage2);

        System.out.println(">>>>>>>>>>1" + productCoverageRepository.findAll());
        System.out.println(">>>>>>>>>>2" + productCoverageRepository.findAll().get(0).getProduct());

    }
}
