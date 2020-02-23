package help.lixin.framework.flowable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import help.lixin.framework.workflow.api.IProcessDefinitionService;
import help.lixin.framework.workflow.api.IProcessInstanceService;
import help.lixin.framework.workflow.model.Page;
import help.lixin.framework.workflow.model.ProcessDefinition;
import help.lixin.framework.workflow.model.ProcessDefinitionQueryCondition;

@SpringBootApplication
public class MyApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MyApplication.class, args);
		IProcessDefinitionService processDefinitionService = ctx.getBean(IProcessDefinitionService.class);
		Page<List<ProcessDefinition>> page = processDefinitionService.list(
				ProcessDefinitionQueryCondition  //
				.newBuilder()   //
				.index(0)   //
				.pageSize(2)  //
				.processDefinitionKey("oneTaskProcess")  //
				.build());
		
		Map<String,Object> variables = new HashMap<>();
		variables.put("test", "test");
		IProcessInstanceService processInstanceService = ctx.getBean(IProcessInstanceService.class);
		help.lixin.framework.workflow.model.ProcessInstance instance = processInstanceService.startProcessInstanceByKey(page.getData().get(0).getKey(), variables);
		System.out.println(instance);
	}

//	@Bean
	public CommandLineRunner init(  //
			final RepositoryService repositoryService, // 
			final RuntimeService runtimeService,//
			final TaskService taskService) {
		return new CommandLineRunner() {
			@SuppressWarnings("unused")
			@Override
			public void run(String... strings) throws Exception {
//				byte[] bytes = IOUtils.toByteArray(new FileInputStream("/Users/lixin/framework/workflow-parent/flowable-integration/src/test/resources/one-task-process2.bpmn"));
//				Deployment deployment = repositoryService.createDeployment()  //
//				.addBytes("one-task-process2.bpmn", bytes)  //
//				.deploy();

				
//				List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
//              .deploymentId("a9f6fcf4-5542-11ea-ae1b-724c5809725c")
//				.processDefinitionId("oneTaskProcess:1:aa384bb6-5542-11ea-ae1b-724c5809725c")
//				.processDefinitionKey("oneTaskProcess")
				 // 去重
//				.latestVersion()
//				.list();
				
//				System.out.println(
//						"Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
//				System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
//				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneTaskProcess");
//				System.out.println(processInstance);
//				List<Task> tasks  = taskService.createTaskQuery()
//				.taskAssignee("kermit")
//				.list();
//				System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
			}
		};
	}
}

