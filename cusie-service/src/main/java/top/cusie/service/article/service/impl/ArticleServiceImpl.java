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
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.enums.YesOrNoEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.article.ArticlePostReq;
import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.dto.CategoryDTO;
import top.cusie.service.article.dto.TagDTO;
import top.cusie.service.article.repository.ArticleRepository;
import top.cusie.service.article.service.ArticleService;
import top.cusie.service.article.repository.entity.ArticleDO;
import top.cusie.service.article.repository.mapper.*;
import top.cusie.service.article.service.CategoryService;
import top.cusie.service.article.service.TagService;

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
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    /**
     * 获取文章详情
     *
     * @param articleId
     * @return
     */
    public ArticleDTO queryArticleDetail(Long articleId) {
        ArticleDTO article = articleRepository.queryArticleDetail(articleId);
        // 更新分类
        CategoryDTO category = article.getCategory();
        category.setCategory(categoryService.getCategoryName(category.getCategoryId()));

        // 更新tagIds
        Set<Long> tagIds = article.getTags().stream().map(TagDTO::getTagId).collect(Collectors.toSet());
        article.setTags(tagService.getTags(tagIds));
        return article;
    }

    /**
     * 保存文章，当articleId存在时，表示更新记录； 不存在时，表示插入
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long saveArticle(ArticlePostReq req) {
        ArticleDO article = new ArticleDO();
        // 设置作者ID
        article.setUserId(ReqInfoContext.getReqInfo().getUserId());
        article.setId(req.getArticleId());
        article.setTitle(req.getTitle());
        article.setShortTitle(req.getSubTitle());
        article.setArticleType(ArticleTypeEnum.valueOf(req.getArticleType().toUpperCase()).getCode());
        article.setPicture(req.getCover());
        article.setCategoryId(req.getCategoryId());
        article.setSource(req.getSource());
        article.setSourceUrl(req.getSourceUrl());
        article.setSummary(req.getSummery());
        article.setStatus(req.pushStatus().getCode());
        article.setDeleted(req.deleted() ? 1 : 0);

        return articleRepository.saveArticle(article, req.getContent(), req.getTagIds());
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
    public IPage<ArticleDO> getArticleByPage(PageParam pageParam) {
        LambdaQueryWrapper<ArticleDO> query = Wrappers.lambdaQuery();
        query.eq(ArticleDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(ArticleDO::getStatus, PushStatusEnum.ONLINE.getCode());
        Page<ArticleDO> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return articleMapper.selectPage(page, query);
    }
}