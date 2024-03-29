package com.littlepage.demo5.connector;

import com.littlepage.demo5.addConnector.HttpServer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * HTTPResponse
 */
public class HttpResponse implements ServletResponse {

    private static  final int BUFFER_SIZE = 1024;

    private HttpRequest request;

    private OutputStream output;

    private PrintWriter writer;

    public HttpResponse(OutputStream output) {
        this.output = output;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    /* this method is used to serve static pages */
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            /* request.getUri has been replaced by request.getRequestURI*/
            File file = new File(HttpServer.WEB_ROOT,request.getUri());
            fis = new FileInputStream(file);
            /*
            HTTP Response = Status-Line
            *(( general-header | response-header | entity-header) CRLF)
            CRLF
            [ message-body ]
            Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
             */
            int ch = fis.read(bytes,0,BUFFER_SIZE);
            while (ch!=-1){
                output.write(bytes,0,ch);
                ch = fis.read(bytes,0,BUFFER_SIZE);
            }
        }catch (IOException e){
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        }finally {
            if(fis!=null){
                fis.close();
            }
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    /** implementation of Servlet Response */

    @Override
    public PrintWriter getWriter() throws IOException {
        // autoflush is true, println() will flush,
        // but print() will not
        writer = new PrintWriter(output,true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
