package model.server.response;
public class ResponseFile {

    private String outputFileType;
    private String filePath;

    public ResponseFile(String outputFileType, String filePath) {
        this.outputFileType = outputFileType;
        this.filePath = filePath;
    }

    public String getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(String outputFileType) {
        this.outputFileType = outputFileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
