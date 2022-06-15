package com.kakao.contract.exception;

public enum ErrorCode {

    // Common
    ENTITY_NOT_FOUND("CM01", "Entity Not Found"),

    // Contract
    CONTRACT_EXPIRATION("C001","계약이 만료되었습니다."),
    CONTRACT_HAS_NO_COVERAGE("C002","계약에 담보는 1건 이상 가입되어야 합니다."),

    // Product
    INVALID_INSURANCE_PERIOD("P001","입력된 보험기간이 상품에서 정의된 기간에 맞지않습니다.")
    ;

    private String code;
    private String message;

    ErrorCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }
}
