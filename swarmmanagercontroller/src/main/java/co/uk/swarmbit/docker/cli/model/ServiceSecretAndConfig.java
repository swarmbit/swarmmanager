package co.uk.swarmbit.docker.cli.model;

public class ServiceSecretAndConfig {

    private String id;

    private String name;

    private String fileName;

    private String fileUID;

    private String fileGID;

    private Integer fileMode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUID() {
        return fileUID;
    }

    public void setFileUID(String fileUID) {
        this.fileUID = fileUID;
    }

    public String getFileGID() {
        return fileGID;
    }

    public void setFileGID(String fileGID) {
        this.fileGID = fileGID;
    }

    public Integer getFileMode() {
        return fileMode;
    }

    public void setFileMode(Integer fileMode) {
        this.fileMode = fileMode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceSecretAndConfig{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", fileUID='").append(fileUID).append('\'');
        sb.append(", fileGID='").append(fileGID).append('\'');
        sb.append(", fileMode=").append(fileMode);
        sb.append('}');
        return sb.toString();
    }
}
