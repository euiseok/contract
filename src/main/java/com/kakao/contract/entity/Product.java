package com.kakao.contract.entity;

import com.kakao.contract.model.Coverage;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(exclude = {"prodId", "insurancePeriod", "productCoverages"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id", updatable = false)
    private Long prodId;

    @Column(name = "prod_nm", nullable = false)
    private String productName;

    @Column(name = "ins_prd", nullable = false)
    private Long insurancePeriod;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductCoverage> productCoverages = new HashSet<>();

    public void addProductCoverage(ProductCoverage productCoverage){
        productCoverage.setProduct(this);
        productCoverages.add(productCoverage);
    }

    public void deleteProductCoverage(ProductCoverage productCoverage){
        productCoverage.setProduct(null);
        productCoverages.remove(productCoverage);
    }

    @Builder
    public Product(String productName, Long insurancePeriod){
        this.productName = productName;
        this.insurancePeriod = insurancePeriod;
    }

    @SneakyThrows(CloneNotSupportedException.class)
    public Set<Coverage> getCoverage() {
        final Set<Coverage> coverages = new HashSet<>();
        for(ProductCoverage productCoverage : productCoverages){
            coverages.add(productCoverage.getCoverage().clone());
        }
        return coverages;
    }
}
