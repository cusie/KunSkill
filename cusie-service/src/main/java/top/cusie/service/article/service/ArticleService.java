package top.cusie.service.article.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.article.ArticlePostReq;
import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.repository.entity.ArticleDO;

/**
 * @author Cusie
 * @date 2024/10/31
 */
public interface ArticleService {

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
     * @param req
     * @return
     */
    Long saveArticle(ArticlePostReq req);

    /**
     * 删除文章
     *
     * @param articleId
     */
    void deleteArticle(Long articleId);

    /**
     * 上线/下线文章
     *
     * @param articleId
     * @param pushStatusEnum
     */
    void operateArticle(Long articleId, PushStatusEnum pushStatusEnum);

    /**
     * 分页获取文章列表
     *
     * @param pageParam
     * @return
     */
    IPage<ArticleDO> getArticleByPage(PageParam pageParam);
}
