package help.lixin.framework.code.generator.core.model;

import java.io.Serializable;

public class ProjectConfig implements Serializable {
    // controller
    private String controllerProject = "src/main/java";
    private String controllerPackage = "help.lixin.xxx.controller";

    // model
    private String modelProject = "src/main/java";
    private String modelPackage = "help.lixin.xxx.model";

    // service
    private String serviceProject = "src/main/java";
    private String servicePackage = "help.lixin.xxx.service";
    // mapper
    private String mapperProject = "src/main/java";
    private String mapperPackage = "help.lixin.xxx.mapper";
    // mapper xml position
    private String mapperXmlProject = "src/main/resources";
    private String mapperXmlPackage = "mappers";

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        // controller
        private String controllerProject = "src/main/java";
        private String controllerPackage = "help.lixin.xxx.controller";

        // model
        private String modelProject = "src/main/java";
        private String modelPackage = "help.lixin.xxx.model";

        // service
        private String serviceProject = "src/main/java";
        private String servicePackage = "help.lixin.xxx.service";
        // mapper
        private String mapperProject = "src/main/java";
        private String mapperPackage = "help.lixin.xxx.mapper";
        // mapper xml position
        private String mapperXmlProject = "src/main/resources";
        private String mapperXmlPackage = "mappers";

        public Builder controllerProject(String controllerProject) {
            if (null != controllerProject) {
                this.controllerProject = controllerProject;
            }
            return this;
        }

        public Builder controllerPackage(String controllerPackage) {
            if (null != controllerPackage) {
                this.controllerPackage = controllerPackage;
            }
            return this;
        }

        public Builder modelProject(String modelProject) {
            this.modelProject = modelProject;
            return this;
        }

        public Builder modelPackage(String modelPackage) {
            this.modelPackage = modelPackage;
            return this;
        }

        public Builder serviceProject(String serviceProject) {
            if (null != serviceProject) {
                this.serviceProject = serviceProject;
            }
            return this;
        }

        public Builder servicePackage(String servicePackage) {
            if (null != servicePackage) {
                this.servicePackage = servicePackage;
            }
            return this;
        }

        public Builder mapperProject(String mapperProject) {
            if (null != mapperProject) {
                this.mapperProject = mapperProject;
            }
            return this;
        }

        public Builder mapperPackage(String mapperPackage) {
            if (null != mapperPackage) {
                this.mapperPackage = mapperPackage;
            }
            return this;
        }

        public Builder mapperXmlProject(String mapperXmlProject) {
            if (null != mapperXmlProject) {
                this.mapperXmlProject = mapperXmlProject;
            }
            return this;
        }

        public Builder mapperXmlPackage(String mapperXmlPackage) {
            if (null != mapperXmlPackage) {
                this.mapperXmlPackage = mapperXmlPackage;
            }
            return this;
        }

        public ProjectConfig build() {
            ProjectConfig projectConfig = new ProjectConfig();
            projectConfig.setControllerProject(controllerProject);
            projectConfig.setControllerPackage(controllerPackage);

            projectConfig.setModelProject(modelProject);
            projectConfig.setModelPackage(modelPackage);

            projectConfig.setServiceProject(serviceProject);
            projectConfig.setServicePackage(servicePackage);

            projectConfig.setMapperProject(mapperProject);
            projectConfig.setMapperPackage(mapperPackage);

            projectConfig.setMapperXmlProject(mapperXmlProject);
            projectConfig.setMapperXmlPackage(mapperXmlPackage);
            return projectConfig;
        }
    }

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
}
