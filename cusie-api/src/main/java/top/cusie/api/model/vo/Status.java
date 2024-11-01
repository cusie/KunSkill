package top.cusie.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cusie
 * @date 2024/10/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    private int code;

    private String msg;

    public static Status newStatus(int code, String msg) {
        return new Status(code, msg);
    }


}