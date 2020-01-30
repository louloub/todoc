package com.cleanup.todoc;

import com.cleanup.todoc.model.TaskModelUi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    @Test
    public void test_projects() {
        final TaskModelUi task1 = new TaskModelUi(1, 1, "task 1", new Date().getTime());
        final TaskModelUi task2 = new TaskModelUi(2, 2, "task 2", new Date().getTime());
        final TaskModelUi task3 = new TaskModelUi(3, 3, "task 3", new Date().getTime());
        final TaskModelUi task4 = new TaskModelUi(4, 4, "task 4", new Date().getTime());

        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
        assertNull(task4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final TaskModelUi task1 = new TaskModelUi(1, 1, "aaa", 123);
        final TaskModelUi task2 = new TaskModelUi(2, 2, "zzz", 124);
        final TaskModelUi task3 = new TaskModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskModelUi.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final TaskModelUi task1 = new TaskModelUi(1, 1, "aaa", 123);
        final TaskModelUi task2 = new TaskModelUi(2, 2, "zzz", 124);
        final TaskModelUi task3 = new TaskModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskModelUi.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final TaskModelUi task1 = new TaskModelUi(1, 1, "aaa", 123);
        final TaskModelUi task2 = new TaskModelUi(2, 2, "zzz", 124);
        final TaskModelUi task3 = new TaskModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskModelUi.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final TaskModelUi task1 = new TaskModelUi(1, 1, "aaa", 123);
        final TaskModelUi task2 = new TaskModelUi(2, 2, "zzz", 124);
        final TaskModelUi task3 = new TaskModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskModelUi.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}