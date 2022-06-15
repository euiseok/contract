package com.kakao.contract.exception;

public class ContractExpirationException extends BusinessException {

    public ContractExpirationException() {
        super(ErrorCode.CONTRACT_EXPIRATION);
    }
}
