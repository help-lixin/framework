package help.lixin.framework.code.generator.core.model;

import java.io.Serializable;

public class Pom implements Serializable {
    private String pomXmlTemplateFile;
    private String groupId;
    private String artifactId;
    private String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPomXmlTemplateFile() {
        return pomXmlTemplateFile;
    }

    public void setPomXmlTemplateFile(String pomXmlTemplateFile) {
        this.pomXmlTemplateFile = pomXmlTemplateFile;
    }

    @Override
    public String toString() {
        return "Pom{" +
                "pomXmlTemplateFile='" + pomXmlTemplateFile + '\'' +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
