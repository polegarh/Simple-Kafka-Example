package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {
	
	public boolean addCustomer(long id, String fName, String lName, long contact){
		boolean status = false;
		Connection con = DBUtil.getConnection();
		String sql = "insert into Customer values(?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			pst.setString(2, fName);
			pst.setString(3, lName);
			pst.setLong(4, contact);
			int count = pst.executeUpdate();
			if (count > 0){
				status = true;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean addProduct(long id, String type, String version, String price){
		boolean status = false;
		Connection con = DBUtil.getConnection();
		String sql = "insert into Product values(?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			pst.setString(2, type);
			pst.setString(3, version);
			pst.setString(4, price);
			int count = pst.executeUpdate();
			if (count > 0){
				status = true;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean addSale(long transid, long custid, long prodid, String timestamp, String amount, int quantity){
		boolean status = false;
		Connection con = DBUtil.getConnection();
		String sql = "insert into Sales values(?,?,?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, transid);
			pst.setLong(2, custid);
			pst.setLong(3, prodid);
			pst.setString(4, timestamp);
			pst.setString(5, amount);
			pst.setInt(6, quantity);
			int count = pst.executeUpdate();
			if (count > 0){
				status = true;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean addRefund(long refundid, long transid, long custid, long prodid, String timestamp, String amount, int quantity){
		boolean status = false;
		Connection con = DBUtil.getConnection();
		String sql = "insert into Refund values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, refundid);
			pst.setLong(2, transid);
			pst.setLong(3, custid);
			pst.setLong(4, prodid);
			pst.setString(5, timestamp);
			pst.setString(6, amount);
			pst.setInt(7, quantity);
			int count = pst.executeUpdate();
			if (count > 0){
				status = true;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return status;
	}
}
