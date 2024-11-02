package top.cusie.service.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cusie.api.model.context.ReqInfoContext;
import top.cusie.api.model.enums.ArticleTypeEnum;
import top.cusie.api.model.enums.OperateTypeEnum;
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.enums.YesOrNoEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.article.ArticlePostReq;
import top.cusie.service.article.conveter.ArticleConverter;
import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.dto.ArticleListDTO;
import top.cusie.service.article.dto.CategoryDTO;
import top.cusie.service.article.dto.TagDTO;
import top.cusie.service.article.repository.ArticleRepository;
import top.cusie.service.article.service.ArticleService;
import top.cusie.service.article.repository.entity.ArticleDO;
import top.cusie.service.article.repository.mapper.*;
import top.cusie.service.article.service.CategoryService;
import top.cusie.service.article.service.TagService;
import top.cusie.service.user.service.UserFootService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文章Service
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Service
public class ArticleServiceImpl implements ArticleService {


    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private TagService tagService;

    /**
     * 在一个项目中，UserFootService 就是内部服务调用
     * 拆微服务时，这个会作为远程服务访问
     */
    @Autowired
    private UserFootService userFootService;

    /**
     * 获取文章详情
     *
     * @param articleId
     * @return
     */

    /**
     * 获取文章详情
     *
     * @param articleId
     * @return
     */
    @Override
    public ArticleDTO queryArticleDetail(Long articleId) {
        ArticleDTO article = articleRepository.queryArticleDetail(articleId);
        if (article == null) {
            throw new IllegalArgumentException("文章不存在");
        }

        // 更新分类
        CategoryDTO category = article.getCategory();
        category.setCategory(categoryService.getCategoryName(category.getCategoryId()));

        // 更新tagIds
        Set<Long> tagIds = article.getTags().stream().map(TagDTO::getTagId).collect(Collectors.toSet());
        article.setTags(tagService.getTags(tagIds));

        // 更新统计计数
        article.setCount(userFootService.saveArticleFoot(articleId, article.getAuthor(), OperateTypeEnum.READ));
        return article;
    }

