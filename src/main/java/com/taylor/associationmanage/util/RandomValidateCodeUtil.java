package com.taylor.associationmanage.util;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class RandomValidateCodeUtil {

    /** 存放验证码的Map中的key */
    public static final String CODE_KEY = "CODEKEY";

    /** 验证码为字符类型 */
    public static final int CHAR_TYPE = 1;

    /** 验证码为算式类型 */
    public static final int EQUATION_TYPE = 2;

    /** 字符验证码用到的字符 */
    private static final String CODE_CHARS = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";

    /** 算数验证码需要的运算符 */
    private static final String OPERATE_CHARS = "+-x";

    /**  验证码图片的宽  */
    private static final int WIDTH = 80;

    /**  验证码图片的高  */
    private static final int HEIGHT = 30;

    /**  干扰线的数量  */
    private static final int LINE_COUNT = 15;

    /** 验证码的长度 */
    private static final int CODE_LENGTH = 4;

    /** 算式运算符个数 */
    private static final int OPERATE_COUNT = 2;

    /** 基础字体大小 */
    private static int FONT_BASIS_SIZE = 16;

    /**
     * 颜色tgb值的边界范围, 防止出现浅色
     */
    private static final int COLOR_BOUND = 210;

    /** 正确的验证码 */
    private String rightCode;

    private Random random = new Random();

    private Font getFont(){
        return new Font("Ffixedsys",Font.CENTER_BASELINE, FONT_BASIS_SIZE + random.nextInt(6));
    }

    /**
     * 获取干扰线随机颜色
     */
    private Color getRandColor(){
        int r = random.nextInt(COLOR_BOUND);
        int g = random.nextInt(COLOR_BOUND);
        int b = random.nextInt(COLOR_BOUND);
        return new Color(r, g, b);
    }

    /**
     * 绘制干扰线
     * @param graphics
     */
    private void drawLine(Graphics graphics){
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        int x1 = random.nextInt(x + random.nextInt(WIDTH));
        int y1 = random.nextInt(y + random.nextInt(HEIGHT));
        graphics.drawLine(x,y,x1,y1);
    }

    /**
     * 获取随机字符验证码
     * @param codeLength 验证码长度
     * @return codes 随机验证码
     */
    private char[] getCharCode(int codeLength){
        char[] codes = new char[codeLength];
        for (int i = 0; i < codeLength; i++) {
            codes[i] = CODE_CHARS.charAt(random.nextInt(CODE_CHARS.length()));
        }
        rightCode = String.valueOf(codes);
        return codes;
    }

    /**
     * 获取算式验证码, 10以内运算
     * @param operateConut 运算符数量
     * @return equation 算式验证码
     */
    private char[] getEquation(int operateConut){
        int[] nums = new int[operateConut+1];
        char[] operates = new char[operateConut];
        char[] equation = new char[operateConut*2+1];
        for (int i = 0; i < operateConut; i++) {
            nums[i] = random.nextInt(10);
            operates[i] = OPERATE_CHARS.charAt(random.nextInt(OPERATE_CHARS.length()));
        }
        nums[operateConut] = random.nextInt(10);
        for (int i = 0; i < operateConut; i++) {
            equation[i*2] = String.valueOf(nums[i]).charAt(0);
            equation[i*2+1] = operates[i];
        }
        equation[operateConut*2] = String.valueOf(nums[operateConut]).charAt(0);
        int result = getResult(nums,operates);
        rightCode = String.valueOf(result);
//        System.out.println(rightCode);
        return equation;
    }

    /**
     * 计算算式结果
     * @param nums 数字
     * @param operates 运算符
     * @return
     */
    int getResult(int[] nums,char[] operates){
        int end = nums[0];
        int last = 0;
        int flag = 0;
        for (int i = 0; i <operates.length ; i++) {
            switch (operates[i]){
                case '-':
                    if( i>=operates.length-1 || operates[i+1] != 'x'){   //如果是最后一个,或者下一个运算符不为x
                        end = end - nums[i+1];
                    }
                    else {                                                //否则(下一个运算符为x),记录下一个数字
                        last = nums[i+1];
                        flag = 1;
                    }
                    break;
                case '+':
                    if( i+1>=operates.length || operates[i+1] != 'x'){
                        end = end + nums[i+1];
                    }
                    else{
                        last = nums[i+1];
                        flag = 2;
                    }
                    break;
                case 'x':
                    if(flag == 0){
                        end *= nums[i+1];
                    }
                    else if(flag == 1){
                        last = last*nums[i+1];                               //先把x两边的算一下
                        if( i>=operates.length-1 || operates[i+1] != 'x'){  //如果是最后一个,或者下一个不是*,
                                                                    // 可以算掉之前的运算符,如果不是,啥都不干
                            end = end - last;
                            flag = 0;
                        }
                    }
                    else if(flag == 2){
                        last = last*nums[i+1];
                        if( i+1>=operates.length || operates[i+1] != 'x'){
                            end = end + last;
                            flag = 0;
                        }
                    }
                    break;
            }
        }
        return end;
    }

    /**
     * 把验证码或算式画入验证图片中
     * @param graphics 图像
     * @param codes 验证码或算式
     */
    private void drawString(Graphics graphics, char[] codes){
        for (int i = 0; i < codes.length; i++) {
            graphics.setFont(getFont());
            graphics.setColor(getRandColor());
            graphics.translate(random.nextInt(2),0);
            graphics.drawString(String.valueOf(codes[i]),
                    FONT_BASIS_SIZE*i+random.nextInt(2),20+random.nextInt(8));
        }
    }

    /**
     * 绘制验证码图片
     * @param httpServletRequest
     * @param httpServletResponse
     * @param codeType 验证码类型
     */
    public void getRandomCodeImage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int codeType){
        HttpSession session = httpServletRequest.getSession();
        BufferedImage bufferedImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_BGR);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.fillRect(0,0,WIDTH,HEIGHT);
        char[] codes = null;
        graphics.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, FONT_BASIS_SIZE));
        if(codeType == RandomValidateCodeUtil.CHAR_TYPE){      //如果是字符类型验证码,则画干扰线
            graphics.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, FONT_BASIS_SIZE));
            for (int i = 0; i < LINE_COUNT; i++) {
                graphics.setColor(getRandColor());
                drawLine(graphics);
            }
            codes = getCharCode(CODE_LENGTH);
        }
        else if(codeType == RandomValidateCodeUtil.EQUATION_TYPE){  //如果是算式类型验证码
            codes = getEquation(OPERATE_COUNT);
        }
        else {
            throw new IllegalArgumentException("codeType must be CHAR_TYPE or EQUATION_TYPE");
        }
        drawString(graphics,codes);
        session.removeAttribute(CODE_KEY);
        session.setAttribute(CODE_KEY,rightCode);
        graphics.dispose();
        try {
            ImageIO.write(bufferedImage,"JPEG",httpServletResponse.getOutputStream());
        } catch (IOException e) {
            System.out.println("-----------图片输出错误---------");
        }
    }



}
