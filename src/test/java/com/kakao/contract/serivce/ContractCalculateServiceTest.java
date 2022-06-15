package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.dto.ProductRequest;
import com.kakao.contract.model.Coverage;
import com.kakao.contract.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractCalculateServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ContractCalculateService contractCalculateService;

    @Test
    @DisplayName("cell phone protect insurance")
    public void testCalculateCellPhoneInsurance(){

        //given
        Product product = makeCellPhoneInsurance();

        //when
        ContractRequest contractRequest = ContractRequest.builder()
                                            .insuranceStartDate(LocalDate.now())
                                            .period(11L)
                                            .coverages(product.getCoverage())
                                            .build();
        BigDecimal expectedPremium = contractCalculateService.calculateContract(contractRequest);

        //then
        //RoundDown((750000/38 + 1570000/40) * 11,2) = 648,855.24
        System.out.println(">>>>>>" + expectedPremium);
        Assertions.assertEquals(648855.24, expectedPremium.doubleValue());
    }

    @Test
    @DisplayName("travel insurance")
    public void testCalculateTravelInsurance(){

        //given
        Product product = makeTravelInsurance();

        //when
        ContractRequest contractRequest = ContractRequest.builder()
                                            .insuranceStartDate(LocalDate.now())
                                            .period(2L)
                                            .coverages(product.getCoverage())
                                            .build();
        BigDecimal expectedPremium = contractCalculateService.calculateContract(contractRequest);

        //then
        //RoundDown((1000000/100) * 2,2) = 20000
        System.out.println(">>>>>>" + expectedPremium);
        Assertions.assertEquals(20000, expectedPremium.doubleValue());
    }

    /*
    휴대폰 보험 상품정보 생성
     */
    private Product makeCellPhoneInsurance(){

        Set<Coverage> coverageSet = new HashSet<>();
        coverageSet.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));
        coverageSet.add(new Coverage("전체손실", new BigDecimal(1570000), new BigDecimal(40)));
        ProductRequest productRequest = new ProductRequest("휴대폰 보험",12L,coverageSet);
        Product product = productRequest.toEntity();
        when(productService.isValidPeriod(any(),any())).thenReturn(true);

        return product;
    }

    /*
    여행자 상품정보 생성
     */
    private Product makeTravelInsurance(){

        Set<Coverage> coverageSet = new HashSet<>();
        coverageSet.add(new Coverage("상해치료비", new BigDecimal(1000000), new BigDecimal(100)));
        ProductRequest productRequest = new ProductRequest("여행자 보험",3L, coverageSet);
        Product product = productRequest.toEntity();
        when(productService.isValidPeriod(any(),any())).thenReturn(true);

        return product;
    }

}