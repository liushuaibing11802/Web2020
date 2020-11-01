package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.Page;
import vo.User;
import vo.User2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.UserMainDao;

@WebServlet(urlPatterns = "/queryController.do")
public class QueryController extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//锟斤拷锟秸客伙拷锟剿诧拷锟斤拷
		String t="";
		String queryParams=request.getParameter("queryParams");
		String pageParams=request.getParameter("pageParams");
		String pageNumber=request.getParameter("pageNumber");
		System.out.println("查询参数："+queryParams);
		System.out.println("页码参数："+pageParams);
		//锟斤拷json锟街凤拷锟斤拷锟街底拷锟轿猨ava锟斤拷锟斤拷
		Gson gson=new GsonBuilder().serializeNulls().create();
		HashMap<String,Object> mapPage=gson.fromJson(pageParams, HashMap.class);
		Page page=new Page();
		page=gson.fromJson(pageParams, Page.class);
	/*	if(page!=null){
			System.out.println("page:"+page.toString());
		}else{
			System.out.println("page nonono");
		}*/
		User2 user=new User2();
		user=gson.fromJson(queryParams, User2.class);
	/*	if(user!=null){
			System.out.println("user:"+user.toString());
		}else{
			System.out.println("user nonono");
		}*/
		//锟斤拷锟斤拷dao执锟叫达拷锟斤拷
		UserMainDao dao=new UserMainDao();
		ArrayList<User2> rows=dao.query(user, page);
		int total=dao.count(user, page);
		//转锟斤拷为json锟斤拷锟�
		HashMap<String,Object> mapReturn=new HashMap<String,Object>();
		mapReturn.put("rows", rows);
		mapReturn.put("total", total);
		String jsonStr=gson.toJson(mapReturn);
		//锟街凤拷锟斤拷锟斤拷锟�
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close(); 
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
