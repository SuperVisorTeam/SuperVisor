package com.gdut.supervisor.utils;
import com.gdut.supervisor.info.Edu_CourseClass;
import com.gdut.supervisor.info.Edu_Survey;

public class PrintlnFromData {

	public static void println(Edu_Survey edu_Survey, String detail) {
		System.out.println(detail + "==============");
		Edu_CourseClass edu_CourseClass = edu_Survey.getCourse_class_no();
		System.out.println("终端类型： " + edu_Survey.Terminal_Type);
		System.out.println("终端描述： " + edu_Survey.Terminal_Desc);
		System.out.println("是否审核（0是未审核）： " + edu_Survey.Audit_Status);
		System.out.println("督查编号ID： " + edu_Survey.Survey_ID);
		System.out.println("教学班编号：" + edu_CourseClass.Course_Class_No);
		System.out.println("============================================");
		System.out.println("校区编码 ：" + edu_CourseClass.School_District);
		System.out.println("上课时间 ： " + edu_Survey.lesson_date);
		System.out.println("上课节次  ：" + edu_Survey.lesson_section);
		System.out.println("上课教室  ： " + edu_Survey.lesson_classroom);
		System.out.println("学生学院 ：" + edu_Survey.Student_Faculty);
		System.out.println("教学班组成 ：" + edu_CourseClass.Teaching_Class_Group);
		System.out.println("实际选课人数：" + edu_CourseClass.Actual_Population);
		System.out.println("实到人数 ： " + edu_Survey.Actual_Num);
		System.out.println("缺席人数  ：" + edu_Survey.Absent_Num);
		System.out.println("============================================");
		System.out.println("迟到人数：" + edu_Survey.Late_LeaveEarly_Num);
		System.out.println("旷课人数：" + edu_Survey.Truant_Num);
		System.out.println("请假人数：" + edu_Survey.Vacate_Num);
		System.out.println("吃东西人数：" + edu_Survey.Food_Eat_Num);
		System.out.println("玩手机人数：" + edu_Survey.Play_Mobil_Num);
		System.out.println("睡觉和说话人数：" + edu_Survey.Sleep_Speak_Num);
		System.out.println("穿拖靴人数：" + edu_Survey.Slipper_Shorts_Num);
		System.out.println("============================================");
		System.out.println("老师是否准时，1是准时，0是否：" + edu_Survey.Teacher_Ontime);
		System.out.println("开课教师：" + edu_CourseClass.Teacher_Name);
		System.out.println("督导员签名：" + edu_Survey.Supervisor);
		System.out.println("其它情况：" + edu_Survey.Other_Situation);
	}
}
