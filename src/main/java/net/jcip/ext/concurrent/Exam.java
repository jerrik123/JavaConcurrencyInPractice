package net.jcip.ext.concurrent;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 模拟一场考试 总共有studentNumber个学生参考 这场考试总共120分钟。 学生最少要考试30分钟 方可交卷。120分钟后 所有学生必须交卷
 * 老师收走试卷
 * 用DelayQueue解决这种延迟处理的问题
 * @author Jerrik
 *
 */
public class Exam {

	private static final int studentNumber = 20;

	static final CountDownLatch countDownLatch = new CountDownLatch(studentNumber + 1);

	public static void main(String[] args) throws InterruptedException {
		DelayQueue<Student> studentQueue = new DelayQueue<Student>();
		
		Random random = new Random();
		for (int i = 0; i < studentNumber; i++) {
			studentQueue.put(new Student("student" + (i + 1), 30 + random.nextInt(120), countDownLatch));
		}
		
		Thread teacherThread = new Thread(new Teacher(studentQueue));
		
		studentQueue.put(new EndExam(studentQueue, 120, countDownLatch, teacherThread));
		
		teacherThread.start();
		
		countDownLatch.await();
		System.out.println("====考试结束,请考生依次走出考场====");
	}

}

/**
 * 学生
 */
class Student implements Runnable, Delayed {
	private String name;
	private long workTime;
	private long submitTime;
	private boolean isForce = false;
	private CountDownLatch countDownLatch;

	public Student() {
	}

	public Student(String name, long workTime, CountDownLatch countDownLatch) {
		this.name = name;
		this.workTime = workTime;
		this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS) + System.nanoTime();
		this.countDownLatch = countDownLatch;
	}

	@Override
	public int compareTo(Delayed o) {
		if (o == null || !(o instanceof Student))
			return 1;
		if (o == this)
			return 0;
		Student s = (Student) o;
		if (this.workTime > s.workTime) {
			return 1;
		} else if (this.workTime == s.workTime) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public long getDelay(TimeUnit timeUnit) {
		return timeUnit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public void run() {
		if (isForce) {
			System.out.println(name + " 交卷, 考试用时" + workTime + "分钟" + " ,实际用时 120分钟");
		} else {
			System.out.println(name + " 交卷, 考试用时" + workTime + "分钟" + " ,实际用时 " + workTime + " 分钟");
		}
		countDownLatch.countDown();
	}

	public boolean isForce() {
		return isForce;
	}

	public void setForce(boolean isForce) {
		this.isForce = isForce;
	}

}

/**
 * 老师
 */
class Teacher implements Runnable {
	private DelayQueue<Student> studentsQueue;

	public Teacher(DelayQueue<Student> students) {
		this.studentsQueue = students;
	}

	@Override
	public void run() {
		try {
			System.out.println("====考试开始,请考生开始作答====");
			while (!Thread.interrupted()) {
				studentsQueue.take().run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class EndExam extends Student {
	private DelayQueue<Student> students;
	private CountDownLatch countDownLatch;
	private Thread teacherThread;

	public EndExam(DelayQueue<Student> students, long workTime, CountDownLatch countDownLatch, Thread teacherThread) {
		super("强制收卷", workTime, countDownLatch);
		this.students = students;
		this.countDownLatch = countDownLatch;
		this.teacherThread = teacherThread;
	}

	@Override
	public void run() {
		teacherThread.interrupt();
		Student tmpStudent;
		for (Iterator<Student> iterator2 = students.iterator(); iterator2.hasNext();) {
			tmpStudent = iterator2.next();
			tmpStudent.setForce(true);
			tmpStudent.run();
		}
		countDownLatch.countDown();
	}

}
