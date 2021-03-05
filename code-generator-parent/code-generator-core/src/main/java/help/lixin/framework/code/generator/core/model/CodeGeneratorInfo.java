package help.lixin.framework.code.generator.core.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeGeneratorInfo implements Serializable {
    private static final String SEPARATOR = "/";
    // 扩展的属性文件
    private String extPropertiesFile;
    // 第三方jar路径
    private String thirdPartyJarPath;

    // 生成代码后的路径(实际是:workDir/proejctName/src/main/java)
    private String workDir;

    private Pom pom = new Pom();

    // 项目名称
    private String projectName;
    // 驱动信息
    private Driver driver = new Driver();
    // 项目信息配置
    private ProjectConfig projectConfig = new ProjectConfig();

    // <context id="simple" targetRuntime="MyBatis3Simple"></context>
    private String id = "simple";
    private String targetRuntime = "MyBatis3Simple";
    private String defaultModelType = "conditional";

    // tables规则信息
    private Set<Table> tables = new HashSet<>();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getThirdPartyJarPath() {
        return thirdPartyJarPath;
    }

    public void setThirdPartyJarPath(String thirdPartyJarPath) {
        this.thirdPartyJarPath = thirdPartyJarPath;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    public String getExtPropertiesFile() {
        return extPropertiesFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetRuntime() {
        return targetRuntime;
    }

    public void setTargetRuntime(String targetRuntime) {
        this.targetRuntime = targetRuntime;
    }

    public String getDefaultModelType() {
        return defaultModelType;
    }

    public void setDefaultModelType(String defaultModelType) {
        this.defaultModelType = defaultModelType;
    }

    public void setExtPropertiesFile(String extPropertiesFile) {
        this.extPropertiesFile = extPropertiesFile;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    public String getWorkDir() {
        return workDir;
    }


    public Pom getPom() {
        return pom;
    }

    public void setPom(Pom pom) {
        this.pom = pom;
    }

    public String getProject() {
        return workDir + SEPARATOR + projectName;
    }

    public String getControllerProject() {
        return getProject() + SEPARATOR + projectConfig.getControllerProject();
    }

    public String getServiceProject() {
        return getProject() + SEPARATOR + projectConfig.getServiceProject();
    }

    public String getModelProject() {
        return getProject() + SEPARATOR + projectConfig.getModelProject();
    }

    public String getMapperProject() {
        return getProject() + SEPARATOR + projectConfig.getMapperProject();
    }

    public String getMapperXmlProject() {
        return getProject() + SEPARATOR + projectConfig.getMapperXmlProject();
    }
}
