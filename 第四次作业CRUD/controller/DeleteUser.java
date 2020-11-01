package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserMainDao;

@WebServlet(urlPatterns = "/deleteUser.do")
public class DeleteUser extends HttpServlet {

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
		String ids=request.getParameter("ids");
		System.out.println("删除参数："+ids);
		UserMainDao dao=new UserMainDao();
		boolean contain=ids.contains("/");
		boolean t=true;
		if(contain){
			String id[]=ids.split("/");
			for(int i=0;i<id.length;i++){
				System.out.println("删除参数数组："+id[i]);
				if(!dao.delete(id[i])){
					t=false;
					System.out.println("删除出错啦");
				}
			}
		}else{
			if(!dao.delete(ids)){
				t=false;
				System.out.println("删除出错啦");
			}
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(!t){
			map.put("code", 1);
			map.put("info", "删除失败");
		}else{
			map.put("code", 0);
			map.put("info", "删除成功");
		}
		// ���ùȸ��Gson�⽫map�������ת��Ϊjson�ַ�
		String jsonStr = new Gson().toJson(map);
		// �ַ�������ַ�
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
