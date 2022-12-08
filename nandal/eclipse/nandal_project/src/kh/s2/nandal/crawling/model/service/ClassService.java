package kh.s2.nandal.crawling.model.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;

import kh.s2.nandal.crawling.jdbc.JdbcTemplate;
import kh.s2.nandal.crawling.model.dao.ClassDao;
import kh.s2.nandal.crawling.model.vo.ClassDto;


public class ClassService {
	private ClassDao dao = new ClassDao();
	
	public int insertMember(ClassDto dto) {
		System.out.println("insertMember()");
		
		int result = 0;
		//jdbcTemplate = 미리 만들어놓은 db와의 연동 및 try~catch문 처리 메소드용 클래스
		Connection conn = JdbcTemplate.getConnection();
		
		//try~catch문으로 묶어주지 않기 위해서 jdbcTemplate에 메소드로 만들어서 사용
		JdbcTemplate.setAutoCommit(conn, false); // 오토커밋 설정
		result = dao.insertClass(conn, dto); // DAO 호출
		if(result > 0) {
			JdbcTemplate.commit(conn); // 커밋
			System.out.println("커밋성공");
		} else {
			JdbcTemplate.rollback(conn); // 롤백
			System.out.println("커밋실패");
		}
		JdbcTemplate.close(conn);
		
		return result;
	}
	public int selectArea(String areaName) {
		System.out.println("selectArea()");
		
		int result = 0;
		//jdbcTemplate = 미리 만들어놓은 db와의 연동 및 try~catch문 처리 메소드용 클래스
		Connection conn = JdbcTemplate.getConnection();
		
		//try~catch문으로 묶어주지 않기 위해서 jdbcTemplate에 메소드로 만들어서 사용
		result = dao.selectArea(conn, areaName); // DAO 호출
		if(result > 0) {
			System.out.println("시도코드 : "+result);
		} else {
			System.out.println("시도코드 가져오기 실패!!!");
		}
		JdbcTemplate.close(conn);
		
		return result;
	}
	public int getImageUrl(String imageUrl, int fileNum) throws IOException {
        URL url = null;
        InputStream in = null;
        OutputStream out = null;
        int result = 0;

        try {
            url = new URL(imageUrl);
            in = url.openStream();

            // 컴퓨터 또는 서버의 저장할 경로(절대패스로 지정해 주세요.)
            out = new FileOutputStream("E:/z_workspace/java/nandal_project/web/images/class/"+fileNum+".jpg");

            while (true) {
                // 루프를 돌면서 이미지데이터를 읽어들이게 됩니다.
                int data = in.read();

                // 데이터값이 -1이면 루프를 종료하고 나오게 됩니다.
                if (data == -1) {
                    result = 0;
                    break;
                }

                // 읽어들인 이미지 데이터값을 컴퓨터 또는 서버공간에 저장하게 됩니다.
                out.write(data);
                result = 1;
            }

            // 저장이 끝난후 사용한 객체는 클로즈를 해줍니다.
            in.close();
            out.close();

        } catch (Exception e) {
        	  // 예외처리
            e.printStackTrace();
        } finally {
            // 만일 에러가 발생해서 클로즈가 안됐을 가능성이 있기에
            // NULL값을 체크후 클로즈 처리를 합니다.
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return result;
    }
}