package com.kakao.contract.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "contract_coverage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class ContractCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cov_id")
    private Long id;

    @Column(name = "cov_nm")
    private String coverageName;

    @Column(name = "ent_amt")
    private BigDecimal entryAmount;

    @Column(name = "bas_prm")
    private BigDecimal basePremium;

    @ManyToOne
    @JoinColumn(name = "ctr_id", updatable = false)
    @ToString.Exclude
    private Contract contract;

    public ContractCoverage(ProductCoverage productCoverage){
        this.coverageName = productCoverage.getCoverageName();
        this.entryAmount = productCoverage.getEntryAmount();
        this.basePremium = productCoverage.getBasePremium();
    }

}
