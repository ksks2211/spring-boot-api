package org.iptime.yoon.blog.service;

import lombok.RequiredArgsConstructor;
import org.iptime.yoon.blog.dto.CategoryDto;
import org.iptime.yoon.blog.dto.CategoryRootDto;
import org.iptime.yoon.blog.entity.Category;
import org.iptime.yoon.blog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author rival
 * @since 2023-09-01
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public Long  createIfNotExists(String root, String sub){
        String fullName = root + sub;
        Category category = categoryRepository.findByFullName(fullName)
            .orElseGet(() -> Category.builder()
                .fullName(fullName)
                .root(root)
                .build());
        if(category.getId()==null)categoryRepository.save(category);
        return category.getId();
    }

    public void deleteIfEmpty(String root, String sub){
        String value = root+sub;
        categoryRepository.findByFullName(value).ifPresent(category -> {
            if(category.getPostCount()==0){
                categoryRepository.delete(category);
            }
        });
    }

    @Override
    public Map<String, CategoryDto> getCategories(String root) {
        List<Category> categories = categoryRepository.findAllByRoot(root);

        CategoryRootDto categoryRoot = new CategoryRootDto(root);

        categories.forEach(el->{
            categoryRoot.insert(el.getFullName(),el.getPostCount());
        });

        return categoryRoot.getRoot();
    }

}
