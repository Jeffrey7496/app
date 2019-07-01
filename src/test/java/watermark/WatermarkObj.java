package watermark;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/24 16:09
 */
public class WatermarkObj {
    private int srcWidth;// 原图片宽
    private int srcHeight;// 原图片高
    private int imgWidth;// 水印图片宽
    private int imgHeight;// 水印图片高

    private int rowNum;// 水印文字行数
    private int rowSize;//每行文字个数
    private int locationRowNum;// 地点水印行数
    private int fontSize;// 水印文字大小
    private int singleWidth;// 每个字体宽度
    private int singleHeight;// 每行高度
    // 各种间距
    private int rowGapLen;// 文字行间距
    private int sideCorner;// 固定值：
    private int imgGapLen;//水印图片到文字的间隔

    private int maxLocationNum;// 最大总文字个数
    private int locationNum;//实际总文字个数
    private int imgX;
    private int imgY;// 水印图片上边Y轴
    private int textX;// 文字水印左边X轴
    private int textY;// 水印文字上边Y轴

    //每行间隔比率
    private static final float rowGapLenRate = 0.7f;
    //水印图片间隔比率
    private static final float imgGapLenRate = 0.5f;
    //整体水印边界间隔比率
    private static final float sideCornerLenRate = 1f;

    // 一个中文1个字符大小，一个英文0.5个字符大小

    public WatermarkObj(int srcWidth, int srcHeight, int imgWidth, int imgHeight, int rowNum, int locationNum) {
        // 不变数据
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.locationNum = locationNum;//实际总字数
        this.rowNum = rowNum;
        this.locationRowNum = rowNum-1;
        //设置相关参数：
        // 利用水印图片高度进行计算:（0.5*2+0.8*(n-1)+n）*size=height-----2个边角+n-1个间隔+n行字
        int fontSize = (int) (imgHeight/(imgGapLenRate*2+rowGapLenRate*(rowNum-1)+rowNum));//换算出字体大小--根据下面的比例进行的计算
        resizeByFont(fontSize);
        // 直接进行调整

    }

    // 如果总字数超标，则进行调整
    public void resize(){
        // 如果当前总字数小于实际总字数
        while (this.getTotalLocationTextNum()<locationNum){
            resizeByFont(fontSize-1);// 字体减小1个
            //如果字体过小，则调整行数
            if (rowNum*singleHeight+fontSize+imgGapLen*2<=imgHeight){// 如果当前字体大小多出一行---即 水印图片/行高 >当前行，则加一行
                setRowNum(rowNum+1);// 调整行数
                break;
            }
        }
        //再次判断
        if (this.getTotalLocationTextNum()<locationNum){
            resize();
        }
    }

    private void resizeByFont(int fontSize) {// 根据字体进行调整---微调
        this.fontSize = fontSize;
        singleWidth = fontSize;
        rowGapLen = (int) (rowGapLenRate*fontSize);
        singleHeight=fontSize+rowGapLen;
        sideCorner = (int) (sideCornerLenRate*fontSize);
        imgGapLen = (int) (imgGapLenRate*fontSize);
        // 原图片长度-水印图片长度-间隔-2个边角
        rowSize = ((srcWidth-imgWidth-imgGapLen-2*sideCorner)/fontSize);// 每行文字个数
        maxLocationNum = rowSize*locationRowNum;
        imgX = sideCorner;
        this.imgY = srcHeight-imgHeight-sideCorner;// 总高-水印图片高-边距
        this.textX = imgWidth+sideCorner+imgGapLen;
        // 总共的文字高度
        int totalTextHeight = (int) (rowNum*fontSize+(rowNum-1)*rowGapLenRate*fontSize);
        // 调整数据：
        int reCustom = (imgHeight-totalTextHeight)/3;
        // 需要调整居中？
        this.textY = imgY+fontSize+reCustom;// 需要+ fontSize，因为需要获得文字上边高
    }


    public void setRowNum(int rowNum) {// 需要重新计算
        this.rowNum = rowNum;
        this.locationRowNum = rowNum-1;
        //设置相关参数：
        // 利用面积进行计算（0.6*2+0.8*(n-1)+n）*size=height-----2个边角+n-1个间隔+n行字
        int fontSize = (int)(imgHeight/(imgGapLenRate*2+rowGapLenRate*(rowNum-1)+rowNum));//换算出字体大小--根据下面的比例进行的计算
        resizeByFont(fontSize);
    }

    //上下边0.6，左右边1.0，竖线间距0.5，行间距0.7
    public int getTotalLocationTextNum(){
        return this.maxLocationNum;
    }

    public int getRowSize(){
        return this.rowSize;
    }
    // 获取图片水印上边Y轴坐标
    public int getImgY(){
        return imgY;
    }
    // 获取文字水印左边X轴坐标
    public int getTextX(){
        return textX;
    }
    public int getTextY(){
        return textY;
    }

    public int getLocationRowNum(){
        return locationRowNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getRowGapLen() {
        return rowGapLen;
    }

    public int getSideCorner() {
        return sideCorner;
    }

    public int getImgGapLen() {
        return imgGapLen;
    }

    public int getSrcWidth() {
        return srcWidth;
    }

    public int getSrcHeight() {
        return srcHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public int getSingleWidth() {
        return singleWidth;
    }

    public int getSingleHeight() {
        return singleHeight;
    }

    public int getMaxLocationNum() {
        return maxLocationNum;
    }

    public int getImgX() {
        return imgX;
    }
}
