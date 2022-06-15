package com.kakao.contract.serivce;

import com.kakao.contract.dto.ProductRequest;
import com.kakao.contract.model.Coverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품이 정하는 보험기간 이내")
    public void testProductPeriodInRange(){

        //given
        Product product = makeProduct();

        //when (가입기간 12개월)
        Long period = 12L;
        boolean isValid = productService.isValidPeriod(product.getProdId(), period);

        //then
        Assertions.assertTrue(isValid);
    }

    @Test
    @DisplayName("상품이 정하는 보험기간 이외")
    public void testProductPeriodOutRange(){

        //given
        Product product = makeProduct();

        //when (가입기간 13개월)
        Long period = 13L;
        boolean isValid = productService.isValidPeriod(product.getProdId(), period);

        //then
        Assertions.assertFalse(isValid);
    }

    /*
    상품정보 생성
     */
    private Product makeProduct(){

        Set<Coverage> coverageSet = new HashSet<>();
        coverageSet.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));
        coverageSet.add(new Coverage("전체손실", new BigDecimal(1570000), new BigDecimal(40)));
        ProductRequest productRequest = new ProductRequest("휴대폰 보험",12L,coverageSet);
        Product product = productRequest.toEntity();
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        return product;
    }

}