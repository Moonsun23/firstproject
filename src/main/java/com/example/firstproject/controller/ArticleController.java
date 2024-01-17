package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j  // 로깅을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired // 스프링부트가 미리 생성해놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString());

        // 1. DTO 를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id =" + id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){   // id를 매개변수로 받아오기(get Mapping의 어노테이션 url주소에 있는 id를 받아오므로)
        // 수정할 데이터 가져오기(DB에서 데이터를 가져올 때는 리파지터리를 이용한다.
        Article articleEntity = articleRepository.findById(id).orElse(null);  // DB에서 수정할 데이터 가져오기
        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    // update() 메서드는 클라이언트의 데이터 수정요청을 받아 수행한다. 수정폼에서 전송한 데이터는 dto로 받는다!!
    @PostMapping("/articles/update")
    public String update(ArticleForm form){  // 매개변수로 dto 받아오기
        log.info(form.toString());
        // 1. dto를 엔티티로 변환하기
        Article articleEntity = form.toEntity();  // dto(form)를 엔티티(articleEntity)로 변환하기
        log.info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null){
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신해)
        }
        // 3. 수정 결과 페이지로 리다이렉트 하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    // HTTP메서드는 get post patch(put) delete 인데..
    // HTML에서는 post와 get을 제외한 다른 메서드를 제공하지 않는다.
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {                              // (2) id 를 매개변수로 가져와
        log.info("삭제 요청이 들어왔습니다.");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);    // (1) 데이터를 찾아!
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제되었슴미당");
        }
        // 여기까지 삭제순서는 뷰에서 delete 버튼을 눌러서 삭제요청을 하면
        // target이라는 이름을 가진 변수를 통해 몇번 id의 게시글인지 articleRepository에 담아서 가져와. 없으면 null을 반환해
        // 그리고 만약 target이 null이 아니라면 삭제할 대상을 찾아서 삭제해버렷
        // 3. 결과 페이지로 리다이렉트 하기
        return "redirect:/articles";
    }
}
