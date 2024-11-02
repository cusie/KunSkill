package top.cusie.service.comment.converter;


import org.springframework.stereotype.Service;
import top.cusie.api.model.vo.comment.CommentSaveReq;
import top.cusie.service.comment.repository.entity.CommentDO;
import top.cusie.service.comment.dto.CommentTreeDTO;

import java.util.HashMap;

/**
 * 评论转换
 *
 * @author Cusie
 * @date 2024/10/31
 */
public class CommentConverter {

    public static CommentDO toDo(CommentSaveReq req) {
        if (req == null) {
            return null;
        }
        CommentDO commentDO = new CommentDO();
        commentDO.setId(req.getCommentId());
        commentDO.setArticleId(req.getArticleId());
        commentDO.setUserId(req.getUserId());
        commentDO.setContent(req.getCommentContent());
        commentDO.setParentCommentId(req.getParentCommentId());
        return commentDO;
    }

    public static CommentTreeDTO toDTO(CommentDO commentDO) {
        CommentTreeDTO commentTreeDTO = new CommentTreeDTO();
        commentTreeDTO.setUserId(commentDO.getUserId());
        commentTreeDTO.setCommentContent(commentDO.getContent());
        commentTreeDTO.setCommentTime(commentDO.getUpdateTime());
        commentTreeDTO.setParentCommentId(commentDO.getParentCommentId());
        commentTreeDTO.setPraiseCount(0);
        commentTreeDTO.setCommentChilds(new HashMap<>());
        return commentTreeDTO;
    }
}
