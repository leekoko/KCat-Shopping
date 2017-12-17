import java.util.Arrays;

public class TestOther {
	public static void main(String[] args) {
		//只存第一个2的位置
		int[] arr = {1,2,1,1,2,1,2};
		int num= -1 ;    //初始化为-1
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] == 2){
				if(num == -1){
					num = i;
				}
			}
		}
		System.out.println(num);   //打印数组位置
	}
}