    /**
     * 保存文章，当articleId存在时，表示更新记录； 不存在时，表示插入
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveArticle(ArticlePostReq req) {
        ArticleDO article = new ArticleDO();
        // 设置作者ID
        article.setUserId(ReqInfoContext.getReqInfo().getUserId());
        article.setId(req.getArticleId());
        article.setTitle(req.getTitle());
        article.setShortTitle(req.getSubTitle());
        article.setArticleType(ArticleTypeEnum.valueOf(req.getArticleType().toUpperCase()).getCode());
        article.setPicture(req.getCover() == null ? "" : req.getCover());
        article.setCategoryId(req.getCategoryId());
        article.setSource(req.getSource());
        article.setSourceUrl(req.getSourceUrl());
        article.setSummary(req.getSummary());
        article.setStatus(req.pushStatus().getCode());
        article.setDeleted(req.deleted() ? 1 : 0);

        return articleRepository.saveArticle(article, req.getContent(), req.getTagIds());
    }


    @Override
    public ArticleListDTO queryArticlesByCategory(Long categoryId, PageParam page) {
        if (categoryId != null && categoryId <= 0) {
            categoryId = null;
        }
        List<ArticleDO> records = articleRepository.getArticleListByCategoryId(categoryId, page);
        List<ArticleDTO> result = new ArrayList<>();
        records.forEach(record -> {
            ArticleDTO dto = ArticleConverter.toDTO(record);
            dto.setCount(userFootService.queryArticleCountByArticleId(record.getId()));
            result.add(dto);
        });

        ArticleListDTO dto = new ArticleListDTO();
        dto.setArticleList(result);
        dto.setIsMore(result.size() == page.getPageSize());
        return dto;
    }

    @Override
    public ArticleListDTO queryArticlesBySearchKey(String key, PageParam page) {
        if (key == null || key.isEmpty()) {
            return new ArticleListDTO();
        }
        List<ArticleDO> records = articleRepository.getArticleListByBySearchKey(key, page);
        List<ArticleDTO> result = new ArrayList<>();
        records.forEach(record -> {
            ArticleDTO dto = ArticleConverter.toDTO(record);
            dto.setCount(userFootService.queryArticleCountByArticleId(record.getId()));
            result.add(dto);
        });

        ArticleListDTO dto = new ArticleListDTO();
        dto.setArticleList(result);
        dto.setIsMore(result.size() == page.getPageSize());
        return dto;
    }

    @Override
    public void deleteArticle(Long articleId) {
        ArticleDO articleDTO = articleMapper.selectById(articleId);
        if (articleDTO != null) {
            articleDTO.setDeleted(YesOrNoEnum.YES.getCode());
            articleMapper.updateById(articleDTO);
        }
    }

    @Override
    public void operateArticle(Long articleId, PushStatusEnum pushStatusEnum) {
        ArticleDO articleDTO = articleMapper.selectById(articleId);
        if (articleDTO != null) {
            articleDTO.setStatus(pushStatusEnum.getCode());
            articleMapper.updateById(articleDTO);
        }
    }

    @Override
    public ArticleListDTO getArticleListByUserId(Long userId, PageParam pageParam) {

        ArticleListDTO articleListDTO = new ArticleListDTO();
        List<ArticleDO> articleDTOS = articleRepository.getArticleListByUserId(userId, pageParam);
        if (articleDTOS.isEmpty()) {
            articleListDTO.setIsMore(Boolean.FALSE);
            return articleListDTO;
        }

        List<ArticleDTO> articleList = new ArrayList<>();
        for (ArticleDO articleDTO : articleDTOS) {
            ArticleDTO dto = ArticleConverter.toDTO(articleDTO);
            // TODO: 筛其它数据
            articleList.add(dto);
        }

        Boolean isMore = (articleList.size() == pageParam.getPageSize()) ? Boolean.TRUE : Boolean.FALSE;

        articleListDTO.setArticleList(articleList);
        articleListDTO.setIsMore(isMore);
        return articleListDTO;
    }

    @Override
    public ArticleListDTO getCollectionArticleListByUserId(Long userId, PageParam pageParam) {
        ArticleListDTO articleListDTO = new ArticleListDTO();

        List<ArticleDO> articleDTOS = userFootService.queryCollectionArticleList(userId, pageParam);
        if (articleDTOS.isEmpty()) {
            articleListDTO.setIsMore(Boolean.FALSE);
            return articleListDTO;
        }

        List<ArticleDTO> articleList = new ArrayList<>();
        for (ArticleDO articleDTO : articleDTOS) {
            ArticleDTO dto = ArticleConverter.toDTO(articleDTO);
            // TODO: 筛其它数据
            articleList.add(dto);
        }

        Boolean isMore = (articleList.size() == pageParam.getPageSize()) ? Boolean.TRUE : Boolean.FALSE;

        articleListDTO.setArticleList(articleList);
        articleListDTO.setIsMore(isMore);
        return articleListDTO;
    }

    @Override
    public ArticleListDTO getReadArticleListByUserId(Long userId, PageParam pageParam) {
        ArticleListDTO articleListDTO = new ArticleListDTO();

        List<ArticleDO> articleDTOS = userFootService.queryReadArticleList(userId, pageParam);
        if (articleDTOS.isEmpty()) {
            articleListDTO.setIsMore(Boolean.FALSE);
            return articleListDTO;
        }

        List<ArticleDTO> articleList = new ArrayList<>();
        for (ArticleDO articleDTO : articleDTOS) {
            ArticleDTO dto = ArticleConverter.toDTO(articleDTO);
            // TODO: 筛其它数据
            articleList.add(dto);
        }

        Boolean isMore = (articleList.size() == pageParam.getPageSize()) ? Boolean.TRUE : Boolean.FALSE;

        articleListDTO.setArticleList(articleList);
        articleListDTO.setIsMore(isMore);
        return articleListDTO;
    }
}