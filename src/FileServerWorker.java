import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/*
 * 파일명 : FileServerWorker
 * 작성자 : 조성일
 * 작성일 : 2021-03-15
 * 개요 : 클라이언트 하나를 담당할 스레드 클래스
 * */
public class FileServerWorker extends Thread {
	private Socket client;
	private BufferedReader br;
	private PrintWriter pw;
	public FileServerWorker(Socket client) {
		super();
		this.client = client;
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			pw = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String msg;
		try {
			while (true) {
				msg = br.readLine();
				if (msg == null || msg.equals("exit"))
					break;
				pw.println(msg);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pw!=null)pw.close();
				if(br!=null)br.close();
				if(client!=null)client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

}