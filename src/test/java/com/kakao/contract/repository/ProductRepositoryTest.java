package com.kakao.contract.repository;

import com.kakao.contract.model.Coverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.entity.ProductCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;


@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("product save")
    public void testCreateProduct(){

        // given
        Product product = new Product("여행자 보험",3L);
        product.addProductCoverage(new ProductCoverage(new Coverage("상해치료비", new BigDecimal(1000000), new BigDecimal(100))));
        product.addProductCoverage(new ProductCoverage(new Coverage("항공기 지연도착시 보상금", new BigDecimal(500000), new BigDecimal(100))));

        // when
        Product saveProduct = productRepository.save(product);

        // then
        System.out.println(">>>" + product);
        System.out.println(">>>" + saveProduct);
        Assertions.assertEquals(product, saveProduct);
        Assertions.assertNotNull(saveProduct.getProdId());
        Assertions.assertTrue(productRepository.count()==1);

    }

}
