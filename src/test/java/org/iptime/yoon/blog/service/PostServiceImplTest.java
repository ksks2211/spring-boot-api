package org.iptime.yoon.blog.service;

import org.iptime.yoon.blog.common.config.JpaConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @author rival
 * @since 2023-08-12
 */

@SpringBootTest
@Import(JpaConfig.class)
class PostServiceImplTest {
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private BlogUserRepository blogUserRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//    public BlogUser createBlogUser(String email, String password){
//        BlogUser user = BlogUser.builder()
//            .username(email)
//            .password(password)
//            .build();
//        blogUserRepository.save(user);
//        return user;
//    }
//
//    public Post createPost(BlogUser writer, String title, String content){
//        Post post = Post.builder()
//            .writer(writer)
//            .writerName(writer.getUsername())
//            .title(title)
//            .content(content)
//            .build();
//        postRepository.save(post);
//        return post;
//    }
//    @Test
//    public void findPostListTest(){
//
//        BlogUser writer = createBlogUser("example@email.com", "12341234");
//
//        IntStream.range(0,30).forEach(i-> createPost(writer, "Content " + i,"Title " + i));
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        PostPageResponse postPageResponse = postService.findPostList(pageRequest);
//
//
//        assertThat(postPageResponse.getTotalPages()).isEqualTo(3);
//        assertThat(postPageResponse.getPostList().size()).isEqualTo(10);
//
//
//        postPageResponse.getPostList().forEach(post-> logger.info("Post : {}",post));
//    }
//
//
//
//    @Test
//    public void deletePostTest(){
//        BlogUser writer = createBlogUser("example@email.com", "12341234");
//        Post post = createPost(writer, "Content", "Title");
//
//        logger.info("Post : {}",post);
//
//        Long id = post.getId();
//
//        postService.deletePost(id);
//
//
//        Post foundPost = postRepository.findById(id).orElse(null);
//
//        assertThat(foundPost).isNull();
//
//
//    }
//
//
//
//    @Test
//    public void updatePostTest(){
//
//        BlogUser writer = createBlogUser("example@email.com", "12341234");
//        Post post = createPost(writer, "Content", "Title");
//        LocalDateTime before = post.getUpdatedAt();
//
//        PostCreateRequest updateReq = new PostCreateRequest();
//
//        String title = "Updated Title";
//        String content = "Updated Content";
//        updateReq.setContent(content);
//        updateReq.setTitle(title);
//
//        postService.updatePost(post.getId(),updateReq);
//
//
//        Post updatedPost = postRepository.findById(post.getId()).get();
//
//        assertThat(updatedPost).isNotNull();
//        assertThat(updatedPost.getTitle()).isEqualTo(title);
//        assertThat(updatedPost.getContent()).isEqualTo(content);
//        assertThat(updatedPost.getUpdatedAt()).isAfter(before);
//    }
}