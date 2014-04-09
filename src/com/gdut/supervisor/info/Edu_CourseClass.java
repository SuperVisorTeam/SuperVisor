package com.gdut.supervisor.info;

/***********************************************************************
 * Module:  edu_CourseClass.java
 * Author:  zhh
 * Purpose: Defines the Class edu_CourseClass
 ***********************************************************************/


/**
 * edu_CourseClass | 教学班基本信�?
 * 
 */

public class Edu_CourseClass {
	/**
	 * 教学班编�?Unique)
	 * 
	 */
	public String Course_Class_No;
	/**
	 * 教学班组�?
	 * 
	 */
	public String Teaching_Class_Group;
	/**
	 * 年级
	 */
	public String student_grade;
	/**
	 * �?��学期
	 * 
	 */
	public String Semester;
	/**
	 * 校区编码
	 * 
	 */
	public String School_District;
	/**
	 * �?��教师
	 * 
	 */
	public String Teacher_Name;
	/**
	 * 教师职工�?
	 * 
	 */
	public String Teacher_Staff_ID;
	/**
	 * 教师职称
	 * 
	 */
	public String Teacher_Title;
	/**
	 * �?��学院
	 * 
	 */
	public String Commence_Dept;
	/**
	 * 课程代码
	 * 
	 */
	public String Course_No;
	/**
	 * 课程名称
	 * 
	 */
	public String Course_Name;
	/**
	 * 课程性质(D)
	 * 
	 */
	public String Course_Nature;
	/**
	 * 课程类别(D)
	 * 
	 */
	public String Course_Category;
	/**
	 * 计划上课人数
	 * 
	 */
	public int Plan_Population = 0;
	/**
	 * 已�?课人�?
	 * 
	 */
	public int Actual_Population = 0;
	/**
	 * 单双周状�?
	 * 
	 */
	public int Mono_Week_State = 0;
	/**
	 * 连排状�?
	 * 
	 */
	public int Continuous_State = 0;
	/**
	 * 班级类型
	 * 
	 */
	public int Class_Category;
	/**
	 * 双语flg
	 * 
	 */
	public int Bilingual_Flag;
	/**
	 * 周学�?
	 * 
	 */
	public String Week_Credit_Hour;
	/**
	 * 学分
	 * 
	 */
	public float Credit_Point;
	/**
	 * 总学�?
	 * 
	 */
	public String Total_Credit_Hour;
	/**
	 * 讲课学时
	 * 
	 */
	public int Teaching_Hours;
	/**
	 * 实验学时
	 * 
	 */
	public int Experiment_Hours;
	/**
	 * 上机学时
	 * 
	 */
	public int PC_Hours;
	/**
	 * 课程实践学时
	 * 
	 */
	public int Design_Hours;
	/**
	 * 习题课学�?
	 * 
	 */
	public int Exercise_Hours;
	/**
	 * 课内上机学时
	 * 
	 */
	public int Inside_PC_Hours;
	/**
	 * 课外上机学时
	 * 
	 */
	public int Outside_PC_Hours;
	/**
	 * 课外学时
	 * 
	 */
	public int Outside_Hours;
	/**
	 * 起止�?
	 * 
	 */
	public String Week_Period;
	/**
	 * 上课时间
	 * 
	 */
	public String Lesson_Time;
	/**
	 * 上课地点
	 * 
	 */
	public String Lesson_Place;
	/**
	 * 教材名称
	 * 
	 */
	public String Textbook_Name;
	/**
	 * 教材作�?
	 * 
	 */
	public String Textbook_Author;
	/**
	 * 出版�?
	 * 
	 */
	public String Textbook_Publisher;
	/**
	 * 版别
	 * 
	 */
	public String Textbook_Version;
	/**
	 * 获奖情况
	 * 
	 */
	public String Textbook_Award;
	/**
	 * 实验场地
	 * 
	 */
	public String Experiment_Place;
	/**
	 * 考核
	 * 
	 */
	public String Check_Mode;
	/**
	 * 教室类型编码
	 * 
	 */
	public String Classroom_Category;
	/**
	 * 教室类型名称
	 * 
	 */
	public String Classroom_Category_Name;
	/**
	 * 备注
	 * 
	 */
	public String Course_Class_Remark;
	/**
	 * 学生学院
	 */
	public String Student_Faculty;
	/**
	 * 创建者编�?
	 * 
	 */
	public String Add_User;
	/**
	 * 创建日期
	 * 
	 */
	public String Add_Time;
	/**
	 * 修改者编�?
	 * 
	 */
	public String Modify_User;
	/**
	 * 修改日期
	 * 
	 */
	public String Modify_Time;

