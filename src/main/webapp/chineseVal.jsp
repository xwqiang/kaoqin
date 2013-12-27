<%@ page  pageEncoding = "gb2312" contentType="image/jpeg" import = "javax.imageio.*,java.util.*,java.awt.image.*,java.awt.*" %>
<%!
    //在此处 获取并生成随机颜色
    Color getRandColor(Random random, int ff, int cc) {
       if (ff > 255)
           ff = 255;
       if (cc > 255)
           cc = 255;
       int r = ff + random.nextInt(cc - ff);
       int g = ff + random.nextInt(cc - ff);
       int b = ff + random.nextInt(cc - ff);
       return new Color(r, g, b);
    } %>
<%
    //在此处 设置JSP页面无缓存
    response.setHeader( "Pragma" , "No-cache" );
    response.setHeader( "Cache-Control" , "no-cache" );
    response.setDateHeader( "Expires" , 0);
    // 设置图片的长宽
    int width = 130;
    int height = 30;
    //设定被随机选取的中文字，此处中文字内容过多，不一一列出，只是举例说明下。 
    String base = "0123456789" ;
    //设置 备选随机汉字的个数
    int length = base.length();
    // 创建缓存图像
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // 获取图像
    Graphics g = image.getGraphics();
    // 创建随机函数的实例
    Random random = new Random();
    //此处 设定图像背景色
    g.setColor(getRandColor(random, 188, 235));
    g.fillRect(0, 0, width, height);
    //设置随机 备选的字体类型
    String[] fontTypes = { "\u5b8b\u4f53" , "\u65b0\u5b8b\u4f53" ,
           "\u9ed1\u4f53" , "\u6977\u4f53" , "\u96b6\u4e66" };
    int fontTypesLength = fontTypes.length;
    // 在图片背景上增加噪点，增加图片分析难度
    g.setColor(getRandColor(random, 180, 199));
    g.setFont( new Font( "Times New Roman" , Font.PLAIN, 14));
    for ( int i = 0; i < 4; i++) {
       g.drawString( "@*@*@*@*@*@*@*" ,
       0, 5 * (i + 2));
    }
    // 取随机产生的验证码 (4 个汉字 )
    // 保存生成的汉字字符串
    String sRand = "" ;
    for ( int i = 0; i < 4; i++) {
       int start = random.nextInt(length);
       String rand = base.substring(start, start + 1);
       sRand += rand;
       // 设置图片上字体的颜色
       g.setColor(getRandColor(random, 10, 150));
       // 设置字体格式
       g.setFont( new Font(fontTypes[random.nextInt(fontTypesLength)],
       Font.BOLD, 18 + random.nextInt(6)));
       // 将此汉字画到验证图片上面
       g.drawString(rand, 24 * i + 10 + random.nextInt(8), 24);
    }
    // 将验证码存入Session中
    session.setAttribute("code" , sRand);
    g.dispose();
    //将 图象输出到JSP页面中
    ImageIO.write(image, "JPEG" , response.getOutputStream());
    //关闭流
    out.clear();
    out=pageContext.pushBody();  
%>