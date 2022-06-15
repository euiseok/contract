package com.kakao.contract.entity;

import com.kakao.contract.model.Coverage;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_coverage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class ProductCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Coverage coverage;

    @ManyToOne
    @JoinColumn(name = "prod_id", updatable = false)
    @ToString.Exclude
    @Setter
    private Product product;

    public ProductCoverage(Coverage coverage){
        this.coverage = coverage;
    }

}