	// public Collection<Edu_PlanExec> edu_planexec;
	//
	// public Collection<Edu_Schedule> edu_schedule;
	//
	// public Collection<Edu_Survey> edu_Survey;

	public String getCourse_Class_No() {
		return Course_Class_No;
	}

	public void setCourse_Class_No(String course_Class_No) {
		Course_Class_No = course_Class_No;
	}

	public String getTeaching_Class_Group() {
		return Teaching_Class_Group;
	}

	public void setTeaching_Class_Group(String teaching_Class_Group) {
		Teaching_Class_Group = teaching_Class_Group;
	}

	public String getStudent_grade() {
		return student_grade;
	}

	public void setStudent_grade(String student_grade) {
		this.student_grade = student_grade;
	}

	public String getSemester() {
		return Semester;
	}

	public void setSemester(String semester) {
		Semester = semester;
	}

	public String getSchool_District() {
		return School_District;
	}

	public void setSchool_District(String school_District) {
		School_District = school_District;
	}

	public String getTeacher_Name() {
		return Teacher_Name;
	}

	public void setTeacher_Name(String teacher_Name) {
		Teacher_Name = teacher_Name;
	}

	public String getTeacher_Staff_ID() {
		return Teacher_Staff_ID;
	}

	public void setTeacher_Staff_ID(String teacher_Staff_ID) {
		Teacher_Staff_ID = teacher_Staff_ID;
	}

	public String getTeacher_Title() {
		return Teacher_Title;
	}

	public void setTeacher_Title(String teacher_Title) {
		Teacher_Title = teacher_Title;
	}

	public String getCommence_Dept() {
		return Commence_Dept;
	}

	public void setCommence_Dept(String commence_Dept) {
		Commence_Dept = commence_Dept;
	}

	public String getCourse_No() {
		return Course_No;
	}

	public void setCourse_No(String course_No) {
		Course_No = course_No;
	}

	public String getCourse_Name() {
		return Course_Name;
	}

	public void setCourse_Name(String course_Name) {
		Course_Name = course_Name;
	}

	public String getCourse_Nature() {
		return Course_Nature;
	}

	public void setCourse_Nature(String course_Nature) {
		Course_Nature = course_Nature;
	}

	public String getCourse_Category() {
		return Course_Category;
	}

	public void setCourse_Category(String course_Category) {
		Course_Category = course_Category;
	}

	public int getPlan_Population() {
		return Plan_Population;
	}

	public void setPlan_Population(int plan_Population) {
		Plan_Population = plan_Population;
	}

	public int getActual_Population() {
		return Actual_Population;
	}

	public void setActual_Population(int actual_Population) {
		Actual_Population = actual_Population;
	}

	public int getMono_Week_State() {
		return Mono_Week_State;
	}

	public void setMono_Week_State(int mono_Week_State) {
		Mono_Week_State = mono_Week_State;
	}

	public int getContinuous_State() {
		return Continuous_State;
	}

	public void setContinuous_State(int continuous_State) {
		Continuous_State = continuous_State;
	}

	public int getClass_Category() {
		return Class_Category;
	}

	public void setClass_Category(int class_Category) {
		Class_Category = class_Category;
	}

	public int getBilingual_Flag() {
		return Bilingual_Flag;
	}

	public void setBilingual_Flag(int bilingual_Flag) {
		Bilingual_Flag = bilingual_Flag;
	}

	public String getWeek_Credit_Hour() {
		return Week_Credit_Hour;
	}

	public void setWeek_Credit_Hour(String week_Credit_Hour) {
		Week_Credit_Hour = week_Credit_Hour;
	}

	public float getCredit_Point() {
		return Credit_Point;
	}

	public void setCredit_Point(float credit_Point) {
		Credit_Point = credit_Point;
	}

	public String getTotal_Credit_Hour() {
		return Total_Credit_Hour;
	}

	public void setTotal_Credit_Hour(String total_Credit_Hour) {
		Total_Credit_Hour = total_Credit_Hour;
	}

	public int getTeaching_Hours() {
		return Teaching_Hours;
	}

	public void setTeaching_Hours(int teaching_Hours) {
		Teaching_Hours = teaching_Hours;
	}

	public int getExperiment_Hours() {
		return Experiment_Hours;
	}

	public void setExperiment_Hours(int experiment_Hours) {
		Experiment_Hours = experiment_Hours;
	}

	public int getPC_Hours() {
		return PC_Hours;
	}

	public void setPC_Hours(int pC_Hours) {
		PC_Hours = pC_Hours;
	}

	public int getDesign_Hours() {
		return Design_Hours;
	}

	public void setDesign_Hours(int design_Hours) {
		Design_Hours = design_Hours;
	}

	public int getExercise_Hours() {
		return Exercise_Hours;
	}

