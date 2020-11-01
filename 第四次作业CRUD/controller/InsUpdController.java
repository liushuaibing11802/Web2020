package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.User2;

import com.google.gson.Gson;

import dao.UserMainDao;

@WebServlet(urlPatterns = "/updateUser.do")
public class InsUpdController extends HttpServlet {

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
		String title=request.getParameter("title");
		String userName=request.getParameter("userName");
		String chrName=request.getParameter("chrName");
		String email=request.getParameter("email");
		String province=request.getParameter("province");
		String city=request.getParameter("city");
		System.out.println("title:"+title);
		System.out.println("register结果："+title+userName+","+chrName+","+email+","+province+","+city);
		User2 user=new User2(userName,chrName,email,province,city);
		UserMainDao dao=new UserMainDao();
		HashMap<String,Object> map=new HashMap<String,Object>();
		if(title.equals("insert")){
			boolean t=dao.insert(user);
			if(t){
				map.put("code", 0);
				map.put("info", "添加成功");
			}else{
				map.put("code", 1);
				map.put("info", "添加失败");
			}
		}else{
			boolean t=dao.update(user);
			if(t){
				map.put("code", 0);
				map.put("info", "修改成功");
			}else{
				map.put("code", 1);
				map.put("info", "修改失败");
			}
		}
		String jsonStr = new Gson().toJson(map);
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
