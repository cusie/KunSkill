package top.cusie.service.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.enums.YesOrNoEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.service.article.dto.TagDTO;
import top.cusie.service.article.repository.entity.TagDO;
import top.cusie.service.article.repository.mapper.TagMapper;
import top.cusie.service.article.service.TagService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签Service
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 查询标签
     *
     * @param tagIds
     * @return
     */
    @Override
    public List<TagDTO> getTags(Collection<Long> tagIds) {
        LambdaQueryWrapper<TagDO> query = Wrappers.lambdaQuery();
        query.eq(TagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .in(TagDO::getId, tagIds);

        List<TagDO> tagList = tagMapper.selectList(query);
        return tagList.stream().map(this::parse).collect(Collectors.toList());
    }


    @Override
    public Long addTag(TagDO tagDTO) {
        tagMapper.insert(tagDTO);
        return tagDTO.getId();
    }

    @Override
    public void updateTag(Long tagId, String tagName) {
        TagDO tagDTO = tagMapper.selectById(tagId);
        if (tagDTO != null) {
            tagDTO.setTagName(tagName);
            tagDTO.setStatus(YesOrNoEnum.NO.getCode());
            tagMapper.updateById(tagDTO);
        }
    }

    @Override
    public void deleteTag(Long tagId) {
        TagDO tagDTO = tagMapper.selectById(tagId);
        if (tagDTO != null) {
            tagDTO.setDeleted(YesOrNoEnum.YES.getCode());
            tagMapper.updateById(tagDTO);
        }
    }

    @Override
    public void operateTag(Long tagId, PushStatusEnum pushStatusEnum) {
        TagDO tagDTO = tagMapper.selectById(tagId);
        if (tagDTO != null) {
            tagDTO.setStatus(pushStatusEnum.getCode());
            tagMapper.updateById(tagDTO);
        }
    }

    @Override
    public IPage<TagDO> getTagByPage(PageParam pageParam) {
        LambdaQueryWrapper<TagDO> query = Wrappers.lambdaQuery();
        query.eq(TagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(TagDO::getStatus, PushStatusEnum.ONLINE.getCode());
        Page<TagDO> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return tagMapper.selectPage(page, query);
    }

    @Override
    public List<TagDTO> getTagListByCategoryId(Long categoryId) {
        LambdaQueryWrapper<TagDO> query = Wrappers.lambdaQuery();
        query.eq(TagDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(TagDO::getCategoryId, categoryId);
        List<TagDO> list = tagMapper.selectList(query);
        return list.stream().map(this::parse).collect(Collectors.toList());
    }

    private TagDTO parse(TagDO tag) {
        return new TagDTO(tag.getCategoryId(), tag.getId(), tag.getTagName());
    }
}
