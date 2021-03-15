import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * 파일명 : FileClientMain
 * 작성자 : 조성일
 * 작성일 : 2021-03-15
 * 개요 : 서버 접속 후 스트림으로 파일 내용 전송
 * */

public class FileClientMain {

	public static void main(String[] args) {
		String fileName = "info.pdf";
		Socket server = null;
		FileInputStream fis = null;
		DataOutputStream dos = null;
		
		try {
			server = new Socket("127.0.0.1",1234);
			fis = new FileInputStream(fileName);
			dos = new DataOutputStream(server.getOutputStream());
			
			dos.writeUTF(fileName);
			byte[] buf = new byte[1024];
			int total = 0;
			while(true) {
				int count = fis.read(buf);
				if(count == -1) break;
				dos.write(buf,0,count);
				dos.flush();
				total += count;
			}
			System.out.println("파일 전송완료");
			System.out.println(total + " byte 전송");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(dos!=null)dos.close();
				if(fis!=null)fis.close();
				if(server!=null)server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}








