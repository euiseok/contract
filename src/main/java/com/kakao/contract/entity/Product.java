package com.kakao.contract.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id", updatable = false)
    private Long prodId;

    @Column(name = "prod_nm", nullable = false)
    private String productName;

    @Column(name = "ins_prd", nullable = false)
    private Integer insurancePeriod;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCoverage> productCoverages = new ArrayList<>();
    public void addProductCoverage(ProductCoverage productCoverage){
        productCoverage.setProduct(this);
        productCoverages.add(productCoverage);
    }

    @Builder
    public Product(String productName, int insurancePeriod){
        this.productName = productName;
        this.insurancePeriod = insurancePeriod;
    }
}
