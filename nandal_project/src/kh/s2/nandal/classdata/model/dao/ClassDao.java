package kh.s2.nandal.classdata.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.jdbc.JdbcTemplate;
import kh.s2.nandal.classdata.model.vo.ClassVo;

public class ClassDao {
//	insert
	public int insert(Connection conn, ClassVo vo) {
		System.out.println(">>> ClassDao insert param : " + vo);
		int result = 0;
		String sql = "insert into class values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // ""안에 ; 는 없어야함
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getClassCode());
			pstmt.setInt(2, vo.getCategoryCode());
			pstmt.setString(3, vo.getClassName()); 
			pstmt.setString(4, vo.getClassImg()); 
			pstmt.setString(5, vo.getClassIntro()); 
			pstmt.setString(6, vo.getClassCur()); 
			pstmt.setString(7, vo.getClassHost()); 
			pstmt.setString(8, vo.getClassAlltime()); 
			pstmt.setString(9, vo.getClassPrd()); 
			pstmt.setInt(10, vo.getAreaCode()); 
			pstmt.setString(11, vo.getClassAddress()); 
			pstmt.setInt(12, vo.getClassPrice()); 
			pstmt.setInt(13, vo.getClassLevel()); 
			pstmt.setInt(14, vo.getClassMin()); 
			pstmt.setInt(15, vo.getClassMax()); 
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao insert return : " + result);
		return result;
	}
//	update
	public int update(Connection conn, ClassVo vo, int classCode) {
		System.out.println(">>> ClassDao update param vo : " + vo);
		System.out.println(">>> ClassDao update param classcode : " + classCode);
		int result = 0;
		
		String sql = "update class set //TODO where class_code=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			//TODO
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao update return : " + result);
		return result;
	}
//	delete
	public int delete(Connection conn, int classCode) {
		System.out.println(">>> ClassDao delete param classcode : " + classCode);
		int result = 0;
		
		String sql = "delete from class where class_code=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classCode);
			//TODO
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao delete return : " + result);
		return result;
	}
	//keywordListCnt - 키워드 목록 조회된 개수 
	public int groupListCnt(Connection conn,int group){
		System.out.println(">>> ClassDao groupListCnt param group : " + group);
		int result = 0;
		
		String sql = "select count(*) cnt"
				+ "    from class c "
				+ "    where c.class_code in (select ca.CLASS_CODE "
				+ "                                from review r join CLASS_APPLY ca on r.REVIEW_CODE = ca.CA_CODE "
				+ "                                where r.REVIEW_GROUP = ?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, group);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao groupListCnt return : " + result);
		return result;
	}
//	selectList - 리뷰 추천유형 해당 목록
	public List<ClassVo> groupList(Connection conn,int group){
		System.out.println(">>> ClassDao groupList param group : " + group);
		List<ClassVo> volist = null;
		
		String sql = "select NVL(ROUND(allavg,1), 0) allavg, NVL(allcnt, 0) allcnt, c.class_code, c.CLASS_IMG, c.CLASS_NAME, c.CLASS_ADDRESS, c.CLASS_PRICE "
				+ "    from class c left join (select ca.CLASS_CODE, avg(r.REVIEW_GRADE) allavg , count(r.review_GRADE) allcnt "
				+ "                                        from (select * from class_apply where CA_CANCEL = 'N') ca join review r  on r.REVIEW_CODE = ca.CA_CODE "
				+ "                                         group by ca.class_code) ca on c.class_code = ca.class_code"
				+ "    where c.class_code in (select ca.CLASS_CODE "
				+ "                                from review r join CLASS_APPLY ca on r.REVIEW_CODE = ca.CA_CODE "
				+ "                                where r.REVIEW_GROUP = ?)"
				+ "	   order by allavg desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, group);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				volist = new ArrayList<ClassVo>();
				do {
					ClassVo vo = new ClassVo();
					vo.setClassCode(rs.getInt("CLASS_CODE"));
					vo.setClassImg(rs.getString("CLASS_IMG"));
					vo.setClassName(rs.getString("CLASS_NAME"));
					String[] addressArr = rs.getString("CLASS_ADDRESS").split("\\s");
					String address = addressArr[0] +" "+addressArr[1];
					vo.setClassAddress(address);
					vo.setClassPrice(rs.getInt("CLASS_PRICE"));
					vo.setAllAvg(rs.getDouble("allavg"));
					vo.setAllCnt(rs.getInt("allcnt"));
					volist.add(vo);
				} while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao groupList return : " + volist);
		return volist;
	}
//	selectList  - 목록조회
	public List<ClassVo> selectList(Connection conn){
		List<ClassVo> volist = null;
		
		String sql = "select * from class";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				volist = new ArrayList<ClassVo>();
				do {
					ClassVo vo = new ClassVo();
					vo.setClassCode(rs.getInt("CLASS_CODE"));
					vo.setClassImg(rs.getString("CLASS_IMG"));
					vo.setClassName(rs.getString("CLASS_NAME"));
					String[] addressArr = rs.getString("CLASS_ADDRESS").split("\\s");
					String address = addressArr[0] +" "+addressArr[1];
					vo.setClassAddress(address);
					vo.setClassPrice(rs.getInt("CLASS_PRICE"));
					volist.add(vo);
				} while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}

		System.out.println(">>> ClassDao selectList return : " + volist);
		return volist;
	}
