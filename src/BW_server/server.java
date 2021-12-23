package BW_server;

public class server {

	public static void main(String[] args) {
		serverGUI sv = new serverGUI();
		sv.connect();
		
		/*
		 *  00 상대방의 접속
			01 서버 턴
			02 클라이언트 턴
			03 패배하였습니다
			04 비겼습니다
			05 이겼습니다
			06 내가 고른 숫자 보내기
			07 게임 종료
		 */

	}

}
