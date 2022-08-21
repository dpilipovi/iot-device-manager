package hr.dpilipovic.api.security.api_key;

import hr.dpilipovic.api.configuration.properties.ApiKeyProperties;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

  private final ApiKeyProperties apiKeyProperties;
  private static final String API_KEY_HEADER = "apiKey";

  @Override
  public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws ServletException, IOException {
    var apiKey = getApiKey(request);

    if (apiKeyProperties.getApiKey().equals(apiKey)) {
        var apiToken = new ApiKeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(apiToken);
      } else {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Invalid API Key");
        return;
      }

    chain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getRequestURI().contains("/actuator");
  }

  private String getApiKey(final HttpServletRequest httpRequest) {
    return httpRequest.getHeader(API_KEY_HEADER);
  }

}
