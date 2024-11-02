package top.cusie.service.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.cusie.api.model.vo.PageParam;
import top.cusie.service.article.repository.entity.ArticleDO;
import top.cusie.service.user.dto.ArticleFootCountDTO;
import top.cusie.service.user.repository.entity.UserFootDO;

import java.util.List;

/**
 * 用户足迹mapper接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserFootMapper extends BaseMapper<UserFootDO> {
    /**
     * 查询文章计数信息
     * @param articleId
     * @return
     */
    ArticleFootCountDTO queryCountByArticle(@Param("articleId")Long articleId);

    /**
     * 查询用户文章计数
     * @param userId
     * @return
     */
    ArticleFootCountDTO queryArticleFootCount(@Param("userId") Long userId);

    /**
     * 查询用户收藏的文章列表
     * @param userId
     * @param pageParam
     * @return
     */
    List<ArticleDO> queryCollectionArticleList(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);


    /**
     * 查询用户阅读的文章列表
     * @param userId
     * @param pageParam
     * @return
     */
    List<ArticleDO> queryReadArticleList(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);
}
