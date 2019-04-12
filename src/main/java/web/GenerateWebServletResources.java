package web;

import util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/4/8 15:16
 * @describe web资源
 */
public abstract class GenerateWebServletResources extends HttpServlet {

    protected final String     resourcePath;

    public GenerateWebServletResources(String resourcePath){
        this.resourcePath = resourcePath;
    }

    protected void returnResourceFile(String fileName, String uri, HttpServletResponse response)
            throws ServletException,
            IOException {

        String filePath = getFilePath(fileName);

        if (filePath.endsWith(".html")) {
            response.setContentType("text/html; charset=utf-8");
        }
        if (fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".gif")) {
                byte[] bytes = WebUtil.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }

            return;
        }

        String text = WebUtil.readFromResource(filePath);
        if (text == null) {
            response.sendRedirect(uri + "/index.html");
            return;
        }
        if (fileName.endsWith(".css")) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(".js")) {
            response.setContentType("text/javascript;charset=utf-8");
        }
        response.getWriter().write(text);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        response.setCharacterEncoding("utf-8");

        if (contextPath == null) { // root context
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());

        if ("/".equals(path)) {
            response.sendRedirect("index.html");
            return;
        }

        if (path.contains(".json")) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(jsonResult(request));
            return;
        }

        // find file in resources path
        returnResourceFile(path, uri, response);
    }
    protected String getFilePath(String fileName) {
        return resourcePath + fileName;
    }

    protected abstract String jsonResult(HttpServletRequest request);
}
