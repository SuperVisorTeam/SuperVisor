package com.gdut.supervisor.info;

/***********************************************************************
 * Module:  edu_CourseClass.java
 * Author:  zhh
 * Purpose: Defines the Class edu_CourseClass
 ***********************************************************************/


/**
 * edu_CourseClass | ��ѧ�������Ϣ
 * 
 */

public class Edu_CourseClass {
	/**
	 * ��ѧ����(Unique)
	 * 
	 */
	public String Course_Class_No;
	/**
	 * ��ѧ�����
	 * 
	 */
	public String Teaching_Class_Group;
	/**
	 * �꼶
	 */
	public String student_grade;
	/**
	 * ����ѧ��
	 * 
	 */
	public String Semester;
	/**
	 * У������
	 * 
	 */
	public String School_District;
	/**
	 * ���ν�ʦ
	 * 
	 */
	public String Teacher_Name;
	/**
	 * ��ʦְ����
	 * 
	 */
	public String Teacher_Staff_ID;
	/**
	 * ��ʦְ��
	 * 
	 */
	public String Teacher_Title;
	/**
	 * ����ѧԺ
	 * 
	 */
	public String Commence_Dept;
	/**
	 * �γ̴���
	 * 
	 */
	public String Course_No;
	/**
	 * �γ�����
	 * 
	 */
	public String Course_Name;
	/**
	 * �γ�����(D)
	 * 
	 */
	public String Course_Nature;
	/**
	 * �γ����(D)
	 * 
	 */
	public String Course_Category;
	/**
	 * �ƻ��Ͽ�����
	 * 
	 */
	public int Plan_Population = 0;
	/**
	 * ��ѡ������
	 * 
	 */
	public int Actual_Population = 0;
	/**
	 * ��˫��״̬
	 * 
	 */
	public int Mono_Week_State = 0;
	/**
	 * ����״̬
	 * 
	 */
	public int Continuous_State = 0;
	/**
	 * �༶����
	 * 
	 */
	public int Class_Category;
	/**
	 * ˫��flg
	 * 
	 */
	public int Bilingual_Flag;
	/**
	 * ��ѧʱ
	 * 
	 */
	public String Week_Credit_Hour;
	/**
	 * ѧ��
	 * 
	 */
	public float Credit_Point;
	/**
	 * ��ѧʱ
	 * 
	 */
	public String Total_Credit_Hour;
	/**
	 * ����ѧʱ
	 * 
	 */
	public int Teaching_Hours;
	/**
	 * ʵ��ѧʱ
	 * 
	 */
	public int Experiment_Hours;
	/**
	 * �ϻ�ѧʱ
	 * 
	 */
	public int PC_Hours;
	/**
	 * �γ�ʵ��ѧʱ
	 * 
	 */
	public int Design_Hours;
	/**
	 * ϰ���ѧʱ
	 * 
	 */
	public int Exercise_Hours;
	/**
	 * �����ϻ�ѧʱ
	 * 
	 */
	public int Inside_PC_Hours;
	/**
	 * �����ϻ�ѧʱ
	 * 
	 */
	public int Outside_PC_Hours;
	/**
	 * ����ѧʱ
	 * 
	 */
	public int Outside_Hours;
	/**
	 * ��ֹ��
	 * 
	 */
	public String Week_Period;
	/**
	 * �Ͽ�ʱ��
	 * 
	 */
	public String Lesson_Time;
	/**
	 * �Ͽεص�
	 * 
	 */
	public String Lesson_Place;
	/**
	 * �̲�����
	 * 
	 */
	public String Textbook_Name;
	/**
	 * �̲�����
	 * 
	 */
	public String Textbook_Author;
	/**
	 * ������
	 * 
	 */
	public String Textbook_Publisher;
	/**
	 * ���
	 * 
	 */
	public String Textbook_Version;
	/**
	 * �����
	 * 
	 */
	public String Textbook_Award;
	/**
	 * ʵ�鳡��
	 * 
	 */
	public String Experiment_Place;
	/**
	 * ����
	 * 
	 */
	public String Check_Mode;
	/**
	 * �������ͱ���
	 * 
	 */
	public String Classroom_Category;
	/**
	 * ������������
	 * 
	 */
	public String Classroom_Category_Name;
	/**
	 * ��ע
	 * 
	 */
	public String Course_Class_Remark;
	/**
	 * ѧ��ѧԺ
	 */
	public String Student_Faculty;
	/**
	 * �����߱���
	 * 
	 */
	public String Add_User;
	/**
	 * ��������
	 * 
	 */
	public String Add_Time;
	/**
	 * �޸��߱���
	 * 
	 */
	public String Modify_User;
	/**
	 * �޸�����
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
