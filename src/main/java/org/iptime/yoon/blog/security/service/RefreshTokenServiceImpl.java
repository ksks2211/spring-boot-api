package org.iptime.yoon.blog.security.service;

import lombok.RequiredArgsConstructor;
import org.iptime.yoon.blog.security.entity.BlogUser;
import org.iptime.yoon.blog.security.entity.RefreshToken;
import org.iptime.yoon.blog.security.exception.ExpiredRefreshTokenException;
import org.iptime.yoon.blog.security.exception.InvalidRefreshTokenException;
import org.iptime.yoon.blog.security.repository.BlogUserRepository;
import org.iptime.yoon.blog.security.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.iptime.yoon.blog.security.service.BlogUserServiceImpl.fromEntity;


/**
 * @author rival
 * @since 2023-08-17
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlogUserRepository blogUserRepository;

    @Value("${auth.refresh-token.refresh-exp-hours}")
    private long REFRESH_EXP_HOURS;

    private LocalDateTime getExpiryDate(){
        return LocalDateTime.now().plusHours(REFRESH_EXP_HOURS);
    }

    @Override
    @Transactional
    public String createToken(String username){
        Long id = blogUserRepository.findIdByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username : " + username + " is not found")).getId();

        BlogUser user = BlogUser.builder().id(id).build();
        RefreshToken token;
        Optional<RefreshToken> optional = refreshTokenRepository.findByUser(user);

        if (optional.isPresent() && !optional.get().isExpired()) {
            token = optional.get();
        } else {
            optional.ifPresent(refreshToken -> refreshTokenRepository.deleteById(refreshToken.getId()));
            token = RefreshToken.builder()
                .expiryDate(getExpiryDate())
                .user(BlogUser.builder().id(id).build())
                .build();
        }

        refreshTokenRepository.save(token);
        return token.getValue();
    }

    @Override
    @Transactional
    public User validateTokenAndGetUser(String token) throws InvalidRefreshTokenException, ExpiredRefreshTokenException {


        RefreshToken refreshToken = refreshTokenRepository.findById(token).orElseThrow(() -> new InvalidRefreshTokenException(token));
        if(refreshToken.isExpired()){
            refreshTokenRepository.delete(refreshToken);
            throw new ExpiredRefreshTokenException(token);
        }

        BlogUser blogUser = refreshToken.getUser();

        User user  = fromEntity(blogUser);
        user.eraseCredentials();
        return user;
    }

    @Override
    public void removeToken(String token) {
        refreshTokenRepository.deleteById(token);
    }




}