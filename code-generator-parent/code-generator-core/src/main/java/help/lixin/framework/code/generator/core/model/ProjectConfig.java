package help.lixin.framework.code.generator.core.model;

import java.io.Serializable;

public class ProjectConfig implements Serializable {
    // controller
    private String controllerProject = "src/main/java";
    private String controllerPackage = "help.lixin.xxx.controller";
    private String controllerTemplate = "template/cotroller.ftl";

    // model
    private String modelProject = "src/main/java";
    private String modelPackage = "help.lixin.xxx.model";

    // service
    private String serviceProject = "src/main/java";
    private String servicePackage = "help.lixin.xxx.service";
    private String serviceTemplate = "template/service.ftl";
    private String serviceImplTemplate = "template/service_impl.ftl";

    // mapper
    private String mapperProject = "src/main/java";
    private String mapperPackage = "help.lixin.xxx.mapper";


    // mapper xml position
    private String mapperXmlProject = "src/main/resources";
    private String mapperXmlPackage = "mappers";

    public String getControllerProject() {
        return controllerProject;
    }

    public void setControllerProject(String controllerProject) {
        this.controllerProject = controllerProject;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getModelProject() {
        return modelProject;
    }

    public void setModelProject(String modelProject) {
        this.modelProject = modelProject;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(String serviceProject) {
        this.serviceProject = serviceProject;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getMapperProject() {
        return mapperProject;
    }

    public void setMapperProject(String mapperProject) {
        this.mapperProject = mapperProject;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    public String getMapperXmlProject() {
        return mapperXmlProject;
    }

    public void setMapperXmlProject(String mapperXmlProject) {
        this.mapperXmlProject = mapperXmlProject;
    }

    public String getMapperXmlPackage() {
        return mapperXmlPackage;
    }

    public void setMapperXmlPackage(String mapperXmlPackage) {
        this.mapperXmlPackage = mapperXmlPackage;
    }

    public String getControllerTemplate() {
        return controllerTemplate;
    }

    public void setControllerTemplate(String controllerTemplate) {
        this.controllerTemplate = controllerTemplate;
    }

    public String getServiceTemplate() {
        return serviceTemplate;
    }

    public void setServiceTemplate(String serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    public String getServiceImplTemplate() {
        return serviceImplTemplate;
    }

    public void setServiceImplTemplate(String serviceImplTemplate) {
        this.serviceImplTemplate = serviceImplTemplate;
    }

    @Override
    public String toString() {
        return "ProjectConfig{" +
                "controllerProject='" + controllerProject + '\'' +
                ", controllerPackage='" + controllerPackage + '\'' +
                ", controllerTemplate='" + controllerTemplate + '\'' +
                ", modelProject='" + modelProject + '\'' +
                ", modelPackage='" + modelPackage + '\'' +
                ", serviceProject='" + serviceProject + '\'' +
                ", servicePackage='" + servicePackage + '\'' +
                ", serviceTemplate='" + serviceTemplate + '\'' +
                ", serviceImplTemplate='" + serviceImplTemplate + '\'' +
                ", mapperProject='" + mapperProject + '\'' +
                ", mapperPackage='" + mapperPackage + '\'' +
                ", mapperXmlProject='" + mapperXmlProject + '\'' +
                ", mapperXmlPackage='" + mapperXmlPackage + '\'' +
                '}';
    }
}
