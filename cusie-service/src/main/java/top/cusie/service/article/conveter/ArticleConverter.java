package top.cusie.service.article.conveter;

import top.cusie.api.model.enums.SourceTypeEnum;
import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.dto.CategoryDTO;
import top.cusie.service.article.repository.entity.ArticleDO;

/**
 * 文章转换
 *
 * @author Cusie
 * @date 2024/11/2
 */
public class ArticleConverter {

    public static ArticleDTO toDTO(ArticleDO articleDO) {
        if (articleDO == null) {
            return null;
        }
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setAuthor(articleDO.getUserId());
        articleDTO.setArticleId(articleDO.getId());
        articleDTO.setArticleType(articleDO.getArticleType());
        articleDTO.setTitle(articleDO.getTitle());
        articleDTO.setShortTitle(articleDO.getShortTitle());
        articleDTO.setSummary(articleDO.getSummary());
        articleDTO.setCover(articleDO.getPicture());
        articleDTO.setSourceType(SourceTypeEnum.formCode(articleDO.getSource()).getDesc());
        articleDTO.setSourceUrl(articleDO.getSourceUrl());
        articleDTO.setStatus(articleDO.getStatus());
        articleDTO.setLastUpdateTime(articleDO.getUpdateTime().getTime());

        // 设置类目id
        articleDTO.setCategory(new CategoryDTO(articleDO.getCategoryId(), null));
        return articleDTO;
    }
}
