package top.cusie.service.article.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import top.cusie.service.article.repository.entity.ArticleTagDO;

import java.util.List;

/**
 * 文章标签映mapper接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTagDO> {
    /**
     * 批量保存
     *
     * @param entityList
     * @return
     */
    @Insert("<script>" +
            "insert into article_tag(`article_id`, `tag_id`, `deleted`) values " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.categoryId}, #{item.tagId}, 0})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<ArticleTagDO> entityList);
}
