package top.cusie.test.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.comment.CommentSaveReq;
import top.cusie.service.comment.dto.CommentTreeDTO;
import top.cusie.service.comment.service.impl.CommentServiceImpl;
import top.cusie.test.BasicTest;

import java.util.Map;

/**
 * @author YiHui
 * @date 2022/7/20
 */
@Slf4j
public class CommentDaoTest extends BasicTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Test
//    @Transactional(rollbackFor = Exception.class)
    public void testSaveComment() throws Exception {
        CommentSaveReq commentSaveReq1 = new CommentSaveReq();
        commentSaveReq1.setArticleId(4L);
        commentSaveReq1.setCommentContent("一灰的评论1");
        commentSaveReq1.setParentCommentId(0L);
        commentSaveReq1.setUserId(2L);
        Long commentId1 = commentService.saveComment(commentSaveReq1);

        CommentSaveReq commentSaveReq2 = new CommentSaveReq();
        commentSaveReq2.setArticleId(4L);
        commentSaveReq2.setCommentContent("二哥的评论1");
        commentSaveReq2.setParentCommentId(0L);
        commentSaveReq2.setUserId(3L);
        Long commentId2 = commentService.saveComment(commentSaveReq2);

        CommentSaveReq commentSaveReq3 = new CommentSaveReq();
        commentSaveReq3.setArticleId(4L);
        commentSaveReq3.setCommentContent("一灰的评论2");
        commentSaveReq3.setParentCommentId(commentId1);
        commentSaveReq3.setUserId(2L);
        Long commentId3 = commentService.saveComment(commentSaveReq3);

        CommentSaveReq commentSaveReq4 = new CommentSaveReq();
        commentSaveReq4.setArticleId(4L);
        commentSaveReq4.setCommentContent("二哥的评论2");
        commentSaveReq4.setParentCommentId(commentId2);
        commentSaveReq4.setUserId(3L);
        commentService.saveComment(commentSaveReq4);

        CommentSaveReq commentSaveReq5 = new CommentSaveReq();
        commentSaveReq5.setArticleId(4L);
        commentSaveReq5.setCommentContent("一灰的评论3");
        commentSaveReq5.setParentCommentId(commentId3);
        commentSaveReq5.setUserId(2L);
        commentService.saveComment(commentSaveReq5);
    }


    @Test
    public void testGetCommentList() {
        PageParam pageSearchReq = new PageParam();
        pageSearchReq.setPageNum(4L);
        pageSearchReq.setPageSize(2L);
        Map<Long, CommentTreeDTO> commentTreeDTOList = commentService.getCommentList(4L, pageSearchReq);
        log.info("commentTreeDTOList: {}", commentTreeDTOList);
    }

    @Test
    public void testDeleteComment() throws Exception {
        commentService.deleteComment(12L);
    }
}
