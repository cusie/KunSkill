package top.cusie.service.article.repository;


import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.repository.entity.ArticleDO;

import java.util.Set;

/**
 * 文章相关DB操作
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface ArticleRepository {
    /**
     * 查询文章详情
     *
     * @param articleId
     * @return
     */
    ArticleDTO queryArticleDetail(Long articleId);

    /**
     * 保存or更新文章
     *
     * @param article
     * @param content
     * @param tags
     * @return
     */
    Long saveArticle(ArticleDO article, String content, Set<Long> tags);
}
