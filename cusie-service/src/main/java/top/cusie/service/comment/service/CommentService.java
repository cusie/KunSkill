package top.cusie.service.comment.service;

import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.comment.CommentSaveReq;
import top.cusie.service.comment.dto.CommentTreeDTO;

import java.util.Map;

/**
 * 评论Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface CommentService {

    /**
     * 获取文章评论列表
     *
     * @param articleId     文章ID
     * @param pageSearchReq 分页
     * @return
     */
    Map<Long, CommentTreeDTO> getCommentList(Long articleId, PageParam pageSearchReq);

    /**
     * 更新/保存评论
     *
     * @param commentSaveReq
     * @throws Exception
     */
    Long saveComment(CommentSaveReq commentSaveReq) throws Exception;

    /**
     * 删除评论
     *
     * @param commentId
     * @throws Exception
     */
    void deleteComment(Long commentId) throws Exception;
}
