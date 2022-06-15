package com.kakao.contract.entity;

import com.kakao.contract.model.Coverage;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "contract")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctr_id")
    private Long contractId;

    @Column(name = "ins_st_dt", nullable = false, updatable = false)
    private LocalDate insuranceStartDate;

    @Column(name = "ins_ed_dt", nullable = false)
    private LocalDate insuranceEndDate;

    @Column(name = "ins_prd", nullable = false)
    private Long period;

    @Column(name = "tot_prm")
    @Setter
    private BigDecimal totalPremium;

    @JoinColumn(name="prod_id", nullable = false)
    @OneToOne(mappedBy = "", cascade = CascadeType.ALL)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "ctr_stats", nullable = false)
    private ContractStatus contractStatus;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<ContractCoverage> contractCoverages = new HashSet<>();
    public void addContractCoverage(ContractCoverage contractCoverage){
        contractCoverage.setContract(this);
        contractCoverages.add(contractCoverage);
    }

    public void deleteContractCoverage(ContractCoverage contractCoverage){
        contractCoverage.setContract(null);
        contractCoverages.remove(contractCoverage);
    }

    @Builder
    public Contract(Product product, LocalDate insuranceStartDate, LocalDate insuranceEndDate, Long period){
        this.product = product;
        this.insuranceStartDate = insuranceStartDate;
        this.insuranceEndDate = insuranceEndDate;
        this.period = period;
        this.contractStatus = ContractStatus.NORMAL;     // 최초생성시에는 정상
    }

    public boolean isExpiration() {
        return LocalDate.now().isAfter(insuranceEndDate);
    }

    public void modifyInsurancePeriod(final Long period){
        this.period = period;
        this.insuranceEndDate = insuranceStartDate.plusMonths(period);
    }

    @SneakyThrows(CloneNotSupportedException.class)
    public Set<Coverage> getCoverage(){
        final Set<Coverage> coverages = new HashSet<>();
        for(ContractCoverage contractCoverage : contractCoverages){
            coverages.add(contractCoverage.getCoverage().clone());
        }
        return coverages;
    }

}
