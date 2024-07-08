package dev.zettalove.zettalove.security;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.exceptions.accountstatus.AccountDeactivatedException;
import dev.zettalove.zettalove.exceptions.accountstatus.AccountImagesMissingException;
import dev.zettalove.zettalove.exceptions.accountstatus.AccountInterestsMissingException;
import dev.zettalove.zettalove.exceptions.accountstatus.AccountNotCompleteException;
import dev.zettalove.zettalove.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

import static dev.zettalove.zettalove.enums.UserStatus.*;

@Component
public class ProfileCompletionFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    public ProfileCompletionFilter(@Qualifier("handlerExceptionResolver")  HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                JWT jwt = JWTParser.parse(token);
                String userIdStr = jwt.getJWTClaimsSet().getSubject();
                UUID userId = UUID.fromString(userIdStr);

                User user = userRepository.findById(userId).orElseGet(() -> {
                    User newUser = User.builder()
                            .Id(userId)
                            .profileStatus(ACCOUNT_NOT_COMPLETE)
                            .build();
                    userRepository.save(newUser);
                    throw new AccountNotCompleteException();
                });

                String requestURI = request.getRequestURI();

                if (requestURI.endsWith("/users")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if ((user.getProfileStatus() == ACCOUNT_NOT_COMPLETE
                        || user.getProfileStatus() == IMAGES_MISSING
                        || user.getProfileStatus() == INTERESTS_MISSING)
                        && (requestURI.endsWith("/images-setup" )
                        || requestURI.endsWith("/interests-setup")))  {
                    filterChain.doFilter(request, response);
                    return;
                }


                switch (user.getProfileStatus()) {
                    case ACTIVE:
                        filterChain.doFilter(request, response);
                        break;
                    case ACCOUNT_NOT_COMPLETE:
                        throw new AccountNotCompleteException();
                    case INTERESTS_MISSING:
                        throw new AccountInterestsMissingException();
                    case IMAGES_MISSING:
                        throw new AccountImagesMissingException();
                    case DEACTIVATED:
                        throw new AccountDeactivatedException();
                    default:
                        throw new RuntimeException("Unknown user status: " + user.getProfileStatus());
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error parsing JWT token.");
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }
}