//	selectList  - 목록조회 페이징 - overloading 
	public List<ClassVo> selectList(Connection conn, int startRnum, int endRnum, String searchword,int searchArea,int searchCategory, List<Integer> searchDay, List<Integer> searchLevel, int searchMin,int searchMax,String classLineUp,int reviewLineUp){
		System.out.println("키워드:"+searchword+",선택지역:"+searchArea+", 카테고리 :" +searchCategory + ",요일:"+searchDay+",난이도:"+searchLevel+",최소금액:"+searchMin+",최고금액:"+searchMax+",classLineUp :" + classLineUp + ", reviewLineUp : " + reviewLineUp);
		System.out.println("startRnum:"+startRnum+",endRnum:"+endRnum);
		
		List<ClassVo> volist = null;
		
		String sqlAllSearch = "select * from (select t1.*, rownum r from "
				+ " (select (select count(ca2.ca_code) from class_apply ca2 where ca2.CA_CANCEL = 'N' and ca2.CA_CODE in (select review_code from review where REVIEW_GROUP = "+reviewLineUp+") and ca2.class_code = c.class_code) groupcnt,"
				+ "			(select count(ca.ca_code)  from CLASS_apply ca where ca.ca_cancel = 'N' and ca.class_code = c.class_code) cacnt, "
				+ "			NVL(ROUND(allavg,1), 0) allavg, NVL(allcnt, 0) allcnt, c.class_code ,c.CLASS_IMG, c.CLASS_NAME, c.CLASS_ADDRESS, c.CLASS_PRICE "
				+ "                                    from class c left join (select ca.CLASS_CODE, avg(r.REVIEW_GRADE) allavg , count(r.review_GRADE) allcnt "
				+ "                                        from (select * from class_apply where CA_CANCEL = 'N') ca join review r  on r.REVIEW_CODE = ca.CA_CODE "
				+ "                                         group by ca.class_code) ca on c.class_code = ca.class_code where 1=1";
		
		if(searchword != null && !searchword.equals("")) {
			sqlAllSearch += " and c.class_name LIKE '" + "%"+searchword+"%'";
		}
		if(searchArea != 0) {
			sqlAllSearch += " and c.area_code =" + searchArea;
		}
		if(searchCategory != 0) {
			sqlAllSearch += " and c.category_code =" + searchCategory;
		}
		if(searchLevel.size() > 0) {
			sqlAllSearch += " and c.class_level in (";
			for(int i = 0; i < searchLevel.size(); i++) {
				if(i == searchLevel.size()-1) sqlAllSearch += searchLevel.get(i)+")";
				else sqlAllSearch += searchLevel.get(i)+",";
			}
		}
		if(searchDay.size() > 0) {
			sqlAllSearch += " and c.class_code in(select class_code from class_schedule where";
			for(int i = 0; i < searchDay.size(); i++) {
				if(i == 0) sqlAllSearch += " bitand(CS_DAY," + searchDay.get(i) +") > 0";
				else sqlAllSearch += " or bitand(CS_DAY," + searchDay.get(i) +") > 0";
			}
			sqlAllSearch  += ")";
		}	
		sqlAllSearch  += " and c.class_price between "+searchMin+" and " +searchMax;
		sqlAllSearch  += " order by";
		
		//1번 정렬 조건 추가
		switch(classLineUp) {
		case "인기순" : 
			sqlAllSearch += " cacnt desc,";
			break;
		case "높은평점순" : 
			sqlAllSearch += " allavg desc,";
			break;
		case "낮은가격순" : 
			sqlAllSearch += " c.class_price asc,";
			break;
		case "높은가격순" : 
			sqlAllSearch += " c.class_price desc,";
			break;
		}
		//2번 정렬 조건 추가
		if(reviewLineUp != 0) {
			sqlAllSearch += " groupcnt desc,";
		}
		
		sqlAllSearch  += " c.class_name asc) t1 ) t2 where r between ? and ?";
		
		System.out.println(sqlAllSearch);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println("키워드 있는 sql 적용");
			pstmt = conn.prepareStatement(sqlAllSearch);
			pstmt.setInt(1, startRnum);
			pstmt.setInt(2, endRnum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				volist = new ArrayList<ClassVo>();
				do {
					ClassVo vo = new ClassVo();
					vo.setClassCode(rs.getInt("CLASS_CODE"));
					vo.setClassImg(rs.getString("CLASS_IMG"));
					vo.setClassName(rs.getString("CLASS_NAME"));
					String[] addressArr = rs.getString("CLASS_ADDRESS").split("\\s");
					String address = addressArr[0] +" "+addressArr[1];
					vo.setClassAddress(address);
					vo.setClassPrice(rs.getInt("CLASS_PRICE"));
					vo.setAllAvg(rs.getDouble("allavg"));
					vo.setAllCnt(rs.getInt("allcnt"));
					volist.add(vo);
				} while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}

		System.out.println(">>> ClassDao selectList return : " + volist);
		return volist;
	}
	//keywordListCnt - 키워드 목록 조회된 개수 
	public int keywordListCnt(Connection conn,String keyword){
		System.out.println(">>> ClassDao keywordListCnt param keyword : " + keyword);
		int result = 0;
		
		String sql = "select count(*) cnt from class where class_name like '%"+keyword+"%'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao keywordList return : " + result);
		return result;
	}
