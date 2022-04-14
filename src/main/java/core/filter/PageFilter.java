package core.filter;

import static core.util.Constants.PREFIX_WEB_INF;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class PageFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String requestPath = request.getServletPath();
		if (requestPath != null && requestPath.endsWith(".html") && !requestPath.endsWith("/index.html")
				|| requestPath.endsWith(".js")
				|| requestPath.endsWith(".css")) {
			request.getRequestDispatcher(getRealPath(requestPath)).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	private String getRealPath(final String requestPath) {
		final int indexOfLastSlash = requestPath.lastIndexOf("/");
		final String path = requestPath.substring(0, indexOfLastSlash);
		final String file = requestPath.substring(indexOfLastSlash);
		final int indexOfLastDot = requestPath.lastIndexOf(".");
		String type = requestPath.substring(indexOfLastDot + 1);
		if (Objects.equals(type, "html")) {
			type = "";
		} else {
			type = "/" + type;
		}
		return PREFIX_WEB_INF + path + type + file;
	}
}
