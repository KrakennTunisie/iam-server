package com.krakennTunisie.IAM_server.shared;

import com.krakennTunisie.IAM_server.domain.enums.MailEventType;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.MailJobRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailJobRequestFactory {


    public MailJobRequest createAccountCreated(
            String toEmail,
            String login,
            String password
    ) {
        String body = """
                Bonjour,

                Votre compte a été créé avec succès.

                Vos identifiants de connexion sont :

                Login : %s
                Mot de passe : %s

                Vous pouvez maintenant vous connecter à votre espace.

                Cordialement,
                L'équipe KERP
                """.formatted(login, password);

        return new MailJobRequest(
                toEmail,
                "Création de votre compte KERP",
                body,
                MailEventType.ACCOUNT_CREATED,
                List.of()
        );
    }

    public MailJobRequest createPasswordResetOtp(
            String toEmail,
            String otp
    ) {
        String body = """
                Bonjour,

                Vous avez demandé la réinitialisation de votre mot de passe.

                Votre code OTP est : %s

                Ce code est nécessaire pour poursuivre la procédure de réinitialisation de votre mot de passe.

                Cordialement,
                L'équipe KERP
                """.formatted(otp);

        return new MailJobRequest(
                toEmail,
                "Code de réinitialisation de votre mot de passe",
                body,
                MailEventType.PASSWORD_RESET_OTP,
                List.of()
        );
    }

    public MailJobRequest createPasswordChangedConfirmation(
            String toEmail,
            String confirmationCode
    ) {
        String body = """
                Bonjour,

                Votre mot de passe a été modifié avec succès.

                Votre code de confirmation est : %s

                Si vous n'êtes pas à l'origine de cette modification, veuillez contacter immédiatement l'administrateur.

                Cordialement,
                L'équipe KERP
                """.formatted(confirmationCode);

        return new MailJobRequest(
                toEmail,
                "Confirmation de modification de votre mot de passe",
                body,
                MailEventType.PASSWORD_CHANGED_CONFIRMATION,
                List.of()
        );
    }

    public MailJobRequest createAccountActivation(
            String toEmail
    ) {
        String body = """
                Bonjour,

                Votre compte KERP a été activé avec succès.

                Vous pouvez maintenant accéder à votre espace.

                Cordialement,
                L'équipe KERP
                """;

        return new MailJobRequest(
                toEmail,
                "Activation de votre compte KERP",
                body,
                MailEventType.ACCOUNT_ACTIVATION,
                List.of()
        );
    }

    public MailJobRequest createAccountDeactivation(
            String toEmail
    ) {
        String body = """
            Bonjour,

            Votre compte KERP a été désactivé.

            Vous ne pouvez plus accéder à votre espace tant que votre compte reste désactivé.

            Si vous pensez qu'il s'agit d'une erreur, veuillez contacter l'administrateur.

            Cordialement,
            L'équipe KERP
            """;

        return new MailJobRequest(
                toEmail,
                "Désactivation de votre compte KERP",
                body,
                MailEventType.ACCOUNT_DEACTIVATION,
                List.of()
        );
    }

    public MailJobRequest createRoleAssigned(
            String toEmail,
            String role
    ) {
        String body = """
                Bonjour,

                Un nouveau rôle vous a été attribué.

                Rôle attribué : %s

                Vous pouvez vous connecter à votre espace pour consulter vos nouvelles permissions.

                Cordialement,
                L'équipe KERP
                """.formatted(role);

        return new MailJobRequest(
                toEmail,
                "Nouveau rôle attribué à votre compte",
                body,
                MailEventType.ROLE_ASSIGNED,
                List.of()
        );
    }

}
