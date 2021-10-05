package com.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.db.dto.BoardDto;

public class BoardDao {
	Connection con = null;
	
	//������ ��� 
	public BoardDao() {
		//driver ����
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//�Խñ� ��ü ��� -> ���
	public List<BoardDto> selectAll(){
		//db ���� ����
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		List<BoardDto> res = new ArrayList<BoardDto>();
		
		String sql = " SELECT * FROM BOARD ";
		
		//sql ���� ����
		try {
			stmt = con.createStatement();
			
			//���� ���� ��� rs�� ����
			rs = stmt.executeQuery(sql);
			
			//rs dto�� ������� ����
			while(rs.next()) {
				BoardDto dto = new BoardDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
			
				res.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	//�Խñ� ���� ���
	public BoardDto selectOne(int bd_no) {
		//db ���� ����
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		BoardDto res = null;
		
		String sql = " SELECT * FROM BOARD WHERE BD_NO=? ";  // ���⿡ ���� ���? ���� �۸� ���
		
		//sql ���� ����
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, bd_no);
			
			//���� ���� ���  rs�� ����
			rs = pstm.executeQuery();
			
			//rs(������ ��ȣ�� �����Ͱ�) -> res�� �ϳ��� ����
			while(rs.next()) {
				res = new BoardDto();
				res.setBd_no(rs.getInt(1));
				res.setBd_name(rs.getString(2));
				res.setBd_title(rs.getString(3));
				res.setBd_content(rs.getString(4));
				res.setBd_date(rs.getDate(5));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	//�� �߰�
	public int insert(BoardDto dto) {
		//db ���� ����
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstm = null;
		int res = 0;
		
		String sql = " INSERT INTO BOARD VALUES(BD_SEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";

		//sql ���� ����
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getBd_name());
			pstm.setString(2, dto.getBd_title());
			pstm.setString(3, dto.getBd_content());
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	//�� ����
	public int update(BoardDto dto) {
		//db ���� ����
				try {
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
		PreparedStatement pstm = null;
		int res = 0;
		
		String sql = " UPDATE BOARD SET BD_TITLE=?, BD_CONTENT=? WHERE BD_NO=? ";
		
		//sql ���� ����
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getBd_title());
			pstm.setString(2, dto.getBd_content());
			pstm.setInt(3, dto.getBd_no());
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return res;
	}
	
	//�� ����
	public int delete(int bd_no) {
		//db ���� ����
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstm = null;
		int res = 0; 
		
		String sql = " DELETE FROM BOARD WHERE BD_NO=? ";
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, bd_no);
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	//�� ������ ����
	public int multiDelete(String[] bd_no) {
		//db ���� ����
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		PreparedStatement pstm = null;
		int res = 0; 
		int[] cnt = null;
		
		String sql = " DELETE FROM BOARD WHERE BD_NO=? ";
		
		try {
			pstm = con.prepareStatement(sql);
			
			for(int i=0; i<bd_no.length; i++) {
				pstm.setString(1, bd_no[i]);
				
				//������ pstm�� ��� �׾� �ѹ��� ó��
				pstm.addBatch();
			}
			
			cnt = pstm.executeBatch();
			
			//���� ���� : -2
			for(int i=0; i<cnt.length; i++) {
				if(cnt[i]==-2) {
					res++;
				}
			}
			
			//��Ƶ� ���� ���� ������ Ŀ��
			if(bd_no.length==res) {
				con.commit();
			} else {
				con.rollback();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
}

