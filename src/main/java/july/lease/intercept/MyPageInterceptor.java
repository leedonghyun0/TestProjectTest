package july.lease.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyPageInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String requestURI = request.getRequestURI();
		requestURI = requestURI.replaceAll("/members/", "");
		requestURI = requestURI.substring(0, requestURI.indexOf("/") == -1 ? requestURI.length() : requestURI.indexOf("/"));
		String memberId = requestURI; 
		//log.info("마이페이지 인증체크 인터셉터 실행 {}, memberId = {}", requestURI, memberId);

		HttpSession session = request.getSession();
		//log.info("sessionValue = {}", session.getAttribute("memberId"));
		String sessionValue = String.valueOf(session.getAttribute("memberId"));
		if (session == null || sessionValue == null || !memberId.equals(sessionValue)) {
			//log.info("미인증 사용자 요청");
			//log.info("접속사용자 = {}, 요청된 memberId = {}", sessionValue, memberId);
			response.sendRedirect("/login");
			return false;
		}
		//log.info("인증된 사용자 접속 승인");
		return true;
	}

}
