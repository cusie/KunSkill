package top.cusie.test.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cusie.api.model.vo.PageParam;
import top.cusie.service.article.dto.ArticleListDTO;
import top.cusie.service.article.dto.TagDTO;
import top.cusie.service.article.repository.entity.CategoryDO;
import top.cusie.service.article.repository.entity.TagDO;
import top.cusie.service.article.service.impl.ArticleServiceImpl;
import top.cusie.service.article.service.impl.CategoryServiceImpl;
import top.cusie.service.article.service.impl.TagServiceImpl;
import top.cusie.test.BasicTest;

import java.util.List;

/**
 * @author YiHui
 * @date 2022/7/20
 */
@Slf4j
public class ArticleDaoTest extends BasicTest {

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Test
    public void testCategory() {
        CategoryDO category = new CategoryDO();
        category.setCategoryName("后端");
        category.setStatus(1);
        Long categoryId = categoryService.addCategory(category);
        log.info("save category:{} -> id:{}", category, categoryId);

        IPage<CategoryDO> list = categoryService.getCategoryByPage(PageParam.newPageInstance(0L, 10L));
        log.info("query list: {}", list.getRecords());

    }

    @Test
    public void testTag() {
        TagDO tag = new TagDO();
        tag.setTagName("Javax");
        tag.setTagType(1);
        tag.setCategoryId(1L);
        Long tagId = tagService.addTag(tag);
        log.info("tagId: {}", tagId);

        List<TagDTO> list = tagService.getTagListByCategoryId(1L);
        log.info("tagList: {}", list);
    }

    @Test
    public void testArticle() {
        ArticleListDTO articleListDTO = articleService.getCollectionArticleListByUserId(1L, PageParam.newPageInstance(1L, 10L));
        log.info("articleListDTO: {}", articleListDTO);
    }

}
