package top.cusie.service.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Cusie
 * @date 2024/11/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 8272116638231812207L;
    public static CategoryDTO EMPTY = new CategoryDTO(-1L, "illegal");

    private Long categoryId;

    private String category;
}
