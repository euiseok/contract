package com.kakao.contract.entity;

import javax.persistence.*;
import java.math.BigDecimal;

public class ContractCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal entryAmount;

    private BigDecimal premium;



}
