package test.snapshot.model;

/**
 
 */
public class Page {

    /**
     * 保存图片的文件名
     */

    private String fileName;

    /**
     * url地址
     */

    private String urlName;

    public Page(String fileName, String urlName) {

        this.fileName = fileName;
        this.urlName = urlName;

    }

    public String getFileName() {

        return fileName;

    }

    public void setFileName(String fileName) {

        this.fileName = fileName;

    }

    public String getUrlName() {

        return urlName;

    }

    public void setUrlName(String urlName) {

        this.urlName = urlName;

    }

}