package plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register(
                "checkTheCorrectnessOfFillingTodoOrFixmeCommentTask",
                checkTheCorrectnessOfFillingTodoOrFixmeCommentTask.class
        );
    }
}
