package model.server.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseFileList {
    private final List<ResponseFile> responseFiles = new ArrayList<>();

    public void add(ResponseFile file) {
        responseFiles.add(file);
    }

    public List<ResponseFile> getResponseFiles() {
        return responseFiles;
    }
}
