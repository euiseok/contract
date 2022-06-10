package com.kakao.contract.repository;

import com.kakao.contract.entity.ProductCoverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCoverageRepository extends JpaRepository<ProductCoverage, Long> {
}
