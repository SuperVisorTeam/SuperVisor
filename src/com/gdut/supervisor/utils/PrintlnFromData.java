package com.gdut.supervisor.utils;
import com.gdut.supervisor.info.Edu_CourseClass;
import com.gdut.supervisor.info.Edu_Survey;

public class PrintlnFromData {

	public static void println(Edu_Survey edu_Survey, String detail) {
		System.out.println(detail + "==============");
		Edu_CourseClass edu_CourseClass = edu_Survey.getCourse_class_no();
		System.out.println("�ն����ͣ� " + edu_Survey.Terminal_Type);
		System.out.println("�ն������� " + edu_Survey.Terminal_Desc);
		System.out.println("�Ƿ���ˣ�0��δ��ˣ��� " + edu_Survey.Audit_Status);
		System.out.println("������ID�� " + edu_Survey.Survey_ID);
		System.out.println("��ѧ���ţ�" + edu_CourseClass.Course_Class_No);
		System.out.println("============================================");
		System.out.println("У������ ��" + edu_CourseClass.School_District);
		System.out.println("�Ͽ�ʱ�� �� " + edu_Survey.lesson_date);
		System.out.println("�Ͽνڴ�  ��" + edu_Survey.lesson_section);
		System.out.println("�Ͽν���  �� " + edu_Survey.lesson_classroom);
		System.out.println("ѧ��ѧԺ ��" + edu_Survey.Student_Faculty);
		System.out.println("��ѧ����� ��" + edu_CourseClass.Teaching_Class_Group);
		System.out.println("ʵ��ѡ��������" + edu_CourseClass.Actual_Population);
		System.out.println("ʵ������ �� " + edu_Survey.Actual_Num);
		System.out.println("ȱϯ����  ��" + edu_Survey.Absent_Num);
		System.out.println("============================================");
		System.out.println("�ٵ�������" + edu_Survey.Late_LeaveEarly_Num);
		System.out.println("����������" + edu_Survey.Truant_Num);
		System.out.println("���������" + edu_Survey.Vacate_Num);
		System.out.println("�Զ���������" + edu_Survey.Food_Eat_Num);
		System.out.println("���ֻ�������" + edu_Survey.Play_Mobil_Num);
		System.out.println("˯����˵��������" + edu_Survey.Sleep_Speak_Num);
		System.out.println("����ѥ������" + edu_Survey.Slipper_Shorts_Num);
		System.out.println("============================================");
		System.out.println("��ʦ�Ƿ�׼ʱ��1��׼ʱ��0�Ƿ�" + edu_Survey.Teacher_Ontime);
		System.out.println("���ν�ʦ��" + edu_CourseClass.Teacher_Name);
		System.out.println("����Աǩ����" + edu_Survey.Supervisor);
		System.out.println("���������" + edu_Survey.Other_Situation);
	}
}
