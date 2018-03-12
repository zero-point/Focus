package uk.ac.gla.focuswatch.tests;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import java.util.ArrayList;
import java.util.List;

import uk.ac.gla.focuswatch.model.Task;
import uk.ac.gla.focuswatch.model.TasksDataSource;

public class DatabaseTest extends AndroidTestCase {
    RenamingDelegatingContext mockContext;
    TasksDataSource datasource;

    @Override
    protected void setUp() {
        mockContext = new RenamingDelegatingContext(getContext(), "test_");
        datasource = new TasksDataSource(mockContext);
        datasource.open();
    }

    public void testCreateTask() {
        datasource.createTask("Hello world");
        List<Task> tasks = datasource.getAllTasks();
        List<Task> fakeTasks = new ArrayList<Task>() {{
            add(new Task(1, "Hello world"));
        }};
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getId(), fakeTasks.get(0).getId());
    }

    public void testGetTaskById() {
        datasource.createTask("a task");
        Task task = datasource.getTaskById(1);
        assertEquals(task.getLabel(), "a task");
    }

    @Override
    protected void tearDown() {
        datasource.close();
    }

}
