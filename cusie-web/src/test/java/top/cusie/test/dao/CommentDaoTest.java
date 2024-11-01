package top.cusie.test.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cusie.api.model.vo.comment.CommentReq;
import top.cusie.service.comment.service.impl.CommentServiceImpl;
import top.cusie.service.comment.dto.CommentTreeDTO;
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
    public void testSaveComment() throws Exception {
        CommentReq commentReq = new CommentReq();
        commentReq.setArticleId(12L);
        commentReq.setCommentContent("comment test1");
        commentReq.setParentCommentId(0L);
        commentReq.setUserId(123L);
        commentService.saveComment(commentReq);
    }


}
