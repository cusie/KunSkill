package top.cusie.service.article.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.article.ArticlePostReq;
import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.dto.ArticleListDTO;
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
     * 查询某个分类下的文章，支持翻页
     *
     * @param categoryId
     * @param page
     * @return
     */
    ArticleListDTO queryArticlesByCategory(Long categoryId, PageParam page);

    /**
     * 根据查询条件查询文章列表，支持翻页
     *
     * @param key
     * @param page
     * @return
     */
    ArticleListDTO queryArticlesBySearchKey(String key, PageParam page);

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
     * 获取用户文章列表
     *
     * @param userId
     * @return
     */
    ArticleListDTO getArticleListByUserId(Long userId, PageParam pageSearchReq);


    /**
     * 获取用户收藏的文章列表
     *
     * @param userId
     * @param pageParam
     * @return
     */
    ArticleListDTO getCollectionArticleListByUserId(Long userId, PageParam pageParam);

    /**
     * 获取用户阅读的文章列表
     *
     * @param userId
     * @param pageParam
     * @return
     */
    ArticleListDTO getReadArticleListByUserId(Long userId, PageParam pageParam);
}
