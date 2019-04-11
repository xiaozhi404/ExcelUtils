package cn.gzhu.test.utils;

import java.util.Random;

public class StringRandom {

    //生成随机数字和字母,
    public String getStringRandom(int length) {

        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            if (i > 5) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                sb.append((char)(random.nextInt(26) + temp));
            } else {
                sb.append(String.valueOf(random.nextInt(10)));
            }
        }
        return sb.toString();
    }

    public static void  main(String[] args) {
        StringRandom test = new StringRandom();
        //测试
        System.out.println(test.getStringRandom(9));
    }
}
