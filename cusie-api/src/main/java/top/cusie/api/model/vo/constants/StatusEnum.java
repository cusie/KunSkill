package top.cusie.api.model.vo.constants;

import lombok.Getter;

/**
 * @author Cusie
 * @date 2024/10/30
 */
@Getter
public enum StatusEnum {
    SUCCESS(0, "OK"),

    ILLEGAL_ARGUMENTS(400_001, "参数异常"),
    ILLEGAL_ARGUMENTS_MIXed(400_002, "参数异常:%s"),
    ;

    private int code;

    private String msg;

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
