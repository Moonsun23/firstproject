package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)   // DB가 id 자동생성
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {     // 수정할 내용이 있는 경우에만 동작하면 된다.
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }


    // Article 생성자 추가

    // toString() 메서드 추가

}
