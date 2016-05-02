package action;

import java.util.List;

import entity.Students;
import service.StudentsDAO;
import serviceImpl.StudentsDAO_Impl;

/*
 * 学生Action类
 * */
public class StudentsAction extends SuperAction {

	private static final long serialVersionUID = 1L;
	
	//查询所有学生的动作
	public String query(){
		
		 //action中调用已经写好的接口来实现！
		StudentsDAO sdao = new StudentsDAO_Impl();
		List <Students> list = sdao.queryAllStudents();   /*list有可能是空，在接口实现类已考虑此情况*/
		//放进session中
		if(list!=null&&list.size()>0){
			session.setAttribute("students_list", list);
		}
		return  "query_success";
	}
	
	//删除指定学生的动作
	public String delete(){
		
		StudentsDAO sdao = new StudentsDAO_Impl();
		String sid = request.getParameter("sid");    //获得传过来的参数
		sdao.deleteStudents(sid);   //action中调用已经写好的接口来实现！
		
		return "delete_success";
	}

	
}
