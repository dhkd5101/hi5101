package board;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

public class viewAction extends ActionSupport{
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private boardVO paramClass = new boardVO();
	private boardVO resultClass = new boardVO();
	
	private int currentPage;
	
	private int no;
	private String password;
	
	private String fileUploadPath = "C:/java/upload/";
	
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;
	
	//»ý¼ºÀÚ
	public viewAction()throws IOException{
		
	}
	

}
