package com.kakao.contract.entity;

import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contract")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctr_id")
    private Long contract_id;

    @Column(name = "ins_st_dt", nullable = false)
    private LocalDate insuranceStartDate;

    @Column(name = "ins_ed_dt", nullable = false)
    private LocalDate insuranceEndDate;

    @Column(name = "ins_prd", nullable = false)
    private Long period;

    @Column(name = "tot_prm")
    private BigDecimal totalPremium;

    @JoinColumn(name="prod_id", nullable = false)
    @OneToOne(mappedBy = "", cascade = CascadeType.ALL)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "ctr_stats", nullable = false)
    private ContractStatus contractStatus;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractCoverage> contractCoverages = new ArrayList<>();
    public void addContractCoverage(ContractCoverage contractCoverage){
        contractCoverage.setContract(this);
        contractCoverages.add(contractCoverage);
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

}
