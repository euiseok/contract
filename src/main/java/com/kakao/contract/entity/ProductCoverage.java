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

    @Column(name = "cov_nm", nullable = false)
    private String coverageName;

    @Column(name = "ent_amt")
    private BigDecimal entryAmount;

    @Column(name = "bas_prm")
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
