package com.kakao.contract.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_coverage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class ProductCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "coverage_name", nullable = false)
    private String coverageName;

    @Column(name = "entry_amount")
    private BigDecimal entryAmount;

    @Column(name = "base_premium")
    private BigDecimal basePremium;

    @ManyToOne
    @JoinColumn(name = "prod_id", updatable = false)
    @ToString.Exclude
    private Product product;

    @Builder
    public ProductCoverage(String coverageName, BigDecimal entryAmount, BigDecimal basePremium){
        this.coverageName = coverageName;
        this.entryAmount = entryAmount;
        this.basePremium = basePremium;
    }


}
