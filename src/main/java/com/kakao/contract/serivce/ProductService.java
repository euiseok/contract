package com.kakao.contract.serivce;

import com.kakao.contract.entity.Product;
import com.kakao.contract.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public boolean isValidPeriod(final Long prodId, final Long period) {

        Optional<Product> product = productRepository.findById(prodId);
        product.orElseThrow(() -> new RuntimeException("상품정보가 존재하지 않습니다. " + prodId));
        return period <= product.get().getInsurancePeriod() && period > 0;
    }

}
