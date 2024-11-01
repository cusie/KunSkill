package top.cusie.web.front.article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.cusie.api.model.vo.article.ArticlePostReq;
import top.cusie.service.article.dto.ArticleDTO;
import top.cusie.service.article.service.ArticleService;

/**
 * 文章
 */
@Controller
@RequestMapping(path = "article")
public class ArticleController {
    private ArticleService articleService;

    /**
     * 文章编辑页
     *
     * @param articleId
     * @return
     */
    @GetMapping(path = "edit")
    public String edit(@RequestParam(required = false) Long articleId, Model model) {
        if (articleId != null) {
            ArticleDTO article = articleService.queryArticleDetail(articleId);
            model.addAttribute("article", article);
        }

        return "article/edit";
    }

    /**
     * 发布文章，完成后跳转到详情页
     *
     * @return
     */
    @PostMapping(path = "post")
    public String post(ArticlePostReq req) {
        articleService.saveArticle(req);
        return "";
    }

}
