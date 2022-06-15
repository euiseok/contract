package com.kakao.contract.serivce;

import com.kakao.contract.dto.ContractModifyRequest;
import com.kakao.contract.dto.ContractRequest;
import com.kakao.contract.dto.ProductRequest;
import com.kakao.contract.entity.Contract;
import com.kakao.contract.entity.ContractStatus;
import com.kakao.contract.exception.BusinessException;
import com.kakao.contract.exception.ContractExpirationException;
import com.kakao.contract.model.Coverage;
import com.kakao.contract.entity.Product;
import com.kakao.contract.repository.ContractRepository;
import com.kakao.contract.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Transactional
@SpringBootTest
public class ContractServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ContractCalculateService contractCalculateService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    private Product product;

    @BeforeEach
    private void setUp(){
        // 상품저장
        product = productRepository.save(makeProduct());
    }

    @Test
    @DisplayName("make a contract")
    public void testMakeContract(){

        // given
        ContractRequest contractRequest = ContractRequest.builder()
                                            .insuranceStartDate(LocalDate.now())
                                            .period(11L)
                                            .coverages(product.getCoverage())
                                            .productId(product.getProdId())
                                            .build();

        // when
        Contract contract = contractService.makeContract(product, contractRequest);

        // then
        System.out.println(">>>"+contract);
        Assertions.assertNotNull(contract.getTotalPremium());
        Assertions.assertEquals(ContractStatus.NORMAL, contract.getContractStatus());
    }

    @Test
    @DisplayName("modify contract status before expiration")
    public void testModifyContractStatusBeforeExpiration(){

        // given
        ContractRequest contractRequest = ContractRequest.builder()
                .insuranceStartDate(LocalDate.now())
                .period(11L)
                .coverages(product.getCoverage())
                .productId(product.getProdId())
                .build();

        Contract contract = contractService.makeContract(product, contractRequest);

        // when
        ContractModifyRequest contractModifyRequest = ContractModifyRequest.builder()
                                                        .contractId(contract.getContractId())
                                                        .contractStatus(ContractStatus.EXPIRATION)
                                                        .build();
        contract = contractService.modifyContract(contractModifyRequest);

        // then
        System.out.println(">>>"+contract);
        Assertions.assertEquals(ContractStatus.EXPIRATION, contract.getContractStatus());
    }

    @Test
    @DisplayName("modify contract status after expiration")
    public void testModifyContractStatusAfterExpiration(){

        // given
        ContractRequest contractRequest = ContractRequest.builder()
                .insuranceStartDate(LocalDate.now().minusMonths(3))
                .period(2L)
                .coverages(product.getCoverage())
                .productId(product.getProdId())
                .build();

        Contract contract = contractService.makeContract(product, contractRequest);
        System.out.println(">>>"+contract);

        // when
        ContractModifyRequest contractModifyRequest = ContractModifyRequest.builder()
                .contractId(contract.getContractId())
                .contractStatus(ContractStatus.EXPIRATION)
                .build();

        // then
        // 계약종료는 현재일로부터 한달전
        Assertions.assertThrows(ContractExpirationException.class, () -> {
            contractService.modifyContract(contractModifyRequest);
        });
    }

    @Test
    @DisplayName("change contract period")
    public void testChangeContractPeriod(){

        // given
        LocalDate insuranceStartDate = LocalDate.now();
        ContractRequest contractRequest = ContractRequest.builder()
                .insuranceStartDate(insuranceStartDate)
                .period(11L)
                .coverages(product.getCoverage())
                .productId(product.getProdId())
                .build();

        Contract contract = contractService.makeContract(product, contractRequest);

        // when
        ContractModifyRequest contractModifyRequest = ContractModifyRequest.builder()
                .contractId(contract.getContractId())
                .period(9L)
                .build();
        contract = contractService.modifyContract(contractModifyRequest);

        // then
        System.out.println(">>>"+contract);
        Assertions.assertEquals(9L, contract.getPeriod());
        Assertions.assertEquals(LocalDate.now().plusMonths(9L), contract.getInsuranceEndDate());
        // 변경 전 RoundDown((750000/38 + 1570000/40) * 11,2) = 648,855.24
        // 변경 후 RoundDown((750000/38 + 1570000/40) * 9,2)  = 530,881.56
        Assertions.assertEquals(530881.56, contract.getTotalPremium().doubleValue());

    }

    @Test
    @DisplayName("delete contract coverage")
    public void testDeleteContractCoverage(){

        // given
        LocalDate insuranceStartDate = LocalDate.now();
        ContractRequest contractRequest = ContractRequest.builder()
                .insuranceStartDate(insuranceStartDate)
                .period(11L)
                .coverages(product.getCoverage())
                .productId(product.getProdId())
                .build();

        Contract contract = contractService.makeContract(product, contractRequest);

        // when
        Set<Coverage> deleteCoverages = new HashSet<>();
        deleteCoverages.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));

        ContractModifyRequest contractModifyRequest = ContractModifyRequest.builder()
                .contractId(contract.getContractId())
                .deleteCoverages(deleteCoverages)
                .build();
        contract = contractService.modifyContract(contractModifyRequest);

        // then
        System.out.println(">>>"+contract);
        Assertions.assertEquals(1, contract.getContractCoverages().size());
        // 변경 전 RoundDown((750000/38 + 1570000/40) * 11,2) = 648,855.24
        // 변경 후 RoundDown((1570000/40) * 11,2)             = 431,750
        Assertions.assertEquals(431750, contract.getTotalPremium().doubleValue());

    }

    @Test
    @DisplayName("delete all contract coverage")
    public void testDeleteAllContractCoverage(){

        // given
        LocalDate insuranceStartDate = LocalDate.now();
        ContractRequest contractRequest = ContractRequest.builder()
                .insuranceStartDate(insuranceStartDate)
                .period(11L)
                .coverages(product.getCoverage())
                .productId(product.getProdId())
                .build();

        Contract contract = contractService.makeContract(product, contractRequest);

        // when
        Set<Coverage> deleteCoverages = new HashSet<>();
        deleteCoverages.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));
        deleteCoverages.add(new Coverage("전체손실", new BigDecimal(1570000), new BigDecimal(40)));

        ContractModifyRequest contractModifyRequest = ContractModifyRequest.builder()
                .contractId(contract.getContractId())
                .deleteCoverages(deleteCoverages)
                .build();
        
        // then
        // 담보필수
        Assertions.assertThrows(BusinessException.class, () -> {
            contractService.modifyContract(contractModifyRequest);
        });

    }

    @Test
    @DisplayName("add contract coverage")
    public void testAddAllContractCoverage(){

        // given
        // 담보를 하나만 선택하여 가입
        LocalDate insuranceStartDate = LocalDate.now();
        Set<Coverage> coverages = product.getCoverage();
        coverages.remove(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));

        ContractRequest contractRequest = ContractRequest.builder()
                .insuranceStartDate(insuranceStartDate)
                .period(10L)
                .coverages(coverages)
                .productId(product.getProdId())
                .build();

        Contract contract = contractService.makeContract(product, contractRequest);

        Assertions.assertEquals(1, contract.getContractCoverages().size());

        // when
        Set<Coverage> addCoverages = new HashSet<>();
        addCoverages.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));

        ContractModifyRequest contractModifyRequest = ContractModifyRequest.builder()
                .contractId(contract.getContractId())
                .addCoverages(addCoverages)
                .build();

        contract = contractService.modifyContract(contractModifyRequest);

        // then
        System.out.println(">>>"+contract);
        Assertions.assertEquals(2, contract.getContractCoverages().size());
        // 변경 전 RoundDown((1570000/40) * 10,2)               = 392,500
        // 변경 후 RoundDown((750000/38) + (1570000/40) * 10,2) = 589,868.40
        Assertions.assertEquals(589868.40, contract.getTotalPremium().doubleValue());

    }


    /*
    상품정보 생성
     */
    private Product makeProduct(){

        Set<Coverage> coverageSet = new HashSet<>();
        coverageSet.add(new Coverage("부분손실", new BigDecimal(750000), new BigDecimal(38)));
        coverageSet.add(new Coverage("전체손실", new BigDecimal(1570000), new BigDecimal(40)));
        ProductRequest productRequest = new ProductRequest("휴대폰 보험",12L,coverageSet);
        return productRequest.toEntity();
    }
}
