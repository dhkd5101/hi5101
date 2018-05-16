package board;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class writeAction extends ActionSupport {

	public static Reader reader;
	public static SqlMapClient sqlMapper;

	private boardVO paramClass;
	private boardVO resultClass;

	private int currentPage;

	private int no;
	private String subject;
	private String name;
	private String password;
	private String content;
	private String file_orgName;
	private String file_savName;

	Calendar today = Calendar.getInstance();

	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private String fileUploadPath = "C:/java/upload/";

	// �깮�꽦�옄
	public writeAction() throws IOException {

		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml �뙆�씪�쓽 �꽕�젙�궡�슜�쓣 媛��졇�삩�떎.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�쓽 �궡�슜�쓣 �쟻�슜�븳 sqlMapper 媛앹껜 �깮�꽦.
		reader.close();
	}

	public String form() throws Exception {
		return SUCCESS;
	}

	// 寃뚯떆�뙋 WRITE �븸�뀡
	public String execute() throws Exception {

		// �뙆�씪誘명꽣�� 由ъ젅�듃 媛앹껜 �깮�꽦.
		paramClass = new boardVO();
		resultClass = new boardVO();

		// �벑濡앺븷 �빆紐� �꽕�젙.
		paramClass.setSubject(getSubject());
		paramClass.setName(getName());
		paramClass.setPassword(getPassword());
		paramClass.setContent(getContent());
		paramClass.setRegdate(today.getTime());

		// �벑濡� 荑쇰━ �닔�뻾.
		sqlMapper.insert("insertBoard", paramClass);

		// 泥⑤��뙆�씪�쓣 �꽑�깮�뻽�떎硫� �뙆�씪�쓣 �뾽濡쒕뱶�븳�떎.
		if (getUpload() != null) {

			// �벑濡앺븳 湲� 踰덊샇 媛��졇�삤湲�.
			resultClass = (boardVO) sqlMapper.queryForObject("selectLastNo");

			// �떎�젣 �꽌踰꾩뿉 ���옣�맆 �뙆�씪 �씠由꾧낵 �솗�옣�옄 �꽕�젙.
			String file_name = "file_" + resultClass.getNo();
			String file_ext = getUploadFileName().substring(getUploadFileName().lastIndexOf('.') + 1,
					getUploadFileName().length());

			// �꽌踰꾩뿉 �뙆�씪 ���옣.
			File destFile = new File(fileUploadPath + file_name + "." + file_ext);
			FileUtils.copyFile(getUpload(), destFile);

			// �뙆�씪 �젙蹂� �뙆�씪誘명꽣 �꽕�젙.
			paramClass.setNo(resultClass.getNo());
			paramClass.setFile_orgname(getUploadFileName()); // �썝�옒 �뙆�씪 �씠由�
			paramClass.setFile_savname(file_name + "." + file_ext); // �꽌踰꾩뿉 ���옣�븳 �뙆�씪 �씠由�

			// �뙆�씪 �젙蹂� �뾽�뜲�씠�듃.
			sqlMapper.update("updateFile", paramClass);
		}

		return SUCCESS;
	}

	public Calendar getToday() {
		return today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}

	public boardVO getParamClass() {
		return paramClass;
	}

	public void setParamClass(boardVO paramClass) {
		this.paramClass = paramClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getFile_orgName() {
		return file_orgName;
	}

	public void setFile_orgName(String file_orgName) {
		this.file_orgName = file_orgName;
	}

	public String getFile_savName() {
		return file_savName;
	}

	public void setFile_savName(String file_savName) {
		this.file_savName = file_savName;
	}

	public boardVO getResultClass() {
		return resultClass;
	}

	public void setResultClass(boardVO resultClass) {
		this.resultClass = resultClass;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
