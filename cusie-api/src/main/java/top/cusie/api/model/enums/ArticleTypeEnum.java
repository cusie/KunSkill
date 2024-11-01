package top.cusie.api.model.enums;

import lombok.Getter;

/**
 * 文章类型枚举
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Getter
public enum ArticleTypeEnum {

    EMPTY(0, ""),
    BLOG(1, "博文"),
    ANSWER(2, "问答");

    ArticleTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static ArticleTypeEnum formCode(Integer code) {
        for (ArticleTypeEnum value : ArticleTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ArticleTypeEnum.EMPTY;
    }
}
