package board;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

import board.pagingAction;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class listAction extends ActionSupport {
	public static Reader reader; // 占쏙옙占쏙옙 占쏙옙트占쏙옙占쏙옙 占쏙옙占쏙옙 reader.
	public static SqlMapClient sqlMapper; // SqlMapClient API占쏙옙 占쏙옙占쏙옙歐占� 占쏙옙占쏙옙 sqlMapper 占쏙옙체.

	private List<boardVO> list = new ArrayList<boardVO>();

	private int currentPage = 1;
	private int totalCount;
	private int blockCount = 10;
	private int blockPage = 5;
	private String pagingHtml;
	private pagingAction page;
	private int num = 0;

	private String searchKeyword;
	private int searchNum;

	public listAction() throws IOException {

		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
	}

	public String execute() throws Exception {
		
		if(getSearchKeyword() != null) 
		{
			return search();
		}
		list = sqlMapper.queryForList("selectAll");

		totalCount = list.size(); // 전체 글 갯수를 구한다
		page = new pagingAction(currentPage, totalCount, blockCount, blockPage, num, "");
		pagingHtml = page.getPagingHtml().toString();

		int lastCount = totalCount;// 현재 페이지에서 보여줄 마지막 글 번호 설정

		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		// 현재 페이지의 마지막 글의 번호가 전체의 마지막 글번호보다 작으면 lastCount를 +1 번호로 설정.

		list = list.subList(page.getStartCount(), lastCount);
		// 전체 리스트에서 현재 페이지 만큼 리스트만 가져옴

		return SUCCESS;
	}

	public String search() throws Exception {

		// searchKeyword = new String(searchKeyword.getBytes("iso-8859-1"),"euc-kr") ;
		// System.out.println(searchKeyword);
		// System.out.println(searchNum);
		if(searchNum == 0){
			list = sqlMapper.queryForList("selectSearchW", "%" + getSearchKeyword() + "%");
		}
		if(searchNum == 1){
			list = sqlMapper.queryForList("selectSearchS", "%" + getSearchKeyword() + "%");
		}
		if(searchNum == 2){
			list = sqlMapper.queryForList("selectSearchC", "%" + getSearchKeyword() + "%");
		}

		totalCount = list.size();
		page = new pagingAction(currentPage, totalCount, blockCount, blockPage, searchNum, getSearchKeyword());
		pagingHtml = page.getPagingHtml().toString();

		int lastCount = totalCount;

		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;

		list = list.subList(page.getStartCount(), lastCount);
		return SUCCESS;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(int searchNum) {
		this.searchNum = searchNum;
	}

	public List<boardVO> getList() {
		return list;
	}

	public void setList(List<boardVO> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public String getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

	public pagingAction getPage() {
		return page;
	}

	public void setPage(pagingAction page) {
		this.page = page;
	}
}
