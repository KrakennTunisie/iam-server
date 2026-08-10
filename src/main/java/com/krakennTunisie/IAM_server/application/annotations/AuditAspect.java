package com.krakennTunisie.IAM_server.application.annotations;


import com.krakennTunisie.IAM_server.application.ports.in.AuditLogUseCase;
import com.krakennTunisie.IAM_server.domain.model.AuditLog;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogUseCase auditService;

    @Around("@annotation(audit)")
    public Object audit(
            ProceedingJoinPoint joinPoint,
            Audit audit
    ) throws Throwable {

        long start = System.currentTimeMillis();

        try {

            Object result = joinPoint.proceed();

            long execution =
                    System.currentTimeMillis() - start;
            if(result instanceof UserDto){
                saveAudit(audit,"description", ((UserDto) result).getId(), execution, null );

            }

            return result;

        } catch (Exception ex) {

            long execution =
                    System.currentTimeMillis() - start;

            saveAudit(audit,"description", null, execution, ex);

            throw ex;
        }
    }

    private void saveAudit(
            Audit annotation,
            String description,
            String resourceId,
            long execution,
            Exception exception
    ) {


        AuditLog log = new AuditLog();

        log.setAction(annotation.action());

        log.setResource(annotation.entity());

        log.setResourceId(resourceId);

        log.setUsername("anonymous");

        log.setDetails(description);



        auditService.save(log);
    }
}