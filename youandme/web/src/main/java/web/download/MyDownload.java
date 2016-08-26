package web.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import service.youandmeService;


@Component
public class MyDownload{

    @Autowired
    private youandmeService youandmeService;

    public void downloadSolve(int id,HttpServletRequest request, HttpServletResponse response,int userId) throws ServletException, IOException {

        String fileName = (youandmeService.showFileOfId(id)).getFileName();
        String filePath = request.getSession().getServletContext().getRealPath("/")+"pluploadDir/"+userId+"/";
        File file = new File(filePath+fileName);
        if(file.exists()){
            System.out.println("File exists!");
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[2048];
        int len = 0;
        while ((len = fileInputStream.read(bytes))>0){
            outputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        outputStream.close();
    }
}
