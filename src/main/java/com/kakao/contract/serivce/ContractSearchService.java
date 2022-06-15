package com.kakao.contract.serivce;

import com.kakao.contract.entity.Contract;
import com.kakao.contract.exception.EntityNotFoundException;
import com.kakao.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractSearchService {

    private final ContractRepository contractRepository;

    public Contract getContract(final Long contractId){
        Optional<Contract> contract = contractRepository.findById(contractId);
        contract.orElseThrow(() -> new EntityNotFoundException("계약정보가 존재하지 않습니다. " + contractId));
        return contract.get();
    }

}