//	selectList - 키워드 목록 조회
	public List<ClassVo> keywordList(Connection conn,String keyword){
		System.out.println(">>> ClassDao keywordList param keyword : " + keyword);
		List<ClassVo> volist = null;
		
		String sql = "select ROUND(allavg,1) allavg, allcnt, c.class_code, c.CLASS_IMG, c.CLASS_NAME, c.CLASS_ADDRESS, c.CLASS_PRICE "
				+ "    from class c left join (select ca.CLASS_CODE, avg(r.REVIEW_GRADE) allavg , count(r.review_GRADE) allcnt "
				+ "                                        from (select * from class_apply where CA_CANCEL = 'N') ca join review r  on r.REVIEW_CODE = ca.CA_CODE "
				+ "                                         group by ca.class_code) ca on c.class_code = ca.class_code "
				+ "    where c.class_name like '%"+keyword+"%'"
				+ "	   order by allavg desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				volist = new ArrayList<ClassVo>();
				do {
					ClassVo vo = new ClassVo();
					vo.setClassCode(rs.getInt("CLASS_CODE"));
					vo.setClassImg(rs.getString("CLASS_IMG"));
					vo.setClassName(rs.getString("CLASS_NAME"));
					String[] addressArr = rs.getString("CLASS_ADDRESS").split("\\s");
					String address = addressArr[0] +" "+addressArr[1];
					vo.setClassAddress(address);
					vo.setClassPrice(rs.getInt("CLASS_PRICE"));
					vo.setAllAvg(rs.getDouble("allavg"));
					vo.setAllCnt(rs.getInt("allcnt"));
					volist.add(vo);
				} while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao keywordList return : " + volist);
		return volist;
	}
//	selectTotalCnt - 클래스 목록 조회 충 클래스 수 
	public int selectTotalCnt(Connection conn, String searchword, int searchArea,int searchCategory, List<Integer> searchDay, List<Integer> searchLevel, int searchMin,int searchMax){
		
		int result = 0;

		String sql = "select count(*) cnt from class where 1=1";
		
		if(searchword != null && !searchword.equals("")) {
			sql += " and class_name LIKE '" + "%"+searchword+"%'";
		}
		if(searchArea != 0) {
			sql += " and area_code =" + searchArea;
		}
		if(searchCategory != 0) {
			sql += " and category_code =" + searchCategory;
		}
		if(searchLevel.size() > 0) {
			sql += " and class_level in (";
			for(int i = 0; i < searchLevel.size(); i++) {
				if(i == searchLevel.size()-1) sql += searchLevel.get(i)+")";
				else sql += searchLevel.get(i)+",";
			}
		}
		if(searchDay.size() > 0) {
			sql += " and class_code in(select class_code from class_schedule where";
			for(int i = 0; i < searchDay.size(); i++) {
				if(i == 0) sql += " bitand(CS_DAY," + searchDay.get(i) +") > 0";
				else sql += "or bitand(CS_DAY," + searchDay.get(i) +") > 0";
			}
			sql  += ")";
		}
		sql  += " and class_price between "+searchMin+" and " +searchMax;
		
		System.out.println(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao selectTotalCnt result : " + result);
		return result;
	}
//	selectOne - 상세조회
	public ClassVo selectOne(Connection conn, int classCode){
		System.out.println(">>> ClassDao selectOne param classcode : " + classCode);
		ClassVo vo = null;
		
		String sql = "select * from class where class_code=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classCode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new ClassVo();
				vo.setClassCode(rs.getInt("class_code"));
				vo.setCategoryCode(rs.getInt("CATEGORY_CODE"));
				vo.setClassName(rs.getString("CLASS_NAME"));
				vo.setClassImg(rs.getString("CLASS_IMG"));
				vo.setClassIntro(rs.getString("CLASS_INTRO"));
				vo.setClassCur(rs.getString("CLASS_CUR"));
				vo.setClassHost(rs.getString("CLASS_HOST"));
				vo.setClassAlltime(rs.getString("CLASS_ALLTIME"));
				vo.setClassPrd(rs.getString("CLASS_PRD"));
				vo.setAreaCode(rs.getInt("AREA_CODE"));
				vo.setClassAddress(rs.getString("CLASS_ADDRESS"));
				vo.setClassPrice(rs.getInt("CLASS_PRICE"));
				vo.setClassLevel(rs.getInt("CLASS_LEVEL"));
				vo.setClassMin(rs.getInt("class_min"));
				vo.setClassMax(rs.getInt("class_max"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		System.out.println(">>> ClassDao selectOne return : " + vo);
		return vo;
	}
}
