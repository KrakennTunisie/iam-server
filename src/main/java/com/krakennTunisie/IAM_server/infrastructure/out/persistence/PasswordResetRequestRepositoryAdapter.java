package com.krakennTunisie.IAM_server.infrastructure.out.persistence;

import com.krakennTunisie.IAM_server.application.ports.out.MailNotificationRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.PasswordResetRequestRepositoryPort;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.MailJobRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.PasswordResetRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository.PasswordResetRequestRepository;
import com.krakennTunisie.IAM_server.shared.MailJobRequestFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PasswordResetRequestRepositoryAdapter implements PasswordResetRequestRepositoryPort {

    private final PasswordResetRequestRepository passwordResetRequestRepository;

    private final MailJobRequestFactory mailJobRequestFactory;

    private final MailNotificationRepositoryPort mailNotificationRepositoryPort;

    private static final int OTP_TTL_MINUTES = 10;
    private static final int RESET_TOKEN_TTL_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 5;

    @Override
    @Transactional
    public void saveOTP(String email) {

        passwordResetRequestRepository.deleteByEmail(email);


        String otp = generateOtp(6);

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(email);
        passwordResetRequest.setOtpHash(hashOtp(otp));
        passwordResetRequest.setExpiresAt(Instant.now().plus(OTP_TTL_MINUTES, ChronoUnit.MINUTES));

        passwordResetRequestRepository.save(passwordResetRequest);

        MailJobRequest mailJobRequest = mailJobRequestFactory.createPasswordResetOtp(email, otp);

        mailNotificationRepositoryPort.createMailJob(mailJobRequest);
    }

    @Override
    public String verifyOtp(String email, String otp) {
        PasswordResetRequest request = passwordResetRequestRepository
                .findByEmailAndExpiresAtAfterAndOtpVerifiedFalse(email, Instant.now())
                .orElseThrow(()->IAMException.badRequest("Code inexistant ou éxpiré"));

        if (request.getAttempts() >= MAX_ATTEMPTS) {
            throw IAMException.badRequest("Le nombre des essais est épuisé.");
        }

        if (!MessageDigest.isEqual(
                hashOtp(otp).getBytes(StandardCharsets.UTF_8),
                request.getOtpHash().getBytes(StandardCharsets.UTF_8)
        )) {
            request.setAttempts(request.getAttempts() + 1);
            passwordResetRequestRepository.save(request);
            throw IAMException.badRequest("Code érroné.");
        }

        String resetToken = UUID.randomUUID().toString();
        request.setOtpVerified(true);
        request.setResetToken(resetToken);
        request.setExpiresAt(Instant.now().plus(RESET_TOKEN_TTL_MINUTES, ChronoUnit.MINUTES));
        passwordResetRequestRepository.save(request);

        return resetToken;
    }


    @Override
    public Optional<PasswordResetRequest> findByResetTokenAndExpiresAtAfter(String resetToken, Instant now) {
        return passwordResetRequestRepository.findByResetTokenAndExpiresAtAfter(resetToken, now);
    }




    @Override
    public void delete(UUID uuid) {
        passwordResetRequestRepository.deleteById(uuid);
    }

    private String generateOtp(int digits) {
        SecureRandom random = new SecureRandom();
        int max = (int) Math.pow(10, digits);
        return String.format("%0" + digits + "d", random.nextInt(max));
    }

    private String hashOtp(String otp) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(otp.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

}
