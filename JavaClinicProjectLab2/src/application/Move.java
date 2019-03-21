package application;

public class Move {
	//---------------------------------------
		// 이동 관련
		boolean isMove = false;		// 이동 여부
		int LAST_DIRECTION = 0;		// 마지막으로 회전한 방향
		
		static final int UP = 1;	// 방향 값 상수로 설정
		static final int DOWN = 2;
		static final int RIGHT = 3;
		static final int LEFT = 4;
		
		static final int DISTANCE = 25;	//이동 값
		//---------------------------------------
}
