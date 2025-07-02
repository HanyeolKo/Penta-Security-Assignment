package com.assignment.pentasecurity_be.config;

import com.assignment.pentasecurity_be.domain.post.entity.Post;
import com.assignment.pentasecurity_be.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitData  implements CommandLineRunner {

    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        if(postRepository.count()==0){
            for(int i = 1; i <= 100; i++){
                postRepository.save(Post.builder()
                                .title("Sample Title" + i)
                                .content("Sample Content" + i)
                                .author("user" + i)
                                .build());
            }
            log.debug("샘플 게시글 100건 등록");
        }else{
            log.debug("게시글 등록 생략");
        }
    }
}
