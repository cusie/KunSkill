package top.cusie.api.model.vo;

import top.cusie.api.model.vo.constants.StatusEnum;

import java.io.Serializable;

/**
 * @author Cusie
 * @date 2024/10/30
 */
public class ResVo<T> implements Serializable {
    private static final long serialVersionUID = -510306209659393854L;
    private Status status;

    private T result;


    public ResVo() {
    }

    public ResVo(Status status) {
        this.status = status;
    }

    public ResVo(T t) {
        status = Status.newStatus(StatusEnum.SUCCESS);
        this.result = t;
    }

    public static <T> ResVo<T> ok(T t) {
        return new ResVo<T>(t);
    }

    @SuppressWarnings("unchecked")
    public static <T> ResVo<T> fail(StatusEnum status, Object... args) {
        return new ResVo<>(Status.newStatus(status, args));
    }
}