	public void setExercise_Hours(int exercise_Hours) {
		Exercise_Hours = exercise_Hours;
	}

	public int getInside_PC_Hours() {
		return Inside_PC_Hours;
	}

	public void setInside_PC_Hours(int inside_PC_Hours) {
		Inside_PC_Hours = inside_PC_Hours;
	}

	public int getOutside_PC_Hours() {
		return Outside_PC_Hours;
	}

	public void setOutside_PC_Hours(int outside_PC_Hours) {
		Outside_PC_Hours = outside_PC_Hours;
	}

	public int getOutside_Hours() {
		return Outside_Hours;
	}

	public void setOutside_Hours(int outside_Hours) {
		Outside_Hours = outside_Hours;
	}

	public String getWeek_Period() {
		return Week_Period;
	}

	public void setWeek_Period(String week_Period) {
		Week_Period = week_Period;
	}

	public String getLesson_Time() {
		return Lesson_Time;
	}

	public void setLesson_Time(String lesson_Time) {
		Lesson_Time = lesson_Time;
	}

	public String getLesson_Place() {
		return Lesson_Place;
	}

	public void setLesson_Place(String lesson_Place) {
		Lesson_Place = lesson_Place;
	}

	public String getTextbook_Name() {
		return Textbook_Name;
	}

	public void setTextbook_Name(String textbook_Name) {
		Textbook_Name = textbook_Name;
	}

	public String getTextbook_Author() {
		return Textbook_Author;
	}

	public void setTextbook_Author(String textbook_Author) {
		Textbook_Author = textbook_Author;
	}

	public String getTextbook_Publisher() {
		return Textbook_Publisher;
	}

	public void setTextbook_Publisher(String textbook_Publisher) {
		Textbook_Publisher = textbook_Publisher;
	}

	public String getTextbook_Version() {
		return Textbook_Version;
	}

	public void setTextbook_Version(String textbook_Version) {
		Textbook_Version = textbook_Version;
	}

	public String getTextbook_Award() {
		return Textbook_Award;
	}

	public void setTextbook_Award(String textbook_Award) {
		Textbook_Award = textbook_Award;
	}

	public String getExperiment_Place() {
		return Experiment_Place;
	}

	public void setExperiment_Place(String experiment_Place) {
		Experiment_Place = experiment_Place;
	}

	public String getCheck_Mode() {
		return Check_Mode;
	}

	public void setCheck_Mode(String check_Mode) {
		Check_Mode = check_Mode;
	}

	public String getClassroom_Category() {
		return Classroom_Category;
	}

	public void setClassroom_Category(String classroom_Category) {
		Classroom_Category = classroom_Category;
	}

	public String getClassroom_Category_Name() {
		return Classroom_Category_Name;
	}

	public void setClassroom_Category_Name(String classroom_Category_Name) {
		Classroom_Category_Name = classroom_Category_Name;
	}

	public String getCourse_Class_Remark() {
		return Course_Class_Remark;
	}

	public void setCourse_Class_Remark(String course_Class_Remark) {
		Course_Class_Remark = course_Class_Remark;
	}

	public String getStudent_Faculty() {
		return Student_Faculty;
	}

	public void setStudent_Faculty(String student_Faculty) {
		Student_Faculty = student_Faculty;
	}

	public String getAdd_User() {
		return Add_User;
	}

	public void setAdd_User(String add_User) {
		Add_User = add_User;
	}

	public String getAdd_Time() {
		return Add_Time;
	}

	public void setAdd_Time(String add_Time) {
		Add_Time = add_Time;
	}

	public String getModify_User() {
		return Modify_User;
	}

	public void setModify_User(String modify_User) {
		Modify_User = modify_User;
	}

	public String getModify_Time() {
		return Modify_Time;
	}

	public void setModify_Time(String modify_Time) {
		Modify_Time = modify_Time;
	}

	// public Collection<Edu_PlanExec> getEdu_planexec() {
	// return edu_planexec;
	// }
	//
	// public void setEdu_planexec(Collection<Edu_PlanExec> edu_planexec) {
	// this.edu_planexec = edu_planexec;
	// }
	//
	//
	// public Collection<Edu_Schedule> getEdu_schedule() {
	// return edu_schedule;
	// }
	//
	// public void setEdu_schedule(Collection<Edu_Schedule> edu_schedule) {
	// this.edu_schedule = edu_schedule;
	// }
	//
	//
	// public Collection<Edu_Survey> getEdu_Survey() {
	// return edu_Survey;
	// }
	//
	// public void setEdu_Survey(Collection<Edu_Survey> edu_Survey) {
	// this.edu_Survey = edu_Survey;
	// }

}
