package com.gdut.supervisor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gdut.supervisor.info.BaseMessage;
import com.gdut.supervisor.info.Edu_Survey;
import com.gdut.supervisor.info.Edu_SurveyToIphone;



public class ChangeData {
	public static Edu_SurveyToIphone changePhone(Edu_Survey s)
			throws ParseException {
		Edu_SurveyToIphone es = new Edu_SurveyToIphone();

		es.setSurvey_ID(s.getSurvey_ID());
	

		es.setCourse_class_no(BaseMessage.class_no);
		
		
		//
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Date Lesson_date= sdf.parse(s.getLesson_date());
		// es.setLesson_date(Lesson_date);
		es.setLesson_date(s.getLesson_date());
		//
		es.setLesson_week_no(s.getLesson_week_no());

		es.setLesson_section(s.getLesson_section());

		es.setLesson_classroom(s.getLesson_classroom());

		es.setAbsent_Num(s.getAbsent_Num());

		es.setActual_Num(s.getActual_Num());

		es.setStudent_Faculty(s.getStudent_Faculty());

		es.setFood_Eat_Num(s.getFood_Eat_Num());

		es.setLate_LeaveEarly_Num(s.getLate_LeaveEarly_Num());

		es.setPlay_Mobil_Num(s.getPlay_Mobil_Num());

		es.setSleep_Speak_Num(s.getSleep_Speak_Num());

		es.setSlipper_Shorts_Num(s.getSlipper_Shorts_Num());

		es.setTeacher_Ontime(s.getTeacher_Ontime());

		es.setTruant_Num(s.getTruant_Num());

		es.setVacate_Num(s.getVacate_Num());

		es.setOther_Situation(s.getOther_Situation());

		es.setSupervisor(s.getSupervisor());

		es.setSurvey_Time(BaseMessage.temp_time);

		es.setTerminal_Type(s.getTerminal_Type());

		es.setTerminal_Desc(s.getTerminal_Desc());

		return es;
	}
}
