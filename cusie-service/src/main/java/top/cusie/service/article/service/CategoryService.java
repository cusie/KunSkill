package top.cusie.service.article.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.service.article.repository.entity.CategoryDO;

/**
 * @author Cusie
 * @date 2024/10/31
 */
public interface CategoryService {
    /**
     * 查询类目名
     *
     * @param categoryId
     * @return
     */
    String getCategoryName(Long categoryId);

    /**
     * 添加类目
     *
     * @param categoryDTO
     * @return
     */
    Long addCategory(CategoryDO categoryDTO);

    /**
     * 更新类目
     *
     * @param categoryId
     * @param categoryName
     */
    void updateCategory(Long categoryId, String categoryName);

    /**
     * 删除类目
     *
     * @param categoryId
     */
    void deleteCategory(Long categoryId);

    /**
     * 操作类目
     *
     * @param categoryId
     */
    void operateCategory(Long categoryId, PushStatusEnum pushStatusEnum);

    /**
     * 类目分页查询
     *
     * @return
     */
    IPage<CategoryDO> getCategoryByPage(PageParam pageParam);
}
