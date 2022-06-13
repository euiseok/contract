package com.kakao.contract.repository;

import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@Transactional
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct(){

        Product product = new Product("핸드폰보험",12);
        product.addProductCoverage(new ProductCoverage("담보1", new BigDecimal(100000), new BigDecimal(200)));
        product.addProductCoverage(new ProductCoverage("담보2", new BigDecimal(175000), new BigDecimal(230)));

        productRepository.save(product);

        System.out.println(">>>>>>>>>>" + productRepository.findAll());

    }



}
