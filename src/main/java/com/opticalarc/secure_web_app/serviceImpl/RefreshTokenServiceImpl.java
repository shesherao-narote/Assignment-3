package com.opticalarc.secure_web_app.serviceImpl;

import com.opticalarc.secure_web_app.dto.RefreshTokenDTO;
import com.opticalarc.secure_web_app.entity.RefreshToken;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.exception.ResourceNotFoundException;
import com.opticalarc.secure_web_app.repository.RefreshTokenRepository;
import com.opticalarc.secure_web_app.repository.UserRepository;
import com.opticalarc.secure_web_app.service.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RefreshTokenDTO createRefreshToken(String username) {

        long refreshTokenDurationMs = 20 * 60 * 1000;

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User", "Username", username));
        RefreshToken refreshToken = user.getRefreshToken();

        if (refreshToken == null){
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                    .user(userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User", "Username", username)))
                    .build();
        }else {
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        }
        user.setRefreshToken(refreshToken);
        refreshTokenRepository.save(refreshToken);
       return modelMapper.map(refreshToken, RefreshTokenDTO.class);
    }


    @Override
    public RefreshTokenDTO verifyRefreshToken(String refreshToken) throws Exception {
        RefreshToken refreshTokenObj = refreshTokenRepository.findByrefreshToken(refreshToken).orElseThrow(()->new ResourceNotFoundException("Token", "Refresh Token",refreshToken));
       if (!refreshTokenObj.getExpiryDate().isAfter(Instant.now())){
           refreshTokenRepository.delete(refreshTokenObj);
           throw new Exception("Refresh token Expired");
       }
       return modelMapper.map(refreshTokenObj,RefreshTokenDTO.class);
    }
}
