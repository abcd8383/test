import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * 파일명 : FileServerMain
 * 작성자 : 조성일
 * 작성일 : 2021-03-15
 * 개요 : 파일 서버 메인 프로그램
 * */

public class FileServerMain {
	private static ArrayList<FileServerWorker> list = new ArrayList<FileServerWorker>();
	public static void main(String[] args) {
		ServerSocket server = null;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		try {

			server = new ServerSocket(1234);
			while(true) {
				Socket client = server.accept();
				dis = new DataInputStream(client.getInputStream());
				String fileName = dis.readUTF();
				String parentPath = client.getInetAddress().toString().replace("/", "");
				File file = new File("c:\\"+parentPath+"\\" + fileName);
				if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
				fos = new FileOutputStream(file);
				byte[] buf = new byte[1024]; 
				while(true) {
					int count = dis.read(buf);
					if(count == -1) break;
					fos.write(buf,0,count);
				FileServerWorker sw = new FileServerWorker(client);
				sw.start();
				list.add(sw);
				System.out.println(list.size()+"명 클라이언트 접속 중입니다.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
