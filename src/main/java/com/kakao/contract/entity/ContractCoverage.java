package com.kakao.contract.entity;

import com.kakao.contract.model.Coverage;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contract_coverage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public class ContractCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cov_id")
    private Long id;

    @Embedded
    @EqualsAndHashCode.Include
    private Coverage coverage;

    @ManyToOne
    @JoinColumn(name = "ctr_id", updatable = false)
    @ToString.Exclude
    @Setter
    private Contract contract;

    public ContractCoverage(Coverage coverage){
        this.coverage = coverage;
    }

}
