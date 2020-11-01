package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbc.DatabaseConnection;
import vo.Page;
import vo.User2;

public class UserMainDao {
	/*public User2 get(String userName) {

	}*/

	public ArrayList<User2> query(User2 user, Page page) {
		ArrayList<User2> list = new ArrayList<User2>();
		StringBuffer condition = new StringBuffer();
		if (user.getUserName() != null && !"".equals(user.getUserName())) {
			condition.append(" and userName like '%")
					.append(user.getUserName()).append("%'");
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) {
			condition.append(" and chrName like '%").append(user.getChrName())
					.append("%'");
		}
		if (user.getEmail() != null && !"".equals(user.getEmail())) {
			condition.append(" and email like '%").append(user.getEmail())
					.append("%'");
		}
		if (user.getProvince() != null && !"".equals(user.getProvince())) {
			condition.append(" and province like '%")
					.append(user.getProvince()).append("%'");
		}
		if (user.getCity() != null && !"".equals(user.getCity())) {
			condition.append(" and city like '%").append(user.getCity())
					.append("%'");
		}
		int begin = (page.getPageNumber()-1) * (page.getPageSize());
		String sql = "select* from test where 1=1";
		sql = sql + condition + " order by " + page.getSort() + " "
				+ page.getSortOrder() + " limit " + begin + ","
				+ page.getPageSize();
		System.out.println(sql);
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User2 user2 = new User2();
				user2.setUserName(rs.getString("userName"));
				user2.setChrName(rs.getString("chrName"));
				user2.setEmail(rs.getString("email"));
				user2.setProvince(rs.getString("province"));
				user2.setCity(rs.getString("city"));
				list.add(user2);
				//System.out.println("dao:"+user2.toString());

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return list;

	}

	public int count(User2 user, Page page) {
		int i=0;
		StringBuffer condition = new StringBuffer();
		if (user.getUserName() != null && !"".equals(user.getUserName())) {
			condition.append(" and userName like '%")
					.append(user.getUserName()).append("%'");
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) {
			condition.append(" and chrName like '%").append(user.getChrName())
					.append("%'");
		}
		if (user.getEmail() != null && !"".equals(user.getEmail())) {
			condition.append(" and email like '%").append(user.getEmail())
					.append("%'");
		}
		if (user.getProvince() != null && !"".equals(user.getProvince())) {
			condition.append(" and province like '%")
					.append(user.getProvince()).append("%'");
		}
		if (user.getCity() != null && !"".equals(user.getCity())) {
			condition.append(" and city like '%").append(user.getCity())
					.append("%'");
		}
		int begin = (page.getPageNumber()-1)*page.getPageSize();
		String sql = "select* from test where 1=1";
		sql = sql + condition + " order by " + page.getSort() + " "
				+ page.getSortOrder();
		System.out.println(sql);
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				i++;
				System.out.println("total:"+i);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return i;
	}

	public boolean delete(String ids) {
		boolean t=false;
		String sql="delete from test where userName=?";
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement(sql);
			ps.setString(1, ids);
			ps.executeUpdate();
			t=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return t;
	}

	public boolean update(User2 user) {
		boolean t=false;
		StringBuffer condition = new StringBuffer();
		if (user.getUserName() != null && !"".equals(user.getUserName())) {
			condition.append(" userName = ")
					.append(user.getUserName());
		}
		if (user.getChrName() != null && !"".equals(user.getChrName())) {
			condition.append(", chrName = " ).append(user.getChrName());
		}
		if (user.getEmail() != null && !"".equals(user.getEmail())) {
			condition.append(",email= ").append(user.getEmail());
		}
		if (user.getProvince() != null && !"".equals(user.getProvince())) {
			condition.append(", province=").append(user.getProvince());
		}
		if (user.getCity() != null && !"".equals(user.getCity())) {
			condition.append(",city=").append(user.getCity());
		}
		String sql="update test set";
		sql=sql+condition+" where userName= "+user.getUserName();
		System.out.println("sql:"+sql);
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement(sql);
			ps.executeUpdate();
			t=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return t;
	}

	public boolean insert(User2 user) {
		boolean t=false;
		String sql="insert into test values(?,?,?,?,?)";
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getChrName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getProvince());
			ps.setString(5, user.getCity());
			ps.executeUpdate();
			t=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return t;
	}
}
