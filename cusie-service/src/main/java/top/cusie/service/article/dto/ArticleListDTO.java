package top.cusie.service.article.dto;

import lombok.Data;

import java.util.List;

/**
 * 文章列表信息
 *
 * @author Cusie
 * @date 2024/11/2
 */
@Data
public class ArticleListDTO {

    /**
     * 文章列表
     */
    List<ArticleDTO> articleList;

    /**
     * 是否有更多
     */
    private Boolean isMore;
}
