package com.kakao.contract.entity;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contract_id;

    private LocalDate insuranceStartDate;

    private LocalDate insuranceEndDate;

    private BigDecimal totalPremium;

    @JoinColumn(name="prod_id")
    @OneToOne(mappedBy = "", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private ContractStatus contractStatus;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCoverage> productCoverages = new ArrayList<>();
    public void addProductCoverage(ProductCoverage productCoverage){
        productCoverages.add(productCoverage);
        productCoverage.setProduct(this);
    }

    @Builder
    public Product(String productName, int period){
        this.productName = productName;
        this.period = period;
    }

    public boolean isExpiration() {
        return LocalDate.now().isAfter(insuranceEndDate);
    }

}
