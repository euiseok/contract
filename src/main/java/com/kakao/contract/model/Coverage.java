package com.kakao.contract.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public class Coverage implements Cloneable {

    @EqualsAndHashCode.Include
    @Column(name = "cov_nm", nullable = false)
    private String coverageName;

    @Column(name = "ent_amt", nullable = false)
    private BigDecimal entryAmount;

    @Column(name = "bas_prm", nullable = false)
    private BigDecimal basePremium;

    @Override
    public Coverage clone() throws CloneNotSupportedException {
        return (Coverage) super.clone();
    }

}
